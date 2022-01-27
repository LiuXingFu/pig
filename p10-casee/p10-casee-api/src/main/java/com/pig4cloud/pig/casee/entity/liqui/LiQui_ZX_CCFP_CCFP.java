package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 财产分配
 */
@Data
public class LiQui_ZX_CCFP_CCFP extends CommonalityData implements Serializable {
	/**
	 * 执行异议书
	 */
	@ApiModelProperty("执行异议书")
	private List<FileAdder> executionObjectionUrl;
	/**
	 * 参与分配表
	 */
	@ApiModelProperty("参与分配表")
	private List<FileAdder> allocationTableUrl;
	/**
	 * 当事人
	 */
	@ApiModelProperty("当事人")
	private String party;
	/**
	 * 案件情况
	 */
	@ApiModelProperty("案件情况")
	private String caseSituation;
}
