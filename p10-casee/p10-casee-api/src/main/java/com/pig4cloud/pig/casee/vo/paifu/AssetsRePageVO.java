package com.pig4cloud.pig.casee.vo.paifu;

import com.pig4cloud.pig.casee.entity.liquientity.AssetsReLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.AssetsRePaifu;
import com.pig4cloud.pig.casee.vo.AssetsDeailsVO;
import lombok.Data;

@Data
public class AssetsRePageVO extends AssetsRePaifu {

	//财产详情
	private AssetsDeailsVO assets;


}
