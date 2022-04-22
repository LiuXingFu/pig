/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.casee.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.feign.RemoteInstitutionService;
import com.pig4cloud.pig.admin.api.feign.RemoteMessageRecordService;
import com.pig4cloud.pig.admin.api.feign.RemoteOutlesService;
import com.pig4cloud.pig.admin.api.vo.InstitutionVO;
import com.pig4cloud.pig.admin.api.vo.OutlesVO;
import com.pig4cloud.pig.casee.dto.CaseeOrTargetTaskFlowDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.mapper.CaseeBizMapper;
import com.pig4cloud.pig.casee.service.*;

import com.pig4cloud.pig.common.core.constant.CaseeOrTargetTaskFlowConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 案件表
 *
 * @author yy
 * @date 2021-09-15 10:03:22
 */
@Service
public class CaseeBizServiceImpl extends ServiceImpl<CaseeBizMapper, CaseeBizBase> implements CaseeBizService {

	@Autowired
	private CaseeOutlesDealReService caseeOutlesDealReService;
	@Autowired
	private TargetService targetService;

	@Autowired
	private RemoteMessageRecordService messageRecordService;
	@Autowired
	private RemoteInstitutionService institutionService;
	@Autowired
	private CaseeBizLiquiService caseeBizLiquiService;
	@Autowired
	private RemoteOutlesService outlesService;
	@Autowired
	private SecurityUtilsService securityUtilsService;
	@Autowired
	private TaskNodeService taskNodeService;
	@Autowired
	private TaskRecordService taskRecordService;
	@Autowired
	private RemoteOutlesService remoteOutlesService;


	@Override
	public Integer modifyCaseStatus(Integer caseeId,Integer status){
		CaseeBizBase caseeBizBase = new CaseeBizBase();
		caseeBizBase.setCaseeId(caseeId);
		caseeBizBase.setStatus(status);
		// 状态确认1=待确认
		caseeBizBase.setStatusConfirm(1);
		Integer result = this.baseMapper.updateById(caseeBizBase);

		// 更新案件机构关联表状态
		UpdateWrapper<CaseeOutlesDealRe> updateWrapper = new UpdateWrapper<>();
		updateWrapper.lambda().eq(CaseeOutlesDealRe::getDelFlag,0);
		updateWrapper.lambda().eq(CaseeOutlesDealRe::getCaseeId,caseeId);
		updateWrapper.lambda().set(CaseeOutlesDealRe::getStatus,status);
		// 状态确认1=待确认
		updateWrapper.lambda().set(CaseeOutlesDealRe::getStatusConfirm,1);
		caseeOutlesDealReService.update(updateWrapper);
		return result;
	}

	@Override
	public Integer caseClosedOpen(Integer caseeId, LocalDateTime closeTime){
		CaseeBizBase caseeBizBase = new CaseeBizBase();
		caseeBizBase.setCaseeId(caseeId);
		// 案件状态4：结案
		caseeBizBase.setStatus(4);
		// 状态确认1=待确认
		caseeBizBase.setStatusConfirm(1);
		caseeBizBase.setCloseTime(closeTime);
		Integer result = this.baseMapper.updateById(caseeBizBase);

		// 更新案件机构关联表状态
		UpdateWrapper<CaseeOutlesDealRe> updateWrapper = new UpdateWrapper<>();
		updateWrapper.lambda().eq(CaseeOutlesDealRe::getDelFlag,0);
		updateWrapper.lambda().eq(CaseeOutlesDealRe::getCaseeId,caseeId);
		// 案件状态4：结案
		updateWrapper.lambda().set(CaseeOutlesDealRe::getStatus,4);
		// 状态确认1=待确认
		updateWrapper.lambda().set(CaseeOutlesDealRe::getStatusConfirm,1);
		caseeOutlesDealReService.update(updateWrapper);

		return result;
	}

	@Override
	public Integer confirmCaseStatus(CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO, Integer statusConfirm){
		List<CaseeOutlesDealRe> caseeOutlesDealReList=null;
		//根据案件id、委托机构委托网点id、以及类型1=委托案件人员关联表
		QueryWrapper<CaseeOutlesDealRe> queryWrapperCaseeOutlesDealRe = new QueryWrapper<>();
		queryWrapperCaseeOutlesDealRe.lambda().eq(CaseeOutlesDealRe::getCaseeId,caseeOrTargetTaskFlowDTO.getCaseeId());
		queryWrapperCaseeOutlesDealRe.lambda().eq(CaseeOutlesDealRe::getDelFlag,0);
		queryWrapperCaseeOutlesDealRe.lambda().eq(CaseeOutlesDealRe::getInsId,securityUtilsService.getCacheUser().getInsId());
		queryWrapperCaseeOutlesDealRe.lambda().eq(CaseeOutlesDealRe::getOutlesId,securityUtilsService.getCacheUser().getOutlesId());
		// 委托机构
		queryWrapperCaseeOutlesDealRe.lambda().eq(CaseeOutlesDealRe::getType,1);
		// 查询案件的委托机构
		caseeOutlesDealReList = caseeOutlesDealReService.list(queryWrapperCaseeOutlesDealRe);

		CaseeOutlesDealRe updateDealRe = new CaseeOutlesDealRe();
		CaseeBizBase casee = this.baseMapper.selectById(caseeOrTargetTaskFlowDTO.getCaseeId());
		if (caseeOutlesDealReList.size()>0){
			//更新委托机构的确认状态
			updateDealRe.setDealReId(caseeOutlesDealReList.get(0).getDealReId());
			updateDealRe.setStatusConfirm(statusConfirm);
			caseeOutlesDealReService.updateById(updateDealRe);
		}

		//启动任务流完成审核任务
		taskNodeService.auditCaseeOrTargetTask(caseeOrTargetTaskFlowDTO);

		// 查询委托机构是否全部确认完毕，确认完毕更新案件状态和确认时间
		QueryWrapper<CaseeOutlesDealRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(CaseeOutlesDealRe::getDelFlag,0);
		queryWrapper.lambda().eq(CaseeOutlesDealRe::getCaseeId,caseeOrTargetTaskFlowDTO.getCaseeId());
		// 状态确认1=待确认
		queryWrapper.lambda().eq(CaseeOutlesDealRe::getStatusConfirm,1);
		queryWrapper.lambda().eq(CaseeOutlesDealRe::getType,1);
		List<CaseeOutlesDealRe> list = caseeOutlesDealReService.list(queryWrapper);
		if(list.size()==0){
			CaseeBizBase caseeBizBase = new CaseeBizBase();
			caseeBizBase.setCaseeId(caseeOrTargetTaskFlowDTO.getCaseeId());
			caseeBizBase.setStatusConfirm(statusConfirm);
			caseeBizBase.setAffirmTime(LocalDateTime.now());
			this.baseMapper.updateById(caseeBizBase);

			// 同步更新标的信息
			if(statusConfirm==2){
				UpdateWrapper<Target> updateWrapper = new UpdateWrapper<>();
				updateWrapper.lambda().eq(Target::getDelFlag,0);
				updateWrapper.lambda().eq(Target::getCaseeId,caseeOrTargetTaskFlowDTO.getCaseeId());
//				updateWrapper.lambda().set(Target::getStatusConfirm,0);
//				updateWrapper.lambda().isNull(Target::getCloseTime);
				// 案件状态4：结案并且状态确认2=已确认，将修改标的状态2=已完成
				if(casee.getStatus()==4){
					updateWrapper.lambda().set(Target::getStatus,2);
//					updateWrapper.lambda().set(Target::getCloseTime,casee.getCloseTime());
					targetService.update(updateWrapper);
					// 案件状态1=开启并且状态确认2=已确认，将修改标的状态1=执行中
				}else if(casee.getStatus()==1){
					updateWrapper.lambda().set(Target::getStatus,1);
					targetService.update(updateWrapper);
					//案件状态2=暂缓或者3=中止并且状态确认2=已确认，将修改标的状态3=停案
				}else if(casee.getStatus()==2 || casee.getStatus()==3){
					updateWrapper.lambda().set(Target::getStatus,3);
					targetService.update(updateWrapper);
				}
			}
		}
		return 1;
	}

	@Override
	public List<CaseeOutlesDealRe> messagePush(Integer caseeId,Integer targetId,String statusName,LocalDateTime closeTime,String explain){
		if(explain!=null && !explain.equals("")){
			explain = "情况说明："+explain+"。";
		}else{
			explain = "";
		}
		Target target = new Target();
		if(Objects.nonNull(targetId)){
			target = targetService.getById(targetId);
			caseeId = target.getCaseeId();
		}
		CaseeBizBase caseeBizBase = this.baseMapper.selectById(caseeId);
		QueryWrapper<CaseeOutlesDealRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(CaseeOutlesDealRe::getCaseeId,caseeId);
		queryWrapper.lambda().eq(CaseeOutlesDealRe::getDelFlag,0);
		// 委托机构
		queryWrapper.lambda().eq(CaseeOutlesDealRe::getType,1);
		// 查询案件的委托机构
		List<CaseeOutlesDealRe> caseeOutlesDealReList = caseeOutlesDealReService.list(queryWrapper);
		if(caseeOutlesDealReList.size()>0){
			List<MessageRecordDTO> list = new ArrayList<>();
			PigUser pigUser = securityUtilsService.getCacheUser();
			// 公司业务案号
			String companyCode = caseeBizBase.getCompanyCode();
			R<InstitutionVO> institution = institutionService.getById(caseeBizBase.getCreateInsId(), SecurityConstants.FROM);
			// 委托机构名称
			String insName = institution.getData().getInsName();
			R<OutlesVO> outles = outlesService.getById(caseeBizBase.getCreateOutlesId(),SecurityConstants.FROM);
			// 委托网点名称
			String outlesName = outles.getData().getOutlesName();
			String name = "";
			if(Objects.nonNull(targetId)){
				name = "标的";
			}else{
				name = "案件";
			}
			// 消息标题
			String messageTitle = name+statusName+"申请通知";
			// 推送内容
			String messageContent = "由"+insName+outlesName+"办理的业务案号为"+companyCode+"，";
			if(Objects.nonNull(targetId)){
				messageContent += "标的名称为："+target.getTargetName()+"，";
			}
			messageContent += "申请"+statusName+"。";
			if(Objects.nonNull(closeTime)){
				messageContent += "结案时间为："+closeTime+"。";
			}
			messageContent += explain+"请处理！";
			for (CaseeOutlesDealRe caseeOutlesDealRe : caseeOutlesDealReList) {
				MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
				messageRecordDTO.setCreateBy(pigUser.getId());
				messageRecordDTO.setCreateTime(LocalDate.now());
				// 300=清收消息
				messageRecordDTO.setMessageType(300);
				messageRecordDTO.setMessageTitle(messageTitle);
				messageRecordDTO.setMessageContent(messageContent);
				messageRecordDTO.setReceiverInsId(caseeOutlesDealRe.getInsId());
				messageRecordDTO.setReceiverOutlesId(caseeOutlesDealRe.getOutlesId());
				list.add(messageRecordDTO);
			}
			// 批量保存委托机构消息通知
			messageRecordService.batchSendMessageRecordOutPush(list,SecurityConstants.FROM);
		}
		return caseeOutlesDealReList;
	}

	@Override
	public void caseeOrTagetFlowStart(TaskNode taskNode, List<CaseeOutlesDealRe> caseeOutlesDealReList,String explain) {
		//案件任务流对象
		CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO=new CaseeOrTargetTaskFlowDTO();

		//当前登录用户信息
		PigUser user = securityUtilsService.getCacheUser();

		//设置案件审核申请人id
		caseeOrTargetTaskFlowDTO.setCaseeOrTargetAuditProposer(user.getId());
		//设置任务流唯一key
		caseeOrTargetTaskFlowDTO.setTaskKey(CaseeOrTargetTaskFlowConstants.CASEEORTARGET_KEY);

		caseeOrTargetTaskFlowDTO.setNodeId(taskNode.getNodeId());

		// 1.创建任务流程实例
		ProcessInstance processInstance = taskNodeService.beginFlow(caseeOrTargetTaskFlowDTO,CaseeOrTargetTaskFlowConstants.CASEEORTARGET_OBJECT);

		if (taskNode.getNodeName().equals("清收案件还款详情")){
			//查询当前登录用户网点信息
			R<OutlesVO> outlesVO = remoteOutlesService.getById(user.getOutlesId(), SecurityConstants.FROM);
			List list=new ArrayList();
//			list.add(outlesVO.getData().getUserId());
			//设置清收案件还款详情审核人id(当前登录网点负责人)
			caseeOrTargetTaskFlowDTO.setCaseeOrTargetAuditorList(list);
		}else {//案件开启、结案、异常和标的损毁和灭失需要银行审核(审核人可能存在多个)
			//网点id集合
			List<Integer> outlesIds=new ArrayList<>();

			if(caseeOutlesDealReList.size()>0){
				for (CaseeOutlesDealRe caseeOutlesDealRe : caseeOutlesDealReList) {
					outlesIds.add(caseeOutlesDealRe.getOutlesId());
				}
			}
			//批量查询网点信息
			R<List<OutlesVO>> batchOutles = remoteOutlesService.batchQueryOutlesId(outlesIds, SecurityConstants.FROM);

			List<OutlesVO> outlesVOList = batchOutles.getData();

			//审核人id  （默认为网点负责人id可能存在多个）
			List<Integer> outlesId=new ArrayList();

			for (OutlesVO outlesVO : outlesVOList) {
//				outlesId.add(outlesVO.getUserId());
			}
			//设置案件审核人id
			caseeOrTargetTaskFlowDTO.setCaseeOrTargetAuditorList(outlesId);
		}

		//设置流程实例id
		caseeOrTargetTaskFlowDTO.setActReProcdefId(processInstance.getId());

		//2.完成案件或标的审核申请人流程
		taskNodeService.executionTask(caseeOrTargetTaskFlowDTO,"案件或标的审核申请人",CaseeOrTargetTaskFlowConstants.CASEEORTARGET_OBJECT);

		taskNode.setStatus(101);

		taskNode.setActReProcdefId(processInstance.getId());

		taskNodeService.updateById(taskNode);

		BeanCopyUtil.copyBean(taskNode,caseeOrTargetTaskFlowDTO);
		caseeOrTargetTaskFlowDTO.setDescribed(explain);

		//添加任务记录
		taskRecordService.addTaskRecord(caseeOrTargetTaskFlowDTO,CaseeOrTargetTaskFlowConstants.CASEEORTARGET_OBJECT);
	}


}
