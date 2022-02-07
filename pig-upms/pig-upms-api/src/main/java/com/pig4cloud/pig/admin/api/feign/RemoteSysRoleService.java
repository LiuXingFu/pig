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

import com.pig4cloud.pig.admin.api.entity.SysRole;
import com.pig4cloud.pig.admin.api.feign.factory.RemoteSysRoleServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@FeignClient(contextId = "remoteSysRoleService", value = ServiceNameConstants.UMPS_SERVICE,
		fallbackFactory = RemoteSysRoleServiceFallbackFactory.class)
public interface RemoteSysRoleService {

	/**
	 * 通过当前登录用户id、机构id、网点id，查询角色信息
	 * @param from 内部调用标志
	 * @return
	 */
	@GetMapping("/role/queryByUserIdList")
	R<List<SysRole>> queryByUserIdList(@RequestParam(value = "userId" )Integer userId, @RequestParam(value = "insId" )Integer insId, @RequestParam(value = "outlesId" ) Integer outlesId, @RequestParam(value = "roleCode" ) String roleCode, @RequestHeader(SecurityConstants.FROM) String from);

}
