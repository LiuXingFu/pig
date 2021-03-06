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

package com.pig4cloud.pig.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.dto.*;
import com.pig4cloud.pig.admin.api.entity.Outles;
import com.pig4cloud.pig.admin.api.vo.OrganizationQueryVO;
import com.pig4cloud.pig.admin.api.vo.OutlesDetailsVO;
import com.pig4cloud.pig.admin.api.vo.OutlesPageVO;
import com.pig4cloud.pig.admin.api.vo.OutlesVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 *
 * @author xiaojun
 * @date 2021-08-17 16:22:59
 */
public interface OutlesService extends IService<Outles> {

	/**
	 * 新增
	 *
	 * @param outlesDTO
	 * @return R
	 */
	int saveOutles(OutlesDTO outlesDTO);

	/**
	 * 分页查询
	 *
	 * @param page   分页对象
	 * @param outlesDTO
	 * @return
	 */
	IPage<OutlesVO> pageOutles(Page page, OutlesDTO outlesDTO);

	/**
	 * 修改
	 *
	 * @param outlesDTO
	 * @return R
	 */
	boolean updateByOutlesId(OutlesDTO outlesDTO);

	/**
	 * 通过id查询
	 *
	 * @param outlesId id
	 * @return R
	 */
	OutlesVO getByIdOutles(Integer outlesId);

	/**
	 * 通过网点id批量查询
	 *
	 * @param outlesIds id
	 * @return R
	 */
	List<OutlesVO> batchQueryOutlesId(List<Integer> outlesIds);


	/**
	 * 根据用户id查询用户网点集合
	 * @return
	 */
	List<Outles> getOutlesListByUserId();

	/**
	 * 根据员工id查询用户网点集合
	 * @return
	 */
	List<Outles> getOutlesListByStaffId();

	/**
	 * 通过机构id和网点名称查询所有网点
	 *
	 * @return R
	 */
	IPage<Outles> getInsIdOrOutlesNameList(Page page, OutlesPageDTO outlesDTO);

	/**
	 * 通过用户id和网点id查询该用户是否关联该网点
	 *
	 * @param userId,outlesId
	 * @return R
	 */
	Outles getUserIdOutlesIdByRelevance(Integer userId, Integer outlesId);

	/**************************************************/
	/**
	 * 分页查询
	 * @param page
	 * @param outlesPageDTO
	 * @return
	 */
	IPage<OutlesPageVO> queryPage(Page page, OutlesPageDTO outlesPageDTO);

	// 根据网点名称查询
	Outles queryByOutlesName(int insId,String outlesName);

	/**
	 * 添加网点及网点负责人
	 * @param outlesAddDTO
	 * @return
	 */
	int addOutles(OutlesAddDTO outlesAddDTO);

	/**
	 * 根据网点id修改网点信息
	 * @param outlesModifyDTO
	 * @return
	 */
	int modifyOutlesById(OutlesModifyDTO outlesModifyDTO);

	/**
	 * 根据网点id查询网点详情
	 * @param outlesId
	 * @return
	 */
	OutlesDetailsVO queryById(Integer outlesId);

	/**
	 * 根据机构id和用户id查询网点
	 * @param insId
	 * @param userId
	 * @return
	 */
	List<Outles> queryByUserIdList(Integer insId,Integer userId);

	Integer addDefaultOutles(Outles outles);

	Outles queryByOutlesId(Integer outlesId);

	/**
	 * 查询网点下拉框组件集合
	 * @param outlesSelectDTO
	 * @return
	 */
	public List<OrganizationQueryVO> queryOutlesIdSelect(OutlesSelectDTO outlesSelectDTO);

	List<Outles> pageOutlesList(Integer insId, String outlesName, List<Integer> outlesIds);

	/**
	 * 根据项目机构id和选中机构id查询合作网点
	 * @param projectOutlesSelectDTO
	 * @return
	 */
	List<OrganizationQueryVO> queryProjectOutlesSelect(ProjectOutlesSelectDTO projectOutlesSelectDTO);

	/**
	 * 根据网点id删除网点，并且删除下面所有员工
	 * @param outlesId
	 * @return
	 */
	Integer deleteOutlesById(Integer outlesId);


	List<Outles> queryCooperativeCourt(Integer insId, Integer courtInsId, String outlesName);
}
