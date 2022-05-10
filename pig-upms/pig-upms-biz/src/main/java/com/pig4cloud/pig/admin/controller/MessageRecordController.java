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
import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.dto.TaskMessageDTO;
import com.pig4cloud.pig.admin.api.entity.MessageRecord;
import com.pig4cloud.pig.admin.service.MessageRecordService;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.vo.paifu.NodeMessageRecordVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * 消息记录表
 *
 * @author 缪建华
 * @date 2021-09-02 16:16:46
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/messagerecord" )
@Api(value = "messagerecord", tags = "消息记录表管理")
public class MessageRecordController {

    private final MessageRecordService messageRecordService;

    /**
     * 分页查询
     *  page 分页对象
     * @param messageRecordDTO 消息记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page" )
    public R getMessageRecordPage(@RequestBody MessageRecordDTO messageRecordDTO) {
        return R.ok(messageRecordService.getMessageRecordPage(messageRecordDTO.getPage(), messageRecordDTO));
    }


    /**
     * 通过id查询消息记录表
     * @param messageId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{messageId}" )
    public R getById(@PathVariable("messageId" ) Integer messageId) {
        return R.ok(messageRecordService.getById(messageId));
    }

    /**
     * 新增消息记录表
     * @param messageRecord 消息记录表
     * @return R
     */
    @ApiOperation(value = "新增消息记录表", notes = "新增消息记录表")
    @SysLog("新增消息记录表" )
    @PostMapping
    public R save(@RequestBody MessageRecord messageRecord) {
        return R.ok(messageRecordService.save(messageRecord));
    }

    /**
     * 修改消息记录表
     * @param messageRecord 消息记录表
     * @return R
     */
    @ApiOperation(value = "修改消息记录表", notes = "修改消息记录表")
    @SysLog("修改消息记录表" )
    @PutMapping
    public R updateById(@RequestBody MessageRecord messageRecord) {
        return R.ok(messageRecordService.updateById(messageRecord));
    }

	/**
	 * 修改消息记录状态全部为已读
	 * @param messageRecord 修改消息记录状态全部为已读
	 * @return R
	 */
	@ApiOperation(value = "修改消息记录状态全部为已读", notes = "修改消息记录状态全部为已读")
	@SysLog("修改消息记录状态全部为已读" )
	@PutMapping("/updateReadFlagAll")
	public R updateReadFlagAll(@RequestBody List<MessageRecord> messageRecord) {
		return R.ok(messageRecordService.updateReadFlagAll(messageRecord));
	}

    /**
     * 通过id删除消息记录表
     * @param messageId id
     * @return R
     */
    @ApiOperation(value = "通过id删除消息记录表", notes = "通过id删除消息记录表")
    @SysLog("通过id删除消息记录表" )
    @DeleteMapping("/{messageId}" )
    public R removeById(@PathVariable Integer messageId) {
        return R.ok(messageRecordService.removeById(messageId));
    }

	/**
	 * 发送消息并推送到app
	 * @param messageRecordDTOList
	 * @return R
	 */
	@ApiOperation(value = "发送消息并推送到app", notes = "发送消息并推送到app")
	@SysLog("发送消息并推送到app" )
	@PostMapping("/batchSendMessageRecordPush")
	public R batchSendMessageRecordPush(@RequestBody List<MessageRecordDTO> messageRecordDTOList) {
		return R.ok(messageRecordService.batchSendMessageRecordPush(messageRecordDTOList));
	}

	/**
	 * 发送消息不推送到app
	 * @param messageRecordDTOList
	 * @return R
	 */
	@ApiOperation(value = "批量发送消息不推送到app", notes = "发送消息不推送到app")
	@SysLog("发送消息不推送到app" )
	@PostMapping("/batchSendMessageRecordOutPush")
	public R batchSendMessageRecordOutPush(@RequestBody List<MessageRecordDTO> messageRecordDTOList) {
		return R.ok(messageRecordService.batchSendMessageRecordOutPush(messageRecordDTOList));
	}

	/**
	 *	查询当前机构的所有合作消息
	 * @param messageId
	 * @return
	 */
	@ApiOperation(value = "查询当前机构的所有合作消息", notes = "查询当前机构的所有合作消息")
	@SysLog("查询当前机构的所有合作消息" )
	@GetMapping("/withCooperativeAgencies")
	public R withCooperativeAgencies(Integer messageId){
		return R.ok(this.messageRecordService.withCooperativeAgencies(messageId));
	}
	/**
	 *	查询消息气泡数
	 * @return
	 */
	@ApiOperation(value = "查询消息气泡数", notes = "查询消息气泡数")
	@SysLog("查询消息气泡数" )
	@GetMapping("/getMessageNumber")
	public R withCooperativeAgencies(){
		return R.ok(this.messageRecordService.getMessageNumber());
	}

	/**
	 * 发送拍辅任务消息
	 * @param taskNode
	 * @return
	 */
	@ApiOperation(value = "发送拍辅任务消息", notes = "发送拍辅任务消息")
	@SysLog("发送拍辅任务消息" )
	@PostMapping("/sendPaifuTaskMessage")
	public R sendPaifuTaskMessage(@RequestBody TaskNode taskNode) {

		int count = this.messageRecordService.sendPaifuTaskMessage(taskNode);

		if(count > 0){
			return R.ok("发送任务消息成功！");
		} else if(count == 0) {
			return R.ok("无消息发送！");
		}else{
			return R.failed("发送任务消息失败！");
		}
	}

	/**
	 * 指定消息目标与目标权限发送到指定用户消息列表中
	 * @param taskMessageDTO
	 * @return
	 */
	@ApiOperation(value = "指定消息目标与目标权限发送到指定用户消息列表中", notes = "指定消息目标与目标权限发送到指定用户消息列表中")
	@SysLog("指定消息目标与目标权限发送到指定用户消息列表中" )
	@PostMapping("/sendTaskMessageByTaskMessageDTO")
	public R sendTaskMessageByTaskMessageDTO(@RequestBody TaskMessageDTO taskMessageDTO){
		int count = this.messageRecordService.sendTaskMessageByTaskMessageDTO(taskMessageDTO);

		if (count > 0) {
			return R.ok("指定消息目标与目标权限发送到指定用户成功！");
		} else {
			return R.failed("指定消息目标与目标权限发送到指定用户失败！");
		}
	}

	/**
	 * 更新消息状态为已读
	 * @param messageId
	 * @return
	 */
	@ApiOperation(value = "更新消息状态为已读", notes = "更新消息状态为已读")
	@SysLog("更新消息状态为已读")
	@PutMapping("/updateMessageStatus/{messageId}")
	public R updateMessageStatus(@PathVariable("messageId") Integer messageId) {
		return R.ok(this.messageRecordService.updateMessageStatus(messageId));
	}

}
