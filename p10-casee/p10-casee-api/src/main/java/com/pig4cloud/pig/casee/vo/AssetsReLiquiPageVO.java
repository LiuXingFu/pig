package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.assets.AssetsReLiqui;
import lombok.Data;

/**
 * 案件财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
public class AssetsReLiquiPageVO extends AssetsReLiqui {

	/**
	 * 财产信息
	 */
	private Assets assets;
}
