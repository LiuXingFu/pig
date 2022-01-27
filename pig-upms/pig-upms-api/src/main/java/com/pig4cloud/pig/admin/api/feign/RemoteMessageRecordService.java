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

package com.pig4cloud.pig.admin.api.feign;

import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.feign.factory.RemoteMessageRecordServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@FeignClient(contextId = "remoteMessageRecordService", value = ServiceNameConstants.UMPS_SERVICE,
		fallbackFactory = RemoteMessageRecordServiceFallbackFactory.class)
public interface RemoteMessageRecordService {
	/**
	 * 发送消息并推送到app
	 * @param messageRecordDTOList
	 * @return R
	 */
	@PostMapping("/messagerecord/batchSendMessageRecordPush")
	R batchSendMessageRecordPush(@RequestBody List<MessageRecordDTO> messageRecordDTOList,@RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 发送消息不推送到app
	 * @param messageRecordDTOList
	 * @return R
	 */
	@PostMapping("/messagerecord/batchSendMessageRecordOutPush")
	R batchSendMessageRecordOutPush(@RequestBody List<MessageRecordDTO> messageRecordDTOList,@RequestHeader(SecurityConstants.FROM) String from);

}
