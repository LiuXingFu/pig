/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Institution;
import com.pig4cloud.pig.admin.api.entity.SysDictItem;
import com.pig4cloud.pig.admin.api.entity.SysRole;
import com.pig4cloud.pig.admin.api.entity.SysRoleMenu;
import com.pig4cloud.pig.admin.mapper.SysRoleMapper;
import com.pig4cloud.pig.admin.mapper.SysRoleMenuMapper;
import com.pig4cloud.pig.admin.service.InstitutionService;
import com.pig4cloud.pig.admin.service.SysDictItemService;
import com.pig4cloud.pig.admin.service.SysRoleService;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * ???????????????
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	private final SysRoleMenuMapper sysRoleMenuMapper;

	@Autowired
	private InstitutionService institutionService;

	@Autowired
	private SysDictItemService dictItemService;

	@Autowired
	private SecurityUtilsService securityUtilsService;

	/**
	 * ????????????ID?????????????????????
	 * @param userId
	 * @return
	 */
	@Override
	public List<SysRole> findRolesByUserId(Integer userId) {
		return baseMapper.listRolesByUserId(userId);
	}

	/**
	 * ????????????ID???????????????,???????????????????????????
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
	public Boolean removeRoleById(Integer id) {
		sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>update().lambda().eq(SysRoleMenu::getRoleId, id));
		return this.removeById(id);
	}

	@Override
	public IPage getRolePage(Page page, SysRole sysRole) {
		return this.baseMapper.getRoleVosPage(page,sysRole);
	}

	/**
	 * ????????????????????????ID?????????????????????
	 * @return
	 */
	@Override
	public List<SysRole> queryRoleList() {
		// 1.??????token?????????????????????id
		Integer insId = securityUtilsService.getCacheUser().getInsId();
		// 2.????????????????????????id????????????????????????
		Institution institution = institutionService.getOne(new LambdaQueryWrapper<Institution>().eq(Institution::getInsId, insId).eq(Institution::getDelFlag, 0));
		// 3.????????????????????????
		if (institution!=null){
			// 3.1???????????????????????????????????????????????????
			SysDictItem dictItem=new SysDictItem();
			dictItem.setType("ins_type");
			dictItem.setValue(institution.getInsType().toString());
			dictItem.setDelFlag("0");
			SysDictItem dictBySysDictItem = dictItemService.getDictBySysDictItem(dictItem);
			List<SysRole> sysRoleList = this.list(new QueryWrapper<SysRole>().like("role_code", dictBySysDictItem.getLabel()));
			return sysRoleList;
		}
		return null;
	}


	@Override
	public SysRole queryByUserIdList(Integer userId,Integer insId,Integer outlesId,String roleCode){
		return this.baseMapper.selectByUserId(userId,insId,outlesId,roleCode);
	}

}
