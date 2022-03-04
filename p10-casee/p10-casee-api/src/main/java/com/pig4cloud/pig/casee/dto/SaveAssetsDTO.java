package com.pig4cloud.pig.casee.dto;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.dto
 * @ClassNAME: SaveAssetsDTO
 * @Author: yd
 * @DATE: 2022/3/3
 * @TIME: 20:52
 * @DAY_NAME_SHORT: 周四
 */
@Data
public class SaveAssetsDTO {

	//层级节点key拼接key
	private String key;

	//添加数据
	private String formData;

	//财产
	private Integer assetsId;

	//案件
	private Integer caseeId;

}
