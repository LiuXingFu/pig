package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.LiquiTransferRecord;
import lombok.Data;

import java.util.List;

@Data
public class QueryLiquiTransferRecordDetailsVO extends LiquiTransferRecord {
	/**
	 * 提交资产处置移交财产id
	 */
	private Integer assetsId;

	/**
	 * 机构名称
	 */
	private String insName;

	/**
	 * 网点名称
	 */
	private String outlesName;

	/**
	 * 申请人
	 */
	private String applicantSubjectName;

	/**
	 * 被执行人
	 */
	private String executorSubjectName;

	/**
	 * 申请提交时间
	 */
	private String applicationSubmissionTime;

	/**
	 * 拍卖申请书
	 */
	private String auctionApplicationFile;

	/**
	 * 移交的财产信息
	 */
	List<AssetsReLiquiVO> assetsReDTOList;

}
