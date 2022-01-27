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

import com.pig4cloud.pig.admin.api.entity.OutlesTemplateRe;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.admin.api.feign.RemoteOutlesTemplateReService;
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
public class RemoteOutlesTemplateReServiceFallbackImpl implements RemoteOutlesTemplateReService {

	@Setter
	private Throwable cause;

	@Override
	public R<List<OutlesTemplateRe>> getByOutlesId(Integer outlesId, String from) {
		log.error("根据网点id查询网点模板关联信息失败", cause);
		return null;
	}

	@Override
	public R<TaskNodeTemplate> queryTemplateByTemplateNature(Integer targetType,Integer outlesId,String from) {
		log.error("根据标的性质查询网点模板关联信息失败", cause);
		return null;
	}
}
