package com.pig4cloud.pig.casee.dto.paifu.excel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ImportPaifuDTO {

	/**
	 * 在办导入集合 2021、2022tab页数据
	 */
	private List<ImportPaifu> inProgressList;

	/**
	 * 已结案导入集合 已结案tab页数据
	 */
	private List<ImportPaifu> closedList;

	/**
	 * 承办法院id
	 */
	@ApiModelProperty(value="承办法院id")
	private Integer courtId;

	/**
	 * 机构id
	 */
	@ApiModelProperty(value="机构id")
	private Integer insId;

	/**
	 * 网点id
	 */
	@ApiModelProperty(value="网点id")
	private Integer outlesId;

	/**
	 * 办理人id
	 */
	@ApiModelProperty(value="办理人id")
	private Integer userId;

	/**
	 * 办理人名称
	 */
	@ApiModelProperty(value="办理人名称")
	private String userNickName;

}
