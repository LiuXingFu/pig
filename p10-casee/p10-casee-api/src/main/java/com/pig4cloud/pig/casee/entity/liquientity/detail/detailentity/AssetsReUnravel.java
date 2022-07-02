package com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity;

import com.pig4cloud.pig.casee.entity.project.fundingzxprocedure.FundingZX_ZJZX_ZJZXDJ_ZJZXDJ;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 财产解封/解冻
 */
@Data
public class AssetsReUnravel {

	/**
	 * 解封/解冻时间
	 */
	@ApiModelProperty(value = "解封/解冻时间")
	private LocalDate unravelTime;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;

	/**
	 * 附件
	 */
	private String appendixFile;
}
