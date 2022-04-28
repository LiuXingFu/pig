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

package com.pig4cloud.pig.admin.api.feign.fallback;

import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.dto.TaskMessageDTO;
import com.pig4cloud.pig.admin.api.entity.TaskNode;
import com.pig4cloud.pig.admin.api.feign.RemoteMessageRecordService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Slf4j
@Component
public class RemoteMessageRecordServiceFallbackImpl implements RemoteMessageRecordService {

	@Setter
	private Throwable cause;

	@Override
	public R batchSendMessageRecordPush(List<MessageRecordDTO> messageRecordDTOList, String from) {
		log.error("添加消息失败", cause);
		return null;
	}

	@Override
	public R batchSendMessageRecordOutPush(List<MessageRecordDTO> messageRecordDTOList, String from) {
		log.error("添加消息失败", cause);
		return null;
	}

	@Override
	public R sendPaifuTaskMessage(@RequestBody TaskNode taskNode, String from) {
		log.error("发送清收任务消息失败", cause);
		return null;
	}

	@Override
	public R sendTaskMessageByTaskMessageDTO(TaskMessageDTO taskMessageDTO, String from) {
		log.error("指定消息目标与目标权限发送到指定用户消息列表中失败", cause);
		return null;
	}
}
