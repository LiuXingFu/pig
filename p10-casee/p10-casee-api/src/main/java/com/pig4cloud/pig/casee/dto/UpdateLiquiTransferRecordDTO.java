package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateLiquiTransferRecordDTO {

	/**
	 * 清收移交记录id
	 */
	@ApiModelProperty(value = "清收移交记录id")
	private Integer liquiTransferRecordId;

	/**
	 * 委托机构id
	 */
	@ApiModelProperty(value = "委托机构id")
	private Integer entrustInsId;

	/**
	 * 委托网点id
	 */
	@ApiModelProperty(value = "委托网点id")
	private Integer entrustOutlesId;

	/**
	 * 受托机构id
	 */
	@ApiModelProperty(value = "受托机构id")
	private Integer entrustedInsId;

	/**
	 * 受托网点id
	 */
	@ApiModelProperty(value = "受托网点id")
	private Integer entrustedOutlesId;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value = "项目id")
	private Integer projectId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id")
	private String nodeId;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value = "案件id")
	private Integer caseeId;

	/**
	 * 案件案号
	 */
	@ApiModelProperty(value = "案件案号")
	private String caseeNumber;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 移交的财产信息
	 */
	List<AssetsReDTO> assetsReDTOList;

	/**
	 * 拍卖申请书
	 */
	private String auctionApplicationFile;

	/**
	 * 申请提交时间
	 */
	private LocalDate applicationSubmissionTime;

	/**
	 * 移交时间
	 */
	@ApiModelProperty(value="移交时间")
	private LocalDate handoverTime;

}
