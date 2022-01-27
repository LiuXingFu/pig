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

import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.admin.api.feign.factory.RemoteNodeTemplateServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * @author lengleng
 * @date 2019/2/1
 */
@FeignClient(contextId = "remoteNodeTemplateService", value = ServiceNameConstants.UMPS_SERVICE,
		fallbackFactory = RemoteNodeTemplateServiceFallbackFactory.class)
public interface RemoteNodeTemplateService {
	/**
	 * 通过模板id，查询模板信息
	 * @param templateId
	 * @param from 内部调用标志
	 * @return
	 */
	@GetMapping("/tasknodetemplate/{templateId}")
	R<TaskNodeTemplate> getById(@PathVariable("templateId") Integer templateId, @RequestHeader(SecurityConstants.FROM) String from);

}
