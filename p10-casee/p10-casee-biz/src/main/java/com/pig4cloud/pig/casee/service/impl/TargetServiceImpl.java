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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.TaskNodeTemplateDTO;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.admin.api.feign.RemoteOutlesTemplateReService;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.Target;
import com.pig4cloud.pig.casee.entity.project.beillegalprocedure.BeIllegal;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX;
import com.pig4cloud.pig.casee.entity.project.fundingzxprocedure.FundingZX;
import com.pig4cloud.pig.casee.entity.project.limitprocedure.Limit;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.LX.LiQui_LX;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SQ.LiQuiSQ;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SQ.LiQui_SQ;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSBQ.LiQuiSSBQ;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSBQ.LiQui_SSBQ;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES.LiQuiSSES;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES.LiQui_SSES;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSQT.LiQuiSSQT;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSQT.LiQui_SSQT;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS.LiQuiSSYS;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS.LiQui_SSYS;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXSZ.LiQuiZXSZ;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXSZ.LiQui_ZXSZ;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXZH.LiQuiZXZH;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXZH.LiQui_ZXZH;
import com.pig4cloud.pig.casee.mapper.TargetMapper;
import com.pig4cloud.pig.casee.service.TargetService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.casee.vo.TargetCaseeProjectPageVO;
import com.pig4cloud.pig.casee.vo.TargetPageVO;
import com.pig4cloud.pig.casee.vo.TaskNodeVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.KeyValue;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 标的表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */

@Service
public class TargetServiceImpl extends ServiceImpl<TargetMapper, Target> implements TargetService {
	@Autowired
	private SecurityUtilsService securityUtilsService;
	@Autowired
	JurisdictionUtilsService jurisdictionUtilsService;
	@Autowired
	RemoteOutlesTemplateReService remoteOutlesTemplateReService;
	@Autowired
	TaskNodeService taskNodeService;

	@Override
	public IPage<TargetPageVO> queryPageList(Page page, TargetPageDTO targetPageDTO) {
		PigUser cacheUser = securityUtilsService.getCacheUser();
		targetPageDTO.setInsId(cacheUser.getInsId());
		targetPageDTO.setOutlesId(cacheUser.getOutlesId());
		return this.baseMapper.queryPageList(page, targetPageDTO);
	}

	@Override
	public Integer addTarget(TargetThingAddDTO targetAddDTO) {
		PigUser pigUser = securityUtilsService.getCacheUser();
		Target target = new Target();
		BeanCopyUtil.copyBean(targetAddDTO, target);
		target.setCreateInsId(pigUser.getInsId());
		target.setCreateOutlesId(pigUser.getOutlesId());
		return this.baseMapper.insert(target);
	}

	@Override
	public Boolean updateBusinessData(AuditTargetDTO auditTargetDTO) {

		JSONObject formData = JSONObject.parseObject(auditTargetDTO.getFormData());
		List<KeyValue> listParams = new ArrayList<KeyValue>();

		for (String o : formData.keySet()) {
			listParams.add(new KeyValue(auditTargetDTO.getKey() + "." + o, formData.get(o)));
		}

		String businessData = this.baseMapper.queryBusinessData(auditTargetDTO);

		if (businessData == null) {
			String[] pathVec = auditTargetDTO.getKey().substring(2).split("\\.");
			Target targetSrc = this.getById(auditTargetDTO.getTargetId());
			JSONObject bussDataJson = JSONObject.parseObject(targetSrc.getBusinessData());

			int index = pathVec.length;
			do {
				index--;
				String varName = pathVec[index];
				JSONObject tmpJson = new JSONObject();
				tmpJson.put(varName, formData);
				formData = tmpJson;
				if (index <= 0) {
					break;
				}
			} while (true);

			BeanCopyUtil.mergeJSONObject(bussDataJson, formData);
			Target needUpdate = new Target();
			needUpdate.setTargetId(targetSrc.getTargetId());
			needUpdate.setBusinessData(bussDataJson.toJSONString());
			this.updateById(needUpdate);
			return true;
		} else {
			this.baseMapper.updateBusinessData(auditTargetDTO, listParams);
			return true;
		}
	}

	/**
	 * 根据程序DTO添加相应程序与相应任务
	 *
	 * @param targetAddDTO
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public int saveTargetAddDTO(TargetAddDTO targetAddDTO) throws Exception {
		targetAddDTO.setCreateOutlesId(securityUtilsService.getCacheUser().getOutlesId());
		targetAddDTO.setCreateInsId(securityUtilsService.getCacheUser().getInsId());
		this.save(targetAddDTO);

		// 查询
		R<TaskNodeTemplate> taskNodeTemplate = remoteOutlesTemplateReService.queryTemplateByTemplateNature(targetAddDTO.getProcedureNature(),targetAddDTO.getOutlesId() , SecurityConstants.FROM);
		//根据案件类型以及当前网点id查询该网点是否配置了对应模板
		if (taskNodeTemplate.getData() == null) {
			//如果当前受托网点没有配置模板直接返回
			throw new RuntimeException("当前网点没有配置模板！");
		}
		//添加任务数据
		configurationNodeTemplate(targetAddDTO, taskNodeTemplate.getData().getTemplateId());

		return 1;
	}

	/**
	 * 根据添加程序DTO添加相应任务
	 * @param targetAddDTO
	 * @param templateId
	 */
	public void configurationNodeTemplate(TargetAddDTO targetAddDTO, Integer templateId) {
		TaskNodeTemplateDTO taskNodeTemplateDTO = new TaskNodeTemplateDTO();
		taskNodeTemplateDTO.setCaseeId(targetAddDTO.getCaseeId());
		taskNodeTemplateDTO.setInsId(securityUtilsService.getCacheUser().getInsId());
		taskNodeTemplateDTO.setOutlesId(securityUtilsService.getCacheUser().getOutlesId());
		taskNodeTemplateDTO.setTargetId(targetAddDTO.getTargetId());
		taskNodeTemplateDTO.setTemplateId(templateId);
		taskNodeTemplateDTO.setProjectId(targetAddDTO.getProjectId());

		//根据性质类型，将实体转换成json
		net.sf.json.JSONObject jsonObject = setTargetAddDTO(targetAddDTO);

		//生成任务
		taskNodeService.queryNodeTemplateAddTaskNode(taskNodeTemplateDTO, jsonObject);
		//添加默认程序数据（表单id、节点顺序、节点名称等）
		targetAddDTO.setBusinessData(jsonObject.toString());
		this.baseMapper.updateById(targetAddDTO);

	}

	/**
	 * 根据案件类型分页查询立案未送达
	 *
	 * @param page
	 * @param targetCaseeProjectPageDTO
	 * @return
	 */
	@Override
	public IPage<TargetCaseeProjectPageVO> standCaseUndeliveredPage(Page page, TargetCaseeProjectPageDTO targetCaseeProjectPageDTO) {
		return this.baseMapper.standCaseUndeliveredPage(page, targetCaseeProjectPageDTO, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	@Override
	public List<TaskNodeVO> getTarget(Integer caseeId, Integer procedureNature,Integer id) {
		return this.baseMapper.getTarget(caseeId,procedureNature,id);
	}

	/**
	 * 根据性质类型，将实体转换成json
	 *
	 * @param targetAddDTO
	 * @throws Exception
	 */

	private net.sf.json.JSONObject setTargetAddDTO(TargetAddDTO targetAddDTO) {
		net.sf.json.JSONObject jsonObject=null;

		switch (targetAddDTO.getProcedureNature()){
			case 1010:
				LiQui_SQ liQui_sq=new LiQui_SQ();
				jsonObject= net.sf.json.JSONObject.fromObject(liQui_sq);
				break;
			case 2010:
				LiQui_SSBQ liQui_ssbq=new LiQui_SSBQ();
				jsonObject= net.sf.json.JSONObject.fromObject(liQui_ssbq);
				break;
			case 2020:
				LiQui_SSYS liQui_ssys=new LiQui_SSYS();
				jsonObject= net.sf.json.JSONObject.fromObject(liQui_ssys);
				break;
			case 2021:
				LiQui_SSES liQui_sses=new LiQui_SSES();
				jsonObject= net.sf.json.JSONObject.fromObject(liQui_sses);
				break;
			case 2030:
				LiQui_SSQT liQui_ssqt=new LiQui_SSQT();
				jsonObject= net.sf.json.JSONObject.fromObject(liQui_ssqt);
				break;
			case 3010:
				LiQui_ZXSZ liQui_zxsz=new LiQui_ZXSZ();
				jsonObject= net.sf.json.JSONObject.fromObject(liQui_zxsz);
				break;
			case 3031:
				LiQui_ZXZH liQui_zxzh=new LiQui_ZXZH();
				jsonObject= net.sf.json.JSONObject.fromObject(liQui_zxzh);
				break;
			case 4040:
				EntityZX entityZX=new EntityZX();
				jsonObject= net.sf.json.JSONObject.fromObject(entityZX);
				break;
			case 4041:
				FundingZX fundingZX=new FundingZX();
				jsonObject= net.sf.json.JSONObject.fromObject(fundingZX);
				break;
			case 5050:
				Limit limit=new Limit();
				jsonObject= net.sf.json.JSONObject.fromObject(limit);
				break;
			case 5051:
				BeIllegal beIllegal=new BeIllegal();
				jsonObject= net.sf.json.JSONObject.fromObject(beIllegal);
				break;
			case 6060:

				break;
			case 7070:
				LiQui_LX liQui_lx=new LiQui_LX();
				jsonObject= net.sf.json.JSONObject.fromObject(liQui_lx);
				break;
		}
		return jsonObject;
	}
}
