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

package com.pig4cloud.pig.casee.feign;

import com.pig4cloud.pig.casee.entity.BankLoan;
import com.pig4cloud.pig.casee.feign.factory.RemoteSubjectBankLoanReServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@FeignClient(contextId = "remoteBankLoanService", value = ServiceNameConstants.CASEE_SERVICE,
		fallbackFactory = RemoteSubjectBankLoanReServiceFallbackFactory.class)
public interface RemoteBankLoanService {

	/**
	 * 新增银行借贷表
	 * @param bankLoan 新增银行借贷表
	 * @return R
	 */
	@PutMapping("/bankloan")
	R<Boolean> updateBankLoan(@RequestBody BankLoan bankLoan, @RequestHeader(SecurityConstants.FROM) String from);


	/**
	 * 查看银行借贷信息
	 * @param bankLoanId 查看银行借贷信息
	 * @return R
	 */
	@GetMapping("/bankloan/getByBankLoanId/{bankLoanId}")
	R<BankLoan> queryBankLoan(@PathVariable("bankLoanId") Integer bankLoanId, @RequestHeader(SecurityConstants.FROM) String from);
}
