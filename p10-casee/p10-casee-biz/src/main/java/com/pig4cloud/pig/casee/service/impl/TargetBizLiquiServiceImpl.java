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


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.dto.TaskNodeTemplateDTO;
import com.pig4cloud.pig.admin.api.entity.OutlesTemplateRe;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.admin.api.feign.RemoteMessageRecordService;
import com.pig4cloud.pig.admin.api.feign.RemoteOutlesTemplateReService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.casee.dto.CaseeOrTargetTaskFlowDTO;
import com.pig4cloud.pig.casee.dto.TargetAddDTO;
import com.pig4cloud.pig.casee.dto.TargetCloseTimeDTO;
import com.pig4cloud.pig.casee.dto.TargetThingAddDTO;
import com.pig4cloud.pig.casee.entity.CaseeBizBase;
import com.pig4cloud.pig.casee.entity.CaseeOutlesDealRe;
import com.pig4cloud.pig.casee.entity.TargetBizLiqui;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.detail.TargetThingLiQuiDateDetail;
import com.pig4cloud.pig.casee.entity.liqui.LiQui;
import com.pig4cloud.pig.casee.mapper.TargetBizLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.TargetByIdVO;
import com.pig4cloud.pig.common.core.constant.CaseeOrTargetTaskFlowConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.casee.vo.TargetThingByIdVO;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import net.sf.json.JSONObject;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 标的表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Service
public class TargetBizLiquiServiceImpl extends ServiceImpl<TargetBizLiquiMapper, TargetBizLiqui> implements TargetBizLiquiService {
	@Autowired
	private SecurityUtilsService securityUtilsService;
	@Autowired
	private RemoteOutlesTemplateReService remoteOutlesTemplateReService;
	@Autowired
	private TaskNodeService taskNodeService;

	@Autowired
	private RemoteAddressService remoteAddressService;

	@Autowired
	private CaseeBizService caseeBizService;

	@Autowired
	private RemoteMessageRecordService remoteMessageRecordService;

//	@Override
//	@Transactional
//	public Integer addTarget(TargetAddDTO targetAddDTO){
//		PigUser pigUser= securityUtilsService.getCacheUser();
//		//如果当前用户添加标的没有选择网点
//		if (pigUser.getOutlesId()==null){
//			return -1;
//		}
//		//通过当前登录网点查询网点模板关联信息
//		R<List<OutlesTemplateRe>> outlesTemplateReList = remoteOutlesTemplateReService.getByOutlesId(pigUser.getOutlesId(), SecurityConstants.FROM);
//
//		//通过标的物性质查询模板信息
//		R<TaskNodeTemplate> taskNodeTemplate = remoteOutlesTemplateReService.queryTemplateByTargetType(targetAddDTO.getTargetType(),pigUser.getOutlesId(), SecurityConstants.FROM);
//
//		//如果当前网点没有关联模板信息或者标的没有配置模板直接返回
//		if (outlesTemplateReList.getData().size()<=0||taskNodeTemplate.getData()==null){
//			return -2;
//		}
//
//
//		TargetBizLiqui targetBizLiqui = new TargetBizLiqui();
//		BeanUtils.copyProperties(targetAddDTO,targetBizLiqui);
//		targetBizLiqui.setCreateInsId(pigUser.getInsId());
//		targetBizLiqui.setCreateOutlesId(pigUser.getOutlesId());
//		// 业务类型100=清收业务
//		targetBizLiqui.setBusinessType(100);
//		// 标的类别1=标的
//		targetBizLiqui.setTargetCategory(1);
//
//		CaseeBizBase caseeBizBase = caseeBizService.getById(targetAddDTO.getCaseeId());
//		// 案件状态为已确认开启状态，标的状态为执行中状态
//		if(caseeBizBase.getStatus()==1&&caseeBizBase.getStatusConfirm()==2){
//			targetBizLiqui.setStatus(1);
//		}
//		targetBizLiqui.setTargetDetail(JsonUtils.objectToJson(targetAddDTO.getTargetDetail()));
//		int insert = this.baseMapper.insert(targetBizLiqui);
//
//		//根据模板id生成任务
//		configurationNodeTemplate(targetBizLiqui,taskNodeTemplate.getData().getTemplateId());
//		//新增标的损毁、灭失任务
//		addTargetThingTask(targetBizLiqui);
//		this.baseMapper.updateById(targetBizLiqui);
//
//		return insert;
//	}
//	@Override
//	@Transactional
//	public Integer addTargetThing(TargetThingAddDTO targetThingAddDTO){
//		PigUser pigUser= securityUtilsService.getCacheUser();
//		//如果当前用户没有选择网点
//		if (pigUser.getOutlesId()==null){
//			return -1;
//		}
//
//		//通过当前登录网点查询网点模板关联信息
//		R<List<OutlesTemplateRe>> outlesTemplateReList = remoteOutlesTemplateReService.getByOutlesId(pigUser.getOutlesId(), SecurityConstants.FROM);
//
//		//通过标的物性质查询模板信息
//		R<TaskNodeTemplate> taskNodeTemplate = remoteOutlesTemplateReService.queryTemplateByTargetType(targetThingAddDTO.getTargetType(),pigUser.getOutlesId(), SecurityConstants.FROM);
//
//		//如果当前网点没有关联模板信息或者标的没有配置模板直接返回
//		if (outlesTemplateReList.getData().size()<=0||taskNodeTemplate.getData()==null){
//			return -2;
//		}
//
//		//--------拿到明细信息
//		ObjectMapper objectMapper = new ObjectMapper();
//
//
//		TargetThingLiQuiDateDetail targetThingAdd=new TargetThingLiQuiDateDetail();
//		BeanCopyUtil.copyBean(targetThingAddDTO,targetThingAdd);
//		/** 房产详情 */
//		if(targetThingAddDTO.getHouseDetail().getParticularsCode() != null){
//			targetThingAdd.setTargetPropertiesDetail(targetThingAddDTO.getHouseDetail());
//		}
//
//		/** 车辆详情 */
//		if(targetThingAddDTO.getCarDetail().getParticularsCode()  != null ){
//
//			targetThingAdd.setTargetPropertiesDetail(targetThingAddDTO.getCarDetail());
//
//		}
//		/** 股权详情 */
//		if(targetThingAddDTO.getEquityDetail().getParticularsCode()  != null ){
//			targetThingAdd.setTargetPropertiesDetail(targetThingAddDTO.getEquityDetail());
//		}
//
//		/** 土地详情 */
//		if(targetThingAddDTO.getLandDetail().getParticularsCode()  != null ){
//			targetThingAdd.setTargetPropertiesDetail(targetThingAddDTO.getLandDetail());
//
//		}
//		/** 其它详情 */
//		if(targetThingAddDTO.getOtherDetail().getParticularsCode()  != null ){
//			targetThingAdd.setTargetPropertiesDetail(targetThingAddDTO.getOtherDetail());
//
//		}
//		TargetBizLiqui targetBizLiqui = new TargetBizLiqui();
//
//		//添加地址
//		Address address=new Address();
//		address.setProvince(targetThingAddDTO.getProvince());
//		address.setCity(targetThingAddDTO.getCity());
//		address.setArea(targetThingAddDTO.getArea());
//		address.setInformationAddress(targetThingAddDTO.getInformationAddress());
//		address.setCode(targetThingAddDTO.getCode());
//		R rest=remoteAddressService.saveAddress(address, SecurityConstants.FROM);
//		if(rest!=null && rest.getData()!=null){
//			address= objectMapper.convertValue( rest.getData(), Address.class);
//			targetThingAddDTO.setAddressId(address.getAddressId());
//		}
//
//		BeanCopyUtil.copyBean(targetThingAddDTO,targetBizLiqui);
//		targetBizLiqui.setCreateInsId(pigUser.getInsId());
//		targetBizLiqui.setCreateOutlesId(pigUser.getOutlesId());
//		// 业务类型100=清收业务
//		targetBizLiqui.setBusinessType(100);
//		// 标的类别2=标的物
//		targetBizLiqui.setTargetCategory(2);
//		CaseeBizBase caseeBizBase = caseeBizService.getById(targetThingAddDTO.getCaseeId());
//		// 案件状态为已确认开启状态，标的物状态为执行中状态
//		if(caseeBizBase.getStatus()==1&&caseeBizBase.getStatusConfirm()==2){
//			targetBizLiqui.setStatus(1);
//		}
//		targetBizLiqui.setTargetDetail(JsonUtils.objectToJson(targetThingAdd) );
//
//		int insert = this.baseMapper.insert(targetBizLiqui);
//
//
//		//根据模板id生成任务
//		configurationNodeTemplate(targetBizLiqui,taskNodeTemplate.getData().getTemplateId());
//		//新增标的物损毁、灭失任务
//		addTargetThingTask(targetBizLiqui);
//		return insert;
//	}
//
//	@Override
//	@Transactional
//	public TargetBizLiqui queryById(Integer targetId){
//		return this.baseMapper.selectById(targetId);
//	}
//
//	@Override
//	@Transactional
//	public TargetThingByIdVO queryTargetThingById(Integer targetId){
//		return this.baseMapper.selectTargetThingById(targetId);
//	}
//
//	@Override
//	@Transactional
//	public Integer modifyStatusById(Integer targetId, Integer status,String explain){
//		String statusName = null;
//		TaskNode taskNode=null;
//
//		TargetBizLiqui target = this.baseMapper.selectById(targetId);
//
//		if(status == 101){
//			statusName = "毁损";
//			if (target.getTargetCategory()==1){//查看标的损毁任务
//				taskNode = taskNodeService.getById(CaseeOrTargetTaskFlowConstants.TARGETID_DAMAGE + targetId);
//			}else {//查看标的物损毁任务
//				taskNode = taskNodeService.getById(CaseeOrTargetTaskFlowConstants.TARGETTHINGID_DAMAGE + targetId);
//			}
//		}else if(status == 102){
//			statusName = "灭失";
//			if (target.getTargetCategory()==1){//查看标的灭失任务
//				taskNode = taskNodeService.getById(CaseeOrTargetTaskFlowConstants.TARGETID_LOST + targetId);
//			}else {//查看标的物灭失任务
//				taskNode = taskNodeService.getById(CaseeOrTargetTaskFlowConstants.TARGETTHINGID_LOST + targetId);
//			}
//		}
//		// 消息推送
//		List<CaseeOutlesDealRe> caseeOutlesDealReList = caseeBizService.messagePush(null, targetId, statusName, null, explain);
//
//		//启动任务流设置审核人
//		caseeBizService.caseeOrTagetFlowStart(taskNode,caseeOutlesDealReList,explain);
//
//		TargetBizLiqui targetBizLiqui = new TargetBizLiqui();
//		targetBizLiqui.setTargetId(targetId);
//		targetBizLiqui.setStatus(status);
//		// 状态确认1=待确认
//		targetBizLiqui.setStatusConfirm(1);
//		return this.baseMapper.updateById(targetBizLiqui);
//	}
//
//	@Override
//	@Transactional
//	public Integer closeTargetById(TargetCloseTimeDTO targetCloseTimeDTO){
//		caseeBizService.messagePush(null,targetCloseTimeDTO.getTargetId(),"结案",targetCloseTimeDTO.getCloseTime(),targetCloseTimeDTO.getExplain());
//		TargetBizLiqui targetBizLiqui = new TargetBizLiqui();
//		targetBizLiqui.setTargetId(targetCloseTimeDTO.getTargetId());
//		targetBizLiqui.setStatus(2);
//		// 状态确认0=默认状态
//		targetBizLiqui.setStatusConfirm(0);
//		targetBizLiqui.setCloseTime(targetCloseTimeDTO.getCloseTime());
//		return this.baseMapper.updateById(targetBizLiqui);
//	}
//
//	@Override
//	@Transactional
//	public TargetByIdVO queryTargetById(Integer targetId){
//		return this.baseMapper.selectTargetById(targetId);
//	}
//
//	@Override
//	@Transactional
//	public Integer confirmCaseStatus(CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO, Integer statusConfirm){
//		String explain=caseeOrTargetTaskFlowDTO.getDescribed();
//
//		if(explain!=null && !explain.equals("")){
//			explain = "情况说明："+explain+"。";
//		}else{
//			explain = "";
//		}
//		String statusName = null;
//		if(statusConfirm==2){
//			statusName = "已确认";
//		}else if(statusConfirm == 3){
//			statusName = "已拒绝";
//		}
//		TargetBizLiqui targetBizLiqui = this.baseMapper.selectById(caseeOrTargetTaskFlowDTO.getTargetId());
//		if(Objects.nonNull(targetBizLiqui)){
//
//			CaseeBizBase caseeBizBase = caseeBizService.getById(targetBizLiqui.getCaseeId());
//
//			PigUser pigUser = securityUtilsService.getCacheUser();
//			// 消息标题
//			String messageTitle = "标的状态确认通知";
//			// 推送内容
//			String messageContent = "办理标的名称为"+targetBizLiqui.getTargetName()+"，标的状态申请"+statusName+"。"+explain+"请知悉！";
//			MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
//			messageRecordDTO.setCreateBy(pigUser.getId());
//			messageRecordDTO.setCreateTime(LocalDateTime.now());
//			// 300=清收消息
//			messageRecordDTO.setMessageType(300);
//			messageRecordDTO.setMessageTitle(messageTitle);
//			messageRecordDTO.setMessageContent(messageContent);
//			messageRecordDTO.setReceiverInsId(caseeBizBase.getCreateInsId());
//			messageRecordDTO.setReceiverOutlesId(caseeBizBase.getCreateOutlesId());
//			messageRecordDTO.setReceiverUserId(caseeBizBase.getUserId());
//			List<MessageRecordDTO>messageRecordDTOList=new ArrayList<>();
//			messageRecordDTOList.add(messageRecordDTO);
//			// 保存消息通知
//			remoteMessageRecordService.batchSendMessageRecordOutPush(messageRecordDTOList,SecurityConstants.FROM);
//		}
//		// 更新标的状态确认
//		TargetBizLiqui target = new TargetBizLiqui();
//		target.setTargetId(caseeOrTargetTaskFlowDTO.getTargetId());
//		target.setStatusConfirm(statusConfirm);
//
//		//启动任务流完成审核任务
//		taskNodeService.auditCaseeOrTargetTask(caseeOrTargetTaskFlowDTO);
//		return this.baseMapper.updateById(target);
//	}
//
//	@Override
//	public void addTargetThingTask(TargetBizLiqui targetBizLiqui) {
//		//1，创建processEngine对象
//		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//		//2，得到repositoryService实例
//		RepositoryService repositoryService = processEngine.getRepositoryService();
//		//查询清收标的任务流程部署信息
//		Deployment targetDeployment = repositoryService.createDeploymentQuery().deploymentName("案件标的审批流程").singleResult();
//
//		TaskNode targetDamageTask=new TaskNode();//标的损毁任务实体
//		TaskNode targetlostTask=new TaskNode();//标的灭失任务实体
//		TaskNode targetThingDamageTask=new TaskNode();//标的物损毁任务实体
//		TaskNode targetThingTask=new TaskNode();//标的物损毁任务实体
//
//		if (targetBizLiqui.getTargetCategory()==1){//添加标的任务
//			targetDamageTask.setNodeId(CaseeOrTargetTaskFlowConstants.TARGETID_DAMAGE+targetBizLiqui.getTargetId());
//			targetDamageTask.setNodeKey(CaseeOrTargetTaskFlowConstants.TARGETNODEKEY_DAMAGE);
//			targetDamageTask.setActDeploymentId(targetDeployment.getId());
//			targetDamageTask.setNodeName("清收标的毁损");
//			targetDamageTask.setNeedAudit(1);
//			targetDamageTask.setInsType(1200);
//			targetDamageTask.setNodeType(110);
//			targetDamageTask.setInsId(targetBizLiqui.getCreateInsId());
//			targetDamageTask.setOutlesId(targetBizLiqui.getCreateOutlesId());
//			targetDamageTask.setCaseeId(targetBizLiqui.getCaseeId());
//			targetDamageTask.setTargetId(targetBizLiqui.getTargetId());
//			taskNodeService.save(targetDamageTask);
//			BeanCopyUtil.copyBean(targetDamageTask,targetlostTask);
//			targetlostTask.setNodeId(CaseeOrTargetTaskFlowConstants.TARGETID_LOST+targetBizLiqui.getTargetId());
//			targetlostTask.setNodeKey(CaseeOrTargetTaskFlowConstants.TARGETNODEKEY_LOST);
//			targetlostTask.setNodeName("清收标的灭失");
//			targetlostTask.setNodeType(111);
//			taskNodeService.save(targetlostTask);
//
//		}else {//添加标的物任务
//			targetThingDamageTask.setNodeId(CaseeOrTargetTaskFlowConstants.TARGETTHINGID_DAMAGE+targetBizLiqui.getTargetId());
//			targetThingDamageTask.setNodeKey(CaseeOrTargetTaskFlowConstants.TARGETTHINGNODEKEY_DAMAGE);
//			targetThingDamageTask.setActDeploymentId(targetDeployment.getId());
//			targetThingDamageTask.setNodeName("清收标的物毁损");
//			targetThingDamageTask.setNeedAudit(1);
//			targetThingDamageTask.setInsType(1200);
//			targetThingDamageTask.setNodeType(120);
//			targetThingDamageTask.setInsId(targetBizLiqui.getCreateInsId());
//			targetThingDamageTask.setOutlesId(targetBizLiqui.getCreateOutlesId());
//			targetThingDamageTask.setCaseeId(targetBizLiqui.getCaseeId());
//			targetThingDamageTask.setTargetId(targetBizLiqui.getTargetId());
//			taskNodeService.save(targetThingDamageTask);
//
//			BeanCopyUtil.copyBean(targetThingDamageTask,targetThingTask);
//			targetThingTask.setNodeId(CaseeOrTargetTaskFlowConstants.TARGETTHINGID_LOST+targetBizLiqui.getTargetId());
//			targetThingTask.setNodeKey(CaseeOrTargetTaskFlowConstants.TARGETTHINGNODEKEY_LOST);
//			targetThingTask.setNodeName("清收标的物灭失");
//			targetThingTask.setNodeType(121);
//			taskNodeService.save(targetThingTask);
//		}
//	}
//	@Override
//	@Transactional
//	public  void configurationNodeTemplate(TargetBizLiqui targetBizLiqui,Integer templateId){
//		// 新增标的物模板
//		TaskNodeTemplateDTO taskNodeTemplateDTO=new TaskNodeTemplateDTO();
//		taskNodeTemplateDTO.setCaseeId(targetBizLiqui.getCaseeId());
//		taskNodeTemplateDTO.setInsId(targetBizLiqui.getCreateInsId());
//		taskNodeTemplateDTO.setOutlesId(targetBizLiqui.getCreateOutlesId());
//		taskNodeTemplateDTO.setTargetId(targetBizLiqui.getTargetId());
//
//		taskNodeTemplateDTO.setTemplateId(templateId);
//		LiQui liQui=new LiQui();
//		JSONObject jsonObject= JSONObject.fromObject(liQui);
//
//		//根据模板id生成任务
//		taskNodeService.queryNodeTemplateAddTaskNode(taskNodeTemplateDTO,jsonObject);
//		targetBizLiqui.setBusinessData(jsonObject.toString());
//		this.baseMapper.updateById(targetBizLiqui);
//	}

}
