package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DelAssetsTransferDTO {

	/**
	 * 清收移交记录id
	 */
	@ApiModelProperty(value = "清收移交记录id")
	private Integer liquiTransferRecordId;

	/**
	 * 财产相关信息
	 */
	private AssetsReDTO assetsReDTO;

}
