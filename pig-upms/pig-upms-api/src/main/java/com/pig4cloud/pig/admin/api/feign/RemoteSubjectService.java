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

import com.pig4cloud.pig.admin.api.entity.Subject;
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
@FeignClient(contextId = "remoteSubjectService", value = ServiceNameConstants.UMPS_SERVICE,
		fallbackFactory = RemoteSubjectServiceFallbackFactory.class)
public interface RemoteSubjectService {


	/**
	 *  保存主体
	 * @param subject
	 * @param from 内部调用标志
	 * @return
	 */
	@PostMapping("/subject/saveBatchSubject")
	R saveBatchSubject(@RequestBody List<Subject> subject, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 *  保存单条主体
	 * @param subject
	 * @param from 内部调用标志
	 * @return
	 */
	@PostMapping("/subject/saveSubject")
	R saveSubject(@RequestBody Subject subject, @RequestHeader(SecurityConstants.FROM) String from);


	/**
	 *  新增或修改主体信息
	 * @param subject
	 * @param from 内部调用标志
	 * @return
	 */
	@PostMapping("/subject/saveOrUpdateById")
	R saveOrUpdateById(@RequestBody Subject subject, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 *  删除主体信息以及主体关联地址信息
	 * @param subjectId
	 * @param from 内部调用标志
	 * @return
	 */
	@DeleteMapping("/subject/{subjectId}")
	R removeById(@PathVariable("subjectId") Integer subjectId, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 根据id集合查询主体信息
	 * @param subjectIdList subjectIdList
	 * @return
	 */
	@GetMapping("/subject/queryBySubjectIdList")
	R<List<Subject>> queryBySubjectIdList(@RequestParam(value = "subjectIdList" ,required=false)List<Integer> subjectIdList, @RequestHeader(SecurityConstants.FROM) String from);

}
