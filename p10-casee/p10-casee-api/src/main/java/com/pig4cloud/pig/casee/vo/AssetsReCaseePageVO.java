package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.assets.AssetsReCasee;
import lombok.Data;

/**
 * 案件财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
public class AssetsReCaseePageVO extends AssetsReCasee {

	/**
	 * 财产信息
	 */
	private Assets assets;
}
