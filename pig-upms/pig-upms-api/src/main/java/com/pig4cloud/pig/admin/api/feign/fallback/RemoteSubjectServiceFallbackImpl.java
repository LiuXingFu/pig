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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.SubjectPageDTO;
import com.pig4cloud.pig.admin.api.entity.Subject;

import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.admin.api.vo.SubjectVO;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Slf4j
@Component
public class RemoteSubjectServiceFallbackImpl implements RemoteSubjectService {
	@Setter
	private Throwable cause;


	@Override
	public R saveBatchSubject(List<Subject> subject, String from){
		log.error("添加多条主体失败", cause);
		return null;
	}

	@Override
	public R saveSubject(Subject subject, String from){
		log.error("添加单条主体失败", cause);
		return null;
	}

	@Override
	public R saveOrUpdateById(Subject subject, String from) {
		log.error("添加或修改主体失败", cause);

		return null;
	}

	@Override
	public R<Subject> getPhoneAndUnifiedIdentityBySaveOrUpdateById(Subject subject, String from) {
		log.error("添加或修改主体失败", cause);

		return null;
	}

	@Override
	public R removeById(Integer subjectId, String from) {
		log.error("删除主体失败", cause);

		return null;
	}

	@Override
	public R<List<Subject>> queryBySubjectIdList(List<Integer> subjectIdList, String from) {
		log.error("查询主体失败", cause);
		return null;
	}

	@Override
	public R queryByProjectId(Integer projectId, String from) {
		log.error("根据项目id查询主体失败");
		return null;
	}

	@Override
	public R saveOrUpdateBatch(List<Subject> subject, String from) {
		log.error("批量新增或修改主体失败");
		return null;
	}

	@Override
	public R<Subject> getByInsId(Integer getByInsId, String from) {
		log.error("查询主体失败", cause);
		return null;
	}

	@Override
	public R pageSubject(Page page, SubjectPageDTO subjectPageDTO, String from) {
		log.error("根据特定条件分页查询债务人失败", cause);
		return null;
	}

	@Override
	public R queryPageList(Page page, SubjectPageDTO subjectPageDTO, String from) {
		log.error("分页查询项目主体列表失败", cause);
		return null;
	}

	@Override
	public R getById(Integer subjectId, String from) {
		log.error("根据主体id查询债务人信息失败", cause);
		return null;
	}

	@Override
	public R<SubjectVO> getByUnifiedIdentity(String unifiedIdentity, String from) {
		log.error("根据身份证查询主体信息失败", cause);
		return null;
	}

	@Override
	public R<String> querySubjectName(List<Integer> subjectIdList, String from) {
		log.error("根据债务人id集合查询债务人姓名（多个用，号隔开）失败", cause);
		return null;
	}

	@Override
	public R<SubjectVO> getByPhone(String phone, String form) {
		log.error("通过电话查询主体信息失败", cause);
		return null;
	}

	@Override
	public R<Subject> queryBySubjectName(String subjectName, String from) {
		log.error("根据主体名称查询主体失败", cause);
		return null;
	}

	@Override
	public R<SubjectVO> getByUnifiedIdentityAndPhone(String unifiedIdentity, String phone, String from) {
		log.error("通过身份证与电话查询主体信息", cause);
		return null;
	}
}
