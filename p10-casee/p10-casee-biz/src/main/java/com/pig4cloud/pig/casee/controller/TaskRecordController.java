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

package com.pig4cloud.pig.casee.controller;

import com.pig4cloud.pig.casee.dto.TaskFlowDTO;
import com.pig4cloud.pig.casee.entity.TaskRecord;
import com.pig4cloud.pig.casee.service.TaskRecordService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 任务记录表
 *
 * @author Mjh
 * @date 2021-09-15 10:03:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/taskRecord" )
@Api(value = "taskRecord", tags = "任务记录表")
public class TaskRecordController {

    private final TaskRecordService taskRecordService;

    /**
     * 分页查询
     * @param taskFlowDTO
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getProcessedTaskPage(TaskFlowDTO taskFlowDTO) {
        return R.ok(taskRecordService.taskRecordList(taskFlowDTO.getPage(),taskFlowDTO));
    }


	/**
     * 通过id查询任务记录表
     * @param ProcessedTaskId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{ProcessedTaskId}" )
    public R getById(@PathVariable("ProcessedTaskId" ) Integer ProcessedTaskId) {
        return R.ok(taskRecordService.getById(ProcessedTaskId));
    }

    /**
     * 新增任务记录表
	 *
     * @param taskRecord 任务记录表
     * @return R
     */
    @ApiOperation(value = "新增任务记录表", notes = "新增任务记录表")
    @SysLog("新增任务记录表" )
    @PostMapping
    public R save(@RequestBody TaskRecord taskRecord) {
        return R.ok(taskRecordService.save(taskRecord));
    }

    /**
     * 修改任务记录表
     * @param taskRecord 任务记录表
     * @return R
     */
    @ApiOperation(value = "修改任务记录表", notes = "修改任务记录表")
    @SysLog("修改任务记录表" )
    @PutMapping
    public R updateById(@RequestBody TaskRecord taskRecord) {
        return R.ok(taskRecordService.updateById(taskRecord));
    }

}
