package com.pig4cloud.pig.casee.vo.count;

import com.pig4cloud.pig.casee.vo.MoneyBackMonthlyRank;
import com.pig4cloud.pig.casee.vo.PropertyCategoryTotalVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo.count
 * @ClassNAME: 统计回款额月排名、财产类型数量与财产总量
 * @Author: yd
 * @DATE: 2022/3/8
 * @TIME: 14:52
 * @DAY_NAME_SHORT: 周二
 */
@Data
public class CountMoneyBackMonthlyRankVO {

	/**
	 * 回款额排名集合
	 */
	private List<MoneyBackMonthlyRank> moneyBackMonthlyRankList;

	/**
	 * 回款总额
	 */
	private BigDecimal totalRepayments;

	/**
	 * 财产分类统计集合
	 */
	private List<PropertyCategoryTotalVO> propertyCategoryTotalList;

	/**
	 * 财产总量
	 */
	private Long totalProperty;

}
