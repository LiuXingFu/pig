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

import com.pig4cloud.pig.admin.api.dto.AddressDTO;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.factory.RemoteAddressServiceFallbackFactory;
import com.pig4cloud.pig.admin.api.feign.factory.RemoteSubjectServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author lengleng
 * @date 2019/2/1
 */
@FeignClient(contextId = "remoteAddressService", value = ServiceNameConstants.UMPS_SERVICE,
		fallbackFactory = RemoteAddressServiceFallbackFactory.class)
public interface RemoteAddressService {

	@PostMapping("/address")
	R saveAddress(@RequestBody Address address,@RequestHeader(SecurityConstants.FROM) String from);

	@PostMapping("/address/saveOrUpdateById")
	R saveOrUpdateById(@RequestBody AddressDTO addressDTO, @RequestHeader(SecurityConstants.FROM) String from);

	@PostMapping("/address/saveOrUpdate")
	R saveOrUpdate(@RequestBody Address address, @RequestHeader(SecurityConstants.FROM) String from);

	@PutMapping("/address")
	R updateById(@RequestBody AddressDTO addressDTO, @RequestHeader(SecurityConstants.FROM) String from);

	@PutMapping("/address/updateByAddressId")
	R updateByAddressId(@RequestBody Address address, @RequestHeader(SecurityConstants.FROM) String from);

	@DeleteMapping("/address/removeUserIdAndType")
	R removeUserIdAndType(@RequestParam("userId") Integer userId, @RequestParam("type") Integer type, @RequestHeader(SecurityConstants.FROM) String from);

	@GetMapping("/address/queryAssetsByTypeIdAndType/{typeId}/{type}")
	R<Address> queryAssetsByTypeIdAndType(@PathVariable("typeId") Integer typeId, @PathVariable("type") Integer type, @RequestHeader(SecurityConstants.FROM) String from);

	@PostMapping("/address/saveOrUpdateBatch")
	R saveOrUpdateBatch(@RequestBody List<Address> addressList, @RequestHeader(SecurityConstants.FROM) String from);

}
