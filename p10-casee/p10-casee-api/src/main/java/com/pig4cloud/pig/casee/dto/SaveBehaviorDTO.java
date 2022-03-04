package com.pig4cloud.pig.casee.dto;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.dto
 * @ClassNAME: SaveBehaviorDTO
 * @Author: yd
 * @DATE: 2022/3/3
 * @TIME: 21:25
 * @DAY_NAME_SHORT: 周四
 */
@Data
public class SaveBehaviorDTO {

	//层级节点key拼接key
	private String key;

	//添加数据
	private String formData;

	//行为
	private Integer behaviorId;

}
