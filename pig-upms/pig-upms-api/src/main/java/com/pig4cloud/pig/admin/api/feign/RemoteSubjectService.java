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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.SubjectPageDTO;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.factory.RemoteSubjectServiceFallbackFactory;
import com.pig4cloud.pig.admin.api.vo.SubjectVO;
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
	R<Subject> saveSubject(@RequestBody Subject subject, @RequestHeader(SecurityConstants.FROM) String from);


	/**
	 *  新增或修改主体信息
	 * @param subject
	 * @param from 内部调用标志
	 * @return
	 */
	@PostMapping("/subject/saveOrUpdateById")
	R<Integer> saveOrUpdateById(@RequestBody Subject subject, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 *  根据手机号判断新增或修改主体
	 * @param subject
	 * @param from 内部调用标志
	 * @return
	 */
	@PostMapping("/subject/getPhoneAndUnifiedIdentityBySaveOrUpdateById")
	R<Subject> getPhoneAndUnifiedIdentityBySaveOrUpdateById(@RequestBody Subject subject, @RequestHeader(SecurityConstants.FROM) String from);

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

	/**
	 * 根据项目id查询债务人信息
	 * @param projectId
	 * @return
	 */
	@GetMapping("/subject/queryByProjectId/{projectId}")
	R<List<Subject>> queryByProjectId(@PathVariable("projectId") Integer projectId, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 *  批量新增或修改主体
	 * @param subject
	 * @param from 内部调用标志
	 * @return
	 */
	@PostMapping("/subject/saveOrUpdateBatch")
	R saveOrUpdateBatch(@RequestBody List<Subject> subject, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 根据机构id查询债务人信息
	 * @param insId
	 * @return
	 */
	@GetMapping("/subject/getByInsId/{insId}")
	R<Subject> getByInsId(@PathVariable("insId") Integer insId, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 *	根据特定条件分页查询债务人
	 * @param page
	 * @param subjectPageDTO
	 * @return
	 */
	@GetMapping("/subject/pageSubject")
	R pageSubject(@RequestParam(value = "page" )Page page,@RequestParam(value = "subjectPageDTO" ) SubjectPageDTO subjectPageDTO, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 *	分页查询项目主体列表
	 * @param page
	 * @param subjectPageDTO
	 * @return
	 */
	@GetMapping("/subject/queryPageList")
	R queryPageList(@RequestParam(value = "page" )Page page,@RequestParam(value = "subjectPageDTO" ) SubjectPageDTO subjectPageDTO, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 根据主体id查询债务人信息
	 * @param subjectId
	 * @return
	 */
	@GetMapping("/subject/selectSubjectById/{subjectId}")
	R<Subject> getById(@PathVariable("subjectId") Integer subjectId, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 通过身份证查询主体信息
	 * @param unifiedIdentity 身份证
	 * @return R
	 */
	@GetMapping("/subject/getByUnifiedIdentity/{unifiedIdentity}" )
	R<SubjectVO> getByUnifiedIdentity(@PathVariable("unifiedIdentity" ) String unifiedIdentity, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 根据债务人id集合查询债务人姓名（多个用，号隔开）
	 * @param subjectIdList
	 * @param from
	 * @return
	 */
	@GetMapping("/subject/querySubjectNameList")
	R<String> querySubjectName(@RequestParam(value = "subjectIdList" ,required=false) List<Integer> subjectIdList, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 通过电话查询主体信息
	 * @param phone
	 * @return
	 */
	@GetMapping("/subject/getByPhone/{phone}/{name}" )
	R<SubjectVO> getByPhone(@PathVariable("phone") String phone, @PathVariable("name") String name,@RequestHeader(SecurityConstants.FROM) String form);

	/**
	 * 根据主体名称查询主体
	 * @param subjectName 主体名称
	 * @return R
	 */
	@GetMapping("/subject/queryBySubjectName/{subjectName}" )
	R<Subject> queryBySubjectName(@PathVariable("subjectName") String subjectName, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 通过身份证与电话查询主体信息
	 * @param unifiedIdentity
	 * @param phone
	 * @return
	 */
	@GetMapping("/subject/getByUnifiedIdentityAndPhone/{unifiedIdentity}/{phone}")
	R<SubjectVO> getByUnifiedIdentityAndPhone(@PathVariable("unifiedIdentity") String unifiedIdentity, @PathVariable("phone") String phone, @RequestHeader(SecurityConstants.FROM) String from);

}
