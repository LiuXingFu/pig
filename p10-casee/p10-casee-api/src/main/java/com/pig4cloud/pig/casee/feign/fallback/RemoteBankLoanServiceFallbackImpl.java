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

package com.pig4cloud.pig.casee.feign.fallback;

import com.pig4cloud.pig.casee.entity.BankLoan;
import com.pig4cloud.pig.casee.entity.SubjectBankLoanRe;
import com.pig4cloud.pig.casee.feign.RemoteBankLoanService;
import com.pig4cloud.pig.casee.feign.RemoteSubjectBankLoanReService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Slf4j
@Component
public class RemoteBankLoanServiceFallbackImpl implements RemoteBankLoanService {

	@Setter
	private Throwable cause;

	@Override
	public R<Boolean> updateBankLoan(BankLoan bankLoan, String from) {
		log.error("新增主体关联借贷表信息失败", cause);

		return null;
	}

	@Override
	public R<BankLoan> queryBankLoan(Integer bankLoanId, String from) {
		log.error("查询借贷表信息失败", cause);

		return null;
	}
}
