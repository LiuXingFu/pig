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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.SysDept;
import com.pig4cloud.pig.admin.api.entity.SysDeptRelation;
import com.pig4cloud.pig.admin.api.vo.DeptVO;
import com.pig4cloud.pig.admin.mapper.SysDeptMapper;
import com.pig4cloud.pig.admin.service.SysDeptRelationService;
import com.pig4cloud.pig.admin.service.SysDeptService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

	private final SysDeptRelationService sysDeptRelationService;

	/**
	 * 添加信息部门
	 * @param dept 部门
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveDept(SysDept dept) {
		// 1.判断当前机构是否为空
		if(Objects.isNull(dept.getInsId())){
			// 1.1为空将当前登录的机构id存入部门对象中
			dept.setInsId(SecurityUtils.getUser().getInsId());
		}
		this.save(dept);
		sysDeptRelationService.saveDeptRelation(dept);
		return Boolean.TRUE;
	}

	/**
	 * 删除部门
	 * @param id 部门 ID
	 * @return 成功、失败
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean removeDeptById(Integer id) {
		// 级联删除部门
		List<Integer> idList = sysDeptRelationService
				.list(Wrappers.<SysDeptRelation>query().lambda().eq(SysDeptRelation::getAncestor, id)).stream()
				.map(SysDeptRelation::getDescendant).collect(Collectors.toList());

		if (CollUtil.isNotEmpty(idList)) {
			this.removeByIds(idList);
		}

		// 删除部门级联关系
		sysDeptRelationService.removeDeptRelationById(id);
		return Boolean.TRUE;
	}

	/**
	 * 更新部门
	 * @param sysDept 部门信息
	 * @return 成功、失败
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateDeptById(SysDept sysDept) {
		// 更新部门状态
		this.updateById(sysDept);
		// 更新部门关系
		SysDeptRelation relation = new SysDeptRelation();
		relation.setAncestor(sysDept.getParentId());
		relation.setDescendant(sysDept.getDeptId());
		sysDeptRelationService.updateDeptRelation(relation);
		return Boolean.TRUE;
	}

	/**
	 * 返回当前机构树形菜单集合
	 * @return
	 */
	@Override
	public List<Tree<Integer>> lisInsDeptTrees() {
		Integer insId = SecurityUtils.getUser().getInsId();
		SysDept dept = this.getOne(new LambdaQueryWrapper<SysDept>().eq(SysDept::getInsId, insId).eq(SysDept::getParentId, 0).eq(SysDept::getDelFlag, 0));
		List<Integer> list = sysDeptRelationService.list(new LambdaQueryWrapper<SysDeptRelation>().eq(SysDeptRelation::getAncestor, dept.getDeptId())).stream().map(SysDeptRelation::getDescendant).collect(Collectors.toList());
		List<SysDept> deptList = baseMapper.selectBatchIds(list);

		return getDeptTree(deptList);
	}

	@Override
	public DeptVO getByIdDept(Integer id) {
		return this.baseMapper.getByIdDept(id);
	}

	/**
	 * 查询全部部门树
	 * @return 树
	 */
	@Override
	public List<Tree<Integer>> listDeptTrees() {
		return getDeptTree(this.list(new LambdaQueryWrapper<SysDept>().eq(SysDept::getInsId,SecurityUtils.getUser().getInsId()).eq(SysDept::getDelFlag, 0)));
	}

	/**
	 * 查询用户部门树
	 * @return
	 */
	@Override
	public List<Tree<Integer>> listCurrentUserDeptTrees() {
		Integer deptId = SecurityUtils.getUser().getDeptId();
		List<Integer> descendantIdList = sysDeptRelationService
				.list(Wrappers.<SysDeptRelation>query().lambda().eq(SysDeptRelation::getAncestor, deptId)).stream()
				.map(SysDeptRelation::getDescendant).collect(Collectors.toList());
		List<SysDept> deptList = baseMapper.selectBatchIds(descendantIdList);
		return getDeptTree(deptList);
	}

	/**
	 * 构建部门树
	 * @param depts 部门
	 * @return
	 */
	private List<Tree<Integer>> getDeptTree(List<SysDept> depts) {
		List<TreeNode<Integer>> collect = depts.stream()
				.filter(dept -> dept.getDeptId().intValue() != dept.getParentId())
				.sorted(Comparator.comparingInt(SysDept::getSort)).map(dept -> {
					TreeNode<Integer> treeNode = new TreeNode();
					treeNode.setId(dept.getDeptId());
					treeNode.setParentId(dept.getParentId());
					treeNode.setName(dept.getName());
					return treeNode;
				}).collect(Collectors.toList());

		return TreeUtil.build(collect, 0);
	}

}
