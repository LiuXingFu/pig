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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.SubjectAddressDTO;
import com.pig4cloud.pig.admin.service.SubjectService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.admin.api.entity.Subject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 主体
 *
 * @author yy
 * @date 2021-09-17 16:55:57
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/subject" )
@Api(value = "subject", tags = "主体管理")
public class SubjectController {

    private final SubjectService subjectService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param subject 主体
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSubjectPage(Page page, Subject subject) {
        return R.ok(subjectService.page(page, Wrappers.query(subject)));
    }


    /**
     * 通过id查询主体
     * @param subjectId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{subjectId}" )
    public R getById(@PathVariable("subjectId" ) Integer subjectId) {
        return R.ok(subjectService.getById(subjectId));
    }

	/**
	 * 通过身份证查询主体信息
	 * @param unifiedIdentity 身份证
	 * @return R
	 */
	@ApiOperation(value = "通过身份证查询主体信息", notes = "通过身份证查询主体信息")
	@GetMapping("/getByUnifiedIdentity/{unifiedIdentity}" )
	public R getByUnifiedIdentity(@PathVariable("unifiedIdentity" ) String unifiedIdentity) {
		return R.ok(subjectService.getOne(new LambdaQueryWrapper<Subject>().eq(Subject::getUnifiedIdentity,unifiedIdentity)));
	}

	/**
	 * 通过主体id集合查询主体信息
	 * @param subjectIds 主体id集合
	 * @return R
	 */
	@ApiOperation(value = "通过主体id集合查询主体信息", notes = "通过主体id集合查询主体信息")
	@GetMapping("/getByIdList" )
	public R getByIdList(@RequestParam(value = "subjectIds" ,required=false) List<Integer> subjectIds) {
		return R.ok(subjectService.listByIds(subjectIds));
	}

    /**
     * 新增主体
     * @param subject 主体
     * @return R
     */
    @ApiOperation(value = "新增主体", notes = "新增主体")
    @SysLog("新增主体" )
    @PostMapping
    public R save(@RequestBody Subject subject) {
        return R.ok(subjectService.save(subject));
    }

	/**
	 * 批量新增主体
	 * @param subjectList 主体
	 * @return R
	 */
	@ApiOperation(value = "批量新增主体", notes = "批量新增主体")
	@SysLog("批量新增主体" )
	@PostMapping("/saveBatchSubject")
	public R saveBatchSubject(@RequestBody List<Subject> subjectList) {

		return R.ok(subjectService.saveBatchSubject(subjectList));
	}

	/**
	 * 新增主体
	 * @param subject 主体
	 * @return R
	 */
	@ApiOperation(value = "新增主体", notes = "新增主体")
	@SysLog("新增主体" )
	@PostMapping("/saveSubject")
	public R saveSubject(@RequestBody Subject subject) {
		return R.ok(subjectService.saveSubject(subject));
	}

	/**
	 * 新增主体、债务人地址信息以及主体关联债务人信息
	 * @param subjectAddressDTOList 新增主体、债务人地址信息以及主体关联债务人信息
	 * @return R
	 */
	@ApiOperation(value = "新增主体、债务人地址信息以及主体关联债务人信息", notes = "新增主体、债务人地址信息以及主体关联债务人信息")
	@SysLog("新增主体" )
	@PostMapping("/saveSubjectAddress")
	public R saveSubjectAddress(@RequestBody List<SubjectAddressDTO> subjectAddressDTOList) {
		return R.ok(subjectService.saveSubjectAddress(subjectAddressDTOList));
	}

    /**
     * 修改主体
     * @param subject 主体
     * @return R
     */
    @ApiOperation(value = "修改主体", notes = "修改主体")
    @SysLog("修改主体" )
    @PutMapping
    public R updateById(@RequestBody Subject subject) {
        return R.ok(subjectService.updateById(subject));
    }

    /**
     * 通过id删除主体
     * @param subjectId id
     * @return R
     */
    @ApiOperation(value = "通过id删除主体", notes = "通过id删除主体")
    @SysLog("通过id删除主体" )
    @DeleteMapping("/{subjectId}" )
    public R removeById(@PathVariable Integer subjectId) {
        return R.ok(subjectService.removeById(subjectId));
    }

}
