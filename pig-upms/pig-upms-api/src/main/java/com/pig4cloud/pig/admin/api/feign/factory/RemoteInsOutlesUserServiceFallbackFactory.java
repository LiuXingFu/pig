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

package com.pig4cloud.pig.admin.api.feign.factory;

import com.pig4cloud.pig.admin.api.feign.RemoteInsOutlesUserService;
import com.pig4cloud.pig.admin.api.feign.RemoteInstitutionService;
import com.pig4cloud.pig.admin.api.feign.fallback.RemoteInsOutlesUserServiceFallbackImpl;
import com.pig4cloud.pig.admin.api.feign.fallback.RemoteInstitutionServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Component
public class RemoteInsOutlesUserServiceFallbackFactory implements FallbackFactory<RemoteInsOutlesUserService> {

	@Override
	public RemoteInsOutlesUserService create(Throwable throwable) {
		RemoteInsOutlesUserServiceFallbackImpl remoteInsOutlesUserServiceFallback = new RemoteInsOutlesUserServiceFallbackImpl();
		remoteInsOutlesUserServiceFallback.setCause(throwable);
		return remoteInsOutlesUserServiceFallback;
	}

}