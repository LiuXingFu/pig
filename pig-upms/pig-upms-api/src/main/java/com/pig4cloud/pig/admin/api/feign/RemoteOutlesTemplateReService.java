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

import com.pig4cloud.pig.admin.api.entity.OutlesTemplateRe;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.admin.api.feign.factory.RemoteOutlesTemplateReServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@FeignClient(contextId = "remoteOutlesTemplateReService", value = ServiceNameConstants.UMPS_SERVICE,
		fallbackFactory = RemoteOutlesTemplateReServiceFallbackFactory.class)
public interface RemoteOutlesTemplateReService {

	/**
	 * 通过网点id查询网点模板关联信息
	 *
	 * @param outlesId id
	 * @return R
	 */
	@GetMapping("/outlestemplatere/getByOutlesId/{outlesId}")
	R<List<OutlesTemplateRe>> getByOutlesId(@PathVariable("outlesId") Integer outlesId, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 通过标的性质查询网点模板信息
	 * @param templateNature
	 * @return R
	 */
	@GetMapping("/outlestemplatere/queryTemplateByTemplateNature")
	R<TaskNodeTemplate> queryTemplateByTemplateNature(@RequestParam(value = "templateNature" )Integer templateNature,@RequestParam(value = "outlesId" ) Integer outlesId, @RequestHeader(SecurityConstants.FROM) String from);


}
