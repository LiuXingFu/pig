package com.pig4cloud.pig.casee.entity.liqui.detail;

import lombok.Data;

/**
 * 存款账户信息
 */
@Data
public class AccountInformationList {
	/**存款所在金融机构*/
	private String depositoryInstitution;
	/**银行名称*/
	private String bankName;
	/**银行卡账户*/
	private String bankCardAccount;
	/**开户行所在地*/
	private String locationOfOpeningBank;
	/**账户余额*/
	private Double accountBalance;
}
