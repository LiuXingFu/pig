package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 抵债务处置
 */
@Data
public class LiQui_ZH_DZWCZ_DZWCZ extends CommonalityData implements Serializable {
	/**
	 * 抵债务处置附件
	 */
	@ApiModelProperty("抵债务处置附件")
	private List<FileAdder> debtSettlementUrl;

	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;


}
