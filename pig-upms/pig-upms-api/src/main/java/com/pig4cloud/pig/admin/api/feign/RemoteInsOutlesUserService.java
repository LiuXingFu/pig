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

import com.pig4cloud.pig.admin.api.entity.InsOutlesUser;
import com.pig4cloud.pig.admin.api.feign.factory.RemoteInsOutlesUserServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@FeignClient(contextId = "RemoteInsOutlesUserService", value = ServiceNameConstants.UMPS_SERVICE,
		fallbackFactory = RemoteInsOutlesUserServiceFallbackFactory.class)
public interface RemoteInsOutlesUserService {

	/**
	 * 通过网点id和类型查询网点负责人
	 *
	 * @return R
	 */
	@GetMapping("/insoutlesuser/getOutlesPrincipal")
	R<List<InsOutlesUser>> getOutlesPrincipal(@RequestParam(value = "type" )Integer type, @RequestParam(value = "outlesId") Integer outlesId, @RequestHeader(SecurityConstants.FROM) String from);

}
