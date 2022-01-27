package com.pig4cloud.pig.casee.entity.project.fundingzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 资金执行财产
 */
@Data
public class FundingZX_ZJZX extends CommonalityData implements Serializable {

	/**
	 * 资金执行冻结
	 */
	FundingZX_ZJZX_ZJZXDJ fundingZX_ZJZX_ZJZXDJ = new FundingZX_ZJZX_ZJZXDJ();

	/**
	 * 资金执行冻结送达情况
	 */
	FundingZX_ZJZX_ZJZXDJSDQK fundingZX_ZJZX_ZJZXDJSDQK=new FundingZX_ZJZX_ZJZXDJSDQK();

	/**
	 * 资金执行资金划扣
	 */
	FundingZX_ZJZX_ZJZXZJHK fundingZX_ZJZX_ZJZXZJHK = new FundingZX_ZJZX_ZJZXZJHK();

	/**
	 * 资金执行资金划扣送达情况
	 */
	FundingZX_ZJZX_ZJZXZJHKSDQK fundingZX_ZJZX_ZJZXZJHKSDQK = new FundingZX_ZJZX_ZJZXZJHKSDQK();

}
