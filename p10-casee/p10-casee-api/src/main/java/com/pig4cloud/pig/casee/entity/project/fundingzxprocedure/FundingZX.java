package com.pig4cloud.pig.casee.entity.project.fundingzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 资金执行财产程序
 */
@Data
public class FundingZX extends CommonalityData implements Serializable {

	/**
	 * 资金执行财产
	 */
	FundingZX_ZJZX fundingZX_ZJZX = new FundingZX_ZJZX();

}
