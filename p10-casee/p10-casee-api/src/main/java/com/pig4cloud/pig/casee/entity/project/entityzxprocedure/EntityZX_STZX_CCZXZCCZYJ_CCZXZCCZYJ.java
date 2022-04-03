package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *	财产执行资产处置移交
 */
@Data
public class EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ extends CommonalityData implements Serializable {

	/**
	 * 移交机构id
	 */
	private Integer entrustedInsId;

	/**
	 * 移交网点id
	 */
	private Integer entrustedOutlesId;

	/**
	 * 移交用户id
	 */
	private Integer userId;

	/**
	 * 拍卖申请书
	 */
	private String auctionApplicationFile;

	/**
	 * 申请提交时间
	 */
	private LocalDate applicationSubmissionTime;

	/**
	 * 备注
	 */
	private String remark;
}
