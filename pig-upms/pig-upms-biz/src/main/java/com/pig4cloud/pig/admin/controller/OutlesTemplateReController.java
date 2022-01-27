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
import com.pig4cloud.pig.admin.api.dto.OutlesTemplateReDTO;
import com.pig4cloud.pig.admin.api.entity.OutlesTemplateRe;
import com.pig4cloud.pig.admin.service.OutlesTemplateReService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 网点任务模板关联表
 *
 * @author Mjh
 * @date 2021-12-17 11:31:08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/outlestemplatere" )
@Api(value = "outlestemplatere", tags = "网点任务模板关联表管理")
public class OutlesTemplateReController {

    private final OutlesTemplateReService outlesTemplateReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param outlesTemplateRe 网点任务模板关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOutlesTemplateRePage(Page page, OutlesTemplateRe outlesTemplateRe) {
        return R.ok(outlesTemplateReService.page(page, Wrappers.query(outlesTemplateRe)));
    }


    /**
     * 通过id查询网点任务模板关联表
     * @param templateReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{templateReId}" )
    public R getById(@PathVariable("templateReId" ) Integer templateReId) {
        return R.ok(outlesTemplateReService.getById(templateReId));
    }

    /**
     * 新增网点任务模板关联表
     * @param outlesTemplateRe 网点任务模板关联表
     * @return R
     */
    @ApiOperation(value = "新增网点任务模板关联表", notes = "新增网点任务模板关联表")
    @SysLog("新增网点任务模板关联表" )
    @PostMapping
    public R save(@RequestBody OutlesTemplateRe outlesTemplateRe) {
        return R.ok(outlesTemplateReService.save(outlesTemplateRe));
    }

    /**
     * 修改网点任务模板关联表
     * @param outlesTemplateRe 网点任务模板关联表
     * @return R
     */
    @ApiOperation(value = "修改网点任务模板关联表", notes = "修改网点任务模板关联表")
    @SysLog("修改网点任务模板关联表" )
    @PutMapping
    public R updateById(@RequestBody OutlesTemplateRe outlesTemplateRe) {
        return R.ok(outlesTemplateReService.updateById(outlesTemplateRe));
    }

    /**
     * 通过id删除网点任务模板关联表
     * @param templateReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除网点任务模板关联表", notes = "通过id删除网点任务模板关联表")
    @SysLog("通过id删除网点任务模板关联表" )
    @DeleteMapping("/{templateReId}" )
    public R removeById(@PathVariable Integer templateReId) {
        return R.ok(outlesTemplateReService.removeById(templateReId));
    }

	/**
	 * 配置网点模板
	 *
	 * @param outlesTemplateReDTO
	 * @return R
	 */
	@ApiOperation(value = "配置网点模板", notes = "配置网点模板")
	@SysLog("配置网点模板")
	@RequestMapping("/configureOutlesTemplate")
	public R configureOutlesTemplate(@RequestBody OutlesTemplateReDTO outlesTemplateReDTO) {
		return R.ok(outlesTemplateReService.configureOutlesTemplate(outlesTemplateReDTO));
	}

	/**
	 * 通过网点id查询网点任务模板关联表
	 * @param outlesId
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getByOutlesId/{outlesId}" )
	public R getByOutlesId(@PathVariable("outlesId") Integer outlesId) {
		return R.ok(outlesTemplateReService.getByOutlesId(outlesId));
	}

	/**
	 * 通过模板性质查询网点模板信息
	 * @param templateNature
	 * @return R
	 */
	@ApiOperation(value = "通过模板性质查询网点模板信息", notes = "通过模板性质查询网点模板信息")
	@GetMapping("/queryTemplateByTemplateNature" )
	public R queryTemplateByTemplateNature(Integer templateNature,Integer outlesId) {
		return R.ok(outlesTemplateReService.queryTemplateByTemplateNature(templateNature,outlesId));
	}

}
