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
import com.pig4cloud.pig.casee.dto.AuditTargetDTO;
import com.pig4cloud.pig.casee.dto.TargetAddDTO;
import com.pig4cloud.pig.casee.dto.TargetThingAddDTO;
import com.pig4cloud.pig.casee.dto.TargetPageDTO;
import com.pig4cloud.pig.casee.entity.Target;
import com.pig4cloud.pig.casee.entity.project.beillegalprocedure.BeIllegal;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX;
import com.pig4cloud.pig.casee.entity.project.fundingzxprocedure.FundingZX;
import com.pig4cloud.pig.casee.entity.project.limitprocedure.Limit;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SQ.LiQuiSQ;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSBQ.LiQuiSSBQ;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES.LiQuiSSES;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSQT.LiQuiSSQT;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS.LiQuiSSYS;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXSZ.LiQuiZXSZ;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXZH.LiQuiZXZH;
import com.pig4cloud.pig.casee.mapper.TargetMapper;
import com.pig4cloud.pig.casee.service.TargetService;
import com.pig4cloud.pig.casee.vo.TargetPageVO;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.KeyValue;
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
	 * 根据添加程序DTO添加相应程序
	 * @param targetAddDTO
	 * @return
	 * @throws Exception
	 */
	@Override
	public TargetAddDTO saveTargetAddDTO(TargetAddDTO targetAddDTO) throws Exception {
		setTargetAddDTO(targetAddDTO);

		this.save(targetAddDTO);

		return targetAddDTO;
	}

	/**
	 * 根据程序DTO集合，添加相应集合程序
	 * @param targetAddDTOList
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public Boolean saveTargetAddDTO(List<TargetAddDTO> targetAddDTOList) throws Exception {

		List<Target> target = new ArrayList<>();

		if(Objects.nonNull(targetAddDTOList)) {

			targetAddDTOList.forEach(((targetAddDTO) -> {
				try {
					setTargetAddDTO(targetAddDTO);
					target.add(targetAddDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}));
		} else {
			throw new RuntimeException("程序集合参数异常");
		}

		return this.saveBatch(target);
	}

	/**
	 * 根据性质类型，将实体转换成json
	 * @param targetAddDTO
	 * @throws Exception
	 */

	private void setTargetAddDTO(TargetAddDTO targetAddDTO) throws Exception {
		if (Objects.nonNull(targetAddDTO)) {
			if (targetAddDTO.getProcedureNature().equals(Integer.valueOf("10000"))) {
				targetAddDTO.setTargetName("诉前保全程序");
				String json = JsonUtils.objectToJsonObject(new LiQuiSQ());
				targetAddDTO.setBusinessData(json);
			} else if (targetAddDTO.getProcedureNature().equals(Integer.valueOf("10001"))) {
				targetAddDTO.setTargetName("诉讼保全程序");
				String json = JsonUtils.objectToJsonObject(new LiQuiSSBQ());
				targetAddDTO.setBusinessData(json);
			} else if (targetAddDTO.getProcedureNature().equals(Integer.valueOf("10002"))) {
				targetAddDTO.setTargetName("一审诉讼程序");
				String json = JsonUtils.objectToJsonObject(new LiQuiSSYS());
				targetAddDTO.setBusinessData(json);
			} else if (targetAddDTO.getProcedureNature().equals(Integer.valueOf("10003"))) {
				targetAddDTO.setTargetName("二审诉讼程序");
				String json = JsonUtils.objectToJsonObject(new LiQuiSSES());
				targetAddDTO.setBusinessData(json);
			} else if (targetAddDTO.getProcedureNature().equals(Integer.valueOf("10004"))) {
				targetAddDTO.setTargetName("其它诉讼程序");
				String json = JsonUtils.objectToJsonObject(new LiQuiSSQT());
				targetAddDTO.setBusinessData(json);
			} else if (targetAddDTO.getProcedureNature().equals(Integer.valueOf("10005"))) {
				targetAddDTO.setTargetName("首次执行程序");
				String json = JsonUtils.objectToJsonObject(new LiQuiZXSZ());
				targetAddDTO.setBusinessData(json);
			} else if (targetAddDTO.getProcedureNature().equals(Integer.valueOf("10006"))) {
				targetAddDTO.setTargetName("执恢程序");
				String json = JsonUtils.objectToJsonObject(new LiQuiZXZH());
				targetAddDTO.setBusinessData(json);
			} else if (targetAddDTO.getProcedureNature().equals(Integer.valueOf("20002"))) {
				targetAddDTO.setTargetName("执行实体财产程序");
				String json = JsonUtils.objectToJsonObject(new EntityZX());
				targetAddDTO.setBusinessData(json);
			} else if (targetAddDTO.getProcedureNature().equals(Integer.valueOf("20003"))) {
				targetAddDTO.setTargetName("执行资金财产程序");
				String json = JsonUtils.objectToJsonObject(new FundingZX());
				targetAddDTO.setBusinessData(json);
			} else if (targetAddDTO.getProcedureNature().equals(Integer.valueOf("20004"))) {
				targetAddDTO.setTargetName("行为限制程序");
				String json = JsonUtils.objectToJsonObject(new Limit());
				targetAddDTO.setBusinessData(json);
			} else if (targetAddDTO.getProcedureNature().equals(Integer.valueOf("20005"))) {
				targetAddDTO.setTargetName("行为违法程序");
				String json = JsonUtils.objectToJsonObject(new BeIllegal());
				targetAddDTO.setBusinessData(json);
			} else {
				throw new RuntimeException("程序类型不存在！");
			}
		} else {
			throw new RuntimeException("程序实体参数异常！");
		}
	}
}
