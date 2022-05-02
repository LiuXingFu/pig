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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.AddInsOutlesCourtReDTO;
import com.pig4cloud.pig.admin.api.dto.InsOutlesCourtRePageDTO;
import com.pig4cloud.pig.admin.api.entity.InsOutlesCourtRe;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.admin.service.InsOutlesCourtReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 机构网点法院关联表
 *
 * @author yuanduo
 * @date 2022-04-28 19:56:42
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/insoutlescourtre" )
@Api(value = "insoutlescourtre", tags = "机构网点法院关联表管理")
public class InsOutlesCourtReController {

    private final  InsOutlesCourtReService insOutlesCourtReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param insOutlesCourtRe 机构网点法院关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getInsOutlesCourtRePage(Page page, InsOutlesCourtRe insOutlesCourtRe) {
        return R.ok(insOutlesCourtReService.page(page, Wrappers.query(insOutlesCourtRe)));
    }


    /**
     * 通过id查询机构网点法院关联表
     * @param insOutlesCourtReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{insOutlesCourtReId}" )
    public R getById(@PathVariable("insOutlesCourtReId" ) Integer insOutlesCourtReId) {
        return R.ok(insOutlesCourtReService.getById(insOutlesCourtReId));
    }

    /**
     * 新增机构网点法院关联表
     * @param insOutlesCourtRe 机构网点法院关联表
     * @return R
     */
    @ApiOperation(value = "新增机构网点法院关联表", notes = "新增机构网点法院关联表")
    @SysLog("新增机构网点法院关联表" )
    @PostMapping
    public R save(@RequestBody InsOutlesCourtRe insOutlesCourtRe) {
        return R.ok(insOutlesCourtReService.save(insOutlesCourtRe));
    }

    /**
     * 修改机构网点法院关联表
     * @param insOutlesCourtRe 机构网点法院关联表
     * @return R
     */
    @ApiOperation(value = "修改机构网点法院关联表", notes = "修改机构网点法院关联表")
    @SysLog("修改机构网点法院关联表" )
    @PutMapping
    public R updateById(@RequestBody InsOutlesCourtRe insOutlesCourtRe) {
        return R.ok(insOutlesCourtReService.updateById(insOutlesCourtRe));
    }

    /**
     * 通过id删除机构网点法院关联表
     * @param insOutlesCourtReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除机构网点法院关联表", notes = "通过id删除机构网点法院关联表")
    @SysLog("通过id删除机构网点法院关联表" )
    @DeleteMapping("/{insOutlesCourtReId}" )
    public R removeById(@PathVariable Integer insOutlesCourtReId) {
        return R.ok(insOutlesCourtReService.removeById(insOutlesCourtReId));
    }

	/**
	 * 分页查询机构网点法院关联
	 * @param page
	 * @return
	 */
	@ApiOperation(value = "分页查询机构网点法院关联", notes = "分页查询机构网点法院关联")
	@GetMapping("/queryInsOutlesCourtPage" )
	public R queryInsOutlesCourtPage(Page page, InsOutlesCourtRePageDTO insOutlesCourtRePageDTO) {
    	return R.ok(insOutlesCourtReService.queryInsOutlesCourtPage(page, insOutlesCourtRePageDTO));
	}

	/**
	 * 根据机构id与法院id查询机构网点法院关联表信息
	 * @param insId
	 * @param courtId
	 * @return
	 */
	@ApiOperation(value = "根据机构id与法院id查询机构网点法院关联表信息", notes = "根据机构id与法院id查询机构网点法院关联表信息")
	@GetMapping("/queryInsOutlesCourtReByInsIdAndCourtId" )
    public R queryInsOutlesCourtReByInsIdAndCourtId(Integer insId, Integer courtId){
		return R.ok(insOutlesCourtReService.queryInsOutlesCourtReByInsIdAndCourtId(insId, courtId));
	}

	/**
	 * 将入驻网点id集合入驻到法院机构绑定的法院
	 * @param addInsOutlesCourtReDTO
	 * @return
	 */
	@ApiOperation(value = "将入驻网点id集合入驻到法院机构绑定的法院", notes = "将入驻网点id集合入驻到法院机构绑定的法院")
	@SysLog("将入驻网点id集合入驻到法院机构绑定的法院" )
	@PostMapping("/addInsOutlesCourtRe")
	public R addInsOutlesCourtRe(@RequestBody AddInsOutlesCourtReDTO addInsOutlesCourtReDTO){
		int count = insOutlesCourtReService.addInsOutlesCourtRe(addInsOutlesCourtReDTO);

		if (count > 0) {
			return R.ok("将入驻网点id集合入驻到法院机构绑定的法院成功！");
		} else {
			return R.failed("将入驻网点id集合入驻到法院机构绑定的法院失败！");
		}
	}

}
