package com.pig4cloud.pig.casee.entity.liqui.detail;

import lombok.Data;

/**
 * 关联案件列表
 */
@Data
public class RelatedCasesList {
	/**执行案号*/
	private String partyName;
	/**申请人*/
	private String proposer;
	/**被申请人*/
	private String respondent;
	/**案件详情*/
	private String caseDetails;
}
