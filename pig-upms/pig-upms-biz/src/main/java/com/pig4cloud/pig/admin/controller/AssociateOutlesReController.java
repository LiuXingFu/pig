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

package com.pig4cloud.pig.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.AssociateOutlesReDTO;
import com.pig4cloud.pig.admin.api.entity.AssociateOutlesRe;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.admin.service.AssociateOutlesReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 机构关联网点表
 *
 * @author yuanduo
 * @date 2021-09-03 11:09:36
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/associateoutles" )
@Api(value = "associateoutles", tags = "机构关联网点表管理")
public class AssociateOutlesReController {

    private final AssociateOutlesReService associateOutlesReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param associateOutlesRe 机构关联网点表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAssociateOutlesPage(Page page, AssociateOutlesRe associateOutlesRe) {
        return R.ok(associateOutlesReService.pageAssociateOutles(page, associateOutlesRe));
    }

	/**
	 * 分页查询合作网点
	 * @param page
	 * @param associateOutlesRe
	 * @return
	 */
	@ApiOperation(value = "分页查询合作网点", notes = "分页查询合作网点")
	@GetMapping("/queryCooperateOutlesPage" )
    public R queryCooperateOutlesPage(Page page, AssociateOutlesRe associateOutlesRe) {
    	return R.ok(associateOutlesReService.queryCooperateOutlesPage(page, associateOutlesRe));
	}

	/**
	 * 根据associateID查询授权列表
	 * @param insAssociateId 合作机构id
	 * @param  outlesName 网点名称
	 * @return
	 */
	@ApiOperation(value = "根据associateID查询授权列表", notes = "分页查询")
	@GetMapping("/getAuthorizationPage" )
	public R getAuthorizationPage(Integer insAssociateId, String outlesName) throws Exception {
		return R.ok(associateOutlesReService.getAuthorizationPage(insAssociateId, outlesName));
	}


    /**
     * 通过id查询机构关联网点表
     * @param associateId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{associateId}" )
//    @PreAuthorize("@pms.hasPermission('generator_associateoutles_get')" )
    public R getById(@PathVariable("associateId" ) Integer associateId) {
        return R.ok(associateOutlesReService.getById(associateId));
    }

	/**
	 * 新增机构授权网点
	 * @param associateOutlesDTO 机构关联网点表
	 * @return R
	 */
    @ApiOperation(value = "新增机构关联网点表", notes = "新增机构关联网点表")
    @SysLog("新增机构关联网点表" )
    @PostMapping
    public R save(@RequestBody AssociateOutlesReDTO associateOutlesDTO) throws Exception {
		boolean save = associateOutlesReService.saveAssociateOutles(associateOutlesDTO);
		if (save){
			return R.ok("添加成功！");
		} else {
			return R.failed("添加失败！");
		}
    }

    /**
     * 修改机构关联网点表
     * @param associateOutlesRe 机构关联网点表
     * @return R
     */
    @ApiOperation(value = "修改机构关联网点表", notes = "修改机构关联网点表")
    @SysLog("修改机构关联网点表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('generator_associateoutles_edit')" )
    public R updateById(@RequestBody AssociateOutlesRe associateOutlesRe) {
        return R.ok(associateOutlesReService.updateById(associateOutlesRe));
    }

    /**
     * 通过id删除机构关联网点表
     * @param associateId id
     * @return R
     */
    @ApiOperation(value = "通过id删除机构关联网点表", notes = "通过id删除机构关联网点表")
    @SysLog("通过id删除机构关联网点表" )
    @DeleteMapping("/{associateId}" )
    @PreAuthorize("@pms.hasPermission('generator_associateoutles_del')" )
    public R removeById(@PathVariable Integer associateId) {
        return R.ok(associateOutlesReService.removeById(associateId));
    }

	/**
	 * 解除网点授权关系
	 * @param associateOutlesId
	 * @return R
	 */
	@ApiOperation(value = "解除网点授权关系", notes = "解除网点授权关系")
	@SysLog("解除网点授权关系" )
	@DeleteMapping("/dismissById/{associateOutlesId}" )
	public R dismissById(@PathVariable Integer associateOutlesId) {
		boolean b = associateOutlesReService.dismissById(associateOutlesId);
		if(b) {
			return R.ok("解除网点授权成功！");
		} else {
			return R.failed("解除网点授权失败！");
		}
	}

}
