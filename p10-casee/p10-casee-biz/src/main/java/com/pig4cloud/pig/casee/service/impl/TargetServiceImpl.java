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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.TaskNodeTemplateDTO;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.admin.api.feign.RemoteOutlesTemplateReService;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.Target;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu;
import com.pig4cloud.pig.casee.entity.project.beillegalprocedure.BeIllegal;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX;
import com.pig4cloud.pig.casee.entity.project.fundingzxprocedure.FundingZX;
import com.pig4cloud.pig.casee.entity.project.limitprocedure.Limit;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.LX.LiQuiLX;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SQ.LiQuiSQ;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSBQ.LiQuiSSBQ;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES.LiQuiSSES;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSQT.LiQuiSSQT;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS.LiQuiSSYS;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXSZ.LiQuiZXSZ;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXZH.LiQuiZXZH;
import com.pig4cloud.pig.casee.mapper.TargetMapper;
import com.pig4cloud.pig.casee.service.AssetsService;
import com.pig4cloud.pig.casee.service.TargetService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.casee.vo.TargetCaseeProjectPageVO;
import com.pig4cloud.pig.casee.vo.TargetPageVO;
import com.pig4cloud.pig.casee.vo.TaskNodeVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
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

/**
 * ?????????
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
	@Autowired
	AssetsService assetsService;

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
	 * ????????????DTO?????????????????????????????????
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

		// ??????
		R<TaskNodeTemplate> taskNodeTemplate = remoteOutlesTemplateReService.queryTemplateByTemplateNature(targetAddDTO.getProcedureNature(),targetAddDTO.getOutlesId() , SecurityConstants.FROM);
		//????????????????????????????????????id??????????????????????????????????????????
		if (taskNodeTemplate.getData() == null) {
			//??????????????????????????????????????????????????????
			throw new RuntimeException("?????????????????????????????????");
		}
		//??????????????????
		configurationNodeTemplate(targetAddDTO, taskNodeTemplate.getData().getTemplateId());
		return 1;
	}

	/**
	 * ??????????????????DTO??????????????????
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

		//???????????????????????????????????????json
		net.sf.json.JSONObject jsonObject = setTargetAddDTO(targetAddDTO);

		//????????????
		taskNodeService.queryNodeTemplateAddTaskNode(taskNodeTemplateDTO, jsonObject);
		//?????????????????????????????????id????????????????????????????????????
		targetAddDTO.setBusinessData(jsonObject.toString());
		this.baseMapper.updateById(targetAddDTO);

	}

	@Override
	public List<TaskNodeVO> getTarget(Integer projectId,Integer caseeId, Integer procedureNature,Integer id) {
		return this.baseMapper.getTarget(projectId,caseeId,procedureNature,id);
	}

	/**
	 * ???????????????????????????????????????json
	 *
	 * @param targetAddDTO
	 * @throws Exception
	 */

	private net.sf.json.JSONObject setTargetAddDTO(TargetAddDTO targetAddDTO) {
		net.sf.json.JSONObject jsonObject=null;

		switch (targetAddDTO.getProcedureNature()){
			case 1010:
				LiQuiSQ liQuiSQ=new LiQuiSQ();
				jsonObject= net.sf.json.JSONObject.fromObject(liQuiSQ);
				break;
			case 2010:
				LiQuiSSBQ liQuiSSBQ=new LiQuiSSBQ();
				jsonObject= net.sf.json.JSONObject.fromObject(liQuiSSBQ);
				break;
			case 2020:
				LiQuiSSYS liQuiSSYS=new LiQuiSSYS();
				jsonObject= net.sf.json.JSONObject.fromObject(liQuiSSYS);
				break;
			case 2021:
				LiQuiSSES liQuiSSES=new LiQuiSSES();
				jsonObject= net.sf.json.JSONObject.fromObject(liQuiSSES);
				break;
			case 2030:
				LiQuiSSQT liQuiSSQT=new LiQuiSSQT();
				jsonObject= net.sf.json.JSONObject.fromObject(liQuiSSQT);
				break;
			case 3010:
				LiQuiZXSZ liQuiZXSZ=new LiQuiZXSZ();
				jsonObject= net.sf.json.JSONObject.fromObject(liQuiZXSZ);
				break;
			case 3031:
				LiQuiZXZH liQuiZXZH=new LiQuiZXZH();
				jsonObject= net.sf.json.JSONObject.fromObject(liQuiZXZH);
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
				PaiFu paiFu=new PaiFu();
				jsonObject= net.sf.json.JSONObject.fromObject(paiFu);
				break;
			case 7070:
				LiQuiLX liQuiLX=new LiQuiLX();
				jsonObject= net.sf.json.JSONObject.fromObject(liQuiLX);
				break;
		}
		return jsonObject;
	}
}
