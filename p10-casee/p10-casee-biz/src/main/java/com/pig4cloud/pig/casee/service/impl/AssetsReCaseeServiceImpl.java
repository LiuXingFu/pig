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
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.casee.dto.AssetsAddDTO;
import com.pig4cloud.pig.casee.dto.AssetsDTO;
import com.pig4cloud.pig.casee.dto.TargetAddDTO;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.assets.AssetsReCasee;
import com.pig4cloud.pig.casee.mapper.AssetsReCaseeMapper;
import com.pig4cloud.pig.casee.mapper.AssetsReMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.CaseeOrAssetsVO;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
@Service
public class AssetsReCaseeServiceImpl extends ServiceImpl<AssetsReCaseeMapper, AssetsRe> implements AssetsReCaseeService {

	@Autowired
	AssetsService assetsService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private TargetService targetService;

	@Override
	@Transactional
	public Integer saveAssetsCasee(AssetsAddDTO assetsAddDTO)throws Exception{
		Integer assetsId = assetsAddDTO.getAssetsId();
		// 财产不存在保存财产信息及相关联信息
		if(Objects.isNull(assetsId)){
			Assets assets = new Assets();
			BeanCopyUtil.copyBean(assetsAddDTO,assets);
			assetsService.save(assets);
			assetsId = assets.getAssetsId();
			assetsAddDTO.setAssetsId(assets.getAssetsId());
			// 保存财产地址信息
			if(Objects.nonNull(assetsAddDTO.getCode())){
				Address address = new Address();
				// 4=财产地址
				address.setType(4);
				address.setUserId(assets.getAssetsId());
				BeanCopyUtil.copyBean(assetsAddDTO,address);
			}
		}
		AssetsReCasee assetsReCasee = new AssetsReCasee();
		// 财产来源2=案件
		assetsReCasee.setAssetsSource(2);
		assetsReCasee.setCreateCaseeId(assetsAddDTO.getCaseeId());
		BeanCopyUtil.copyBean(assetsAddDTO,assetsReCasee);

		//添加任务数据以及程序信息
		Project project = projectLiquiService.getById(assetsAddDTO.getProjectId());
		TargetAddDTO targetAddDTO=new TargetAddDTO();
		if (assetsAddDTO.getType()==20100){//资金财产
			targetAddDTO.setProcedureNature(4041);
		}else if (assetsAddDTO.getType()==20200){//实体财产
			targetAddDTO.setProcedureNature(4040);
		}
		targetAddDTO.setCaseeId(assetsAddDTO.getCaseeId());
		targetAddDTO.setOutlesId(project.getOutlesId());
		targetAddDTO.setProjectId(assetsAddDTO.getProjectId());
		targetAddDTO.setGoalType(20001);
		targetAddDTO.setGoalId(assetsId);
		targetService.saveTargetAddDTO(targetAddDTO);
		return this.baseMapper.insert(assetsReCasee);
	}

}
