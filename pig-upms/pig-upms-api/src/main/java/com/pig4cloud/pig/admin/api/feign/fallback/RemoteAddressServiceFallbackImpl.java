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

import com.pig4cloud.pig.admin.api.dto.AddressDTO;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Slf4j
@Component
public class RemoteAddressServiceFallbackImpl implements RemoteAddressService {
	@Setter
	private Throwable cause;


	@Override
	public R saveAddress(Address address, String from){
		log.error("添加地址失败", cause);
		return null;
	}

	@Override
	public R saveOrUpdateById(AddressDTO addressDTO, String from) {
		log.error("添加或修改地址失败", cause);
		return null;
	}

	@Override
	public R saveOrUpdate(@RequestBody Address address, @RequestHeader(SecurityConstants.FROM) String from){
		log.error("根据id批量新增或修改地址表", cause);
		return null;
	}

	@Override
	public R updateById(AddressDTO addressDTO, String from) {
		log.error("修改地址失败", cause);
		return null;
	}

	@Override
	public R updateByAddressId(Address address, String from) {
		log.error("修改地址失败", cause);
		return null;
	}

	@Override
	public R removeUserIdAndType(Integer userId, Integer type, String from) {
		log.error("删除地址失败", cause);
		return null;
	}

	@Override
	public R queryAssetsByTypeIdAndType(Integer typeId, Integer type, String from) {
		log.error("根据类型id和类型查询地址信息失败", cause);
		return null;
	}

	@Override
	public R saveOrUpdateBatch(List<Address> addressList, String from) {
		log.error("将address集合进行修添加或修改失败", cause);
		return null;
	}
}
