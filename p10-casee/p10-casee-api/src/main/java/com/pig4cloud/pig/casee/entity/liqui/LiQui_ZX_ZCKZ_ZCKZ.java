package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 资产控制
 */
@Data
public class LiQui_ZX_ZCKZ_ZCKZ extends CommonalityData implements Serializable {

	//财产控制
	/**
	 * 查封信息附件
	 */
	@ApiModelProperty("查封信息附件")
	private List<FileAdder> seizureInformationUrl;
	/**
	 * 冻结信息附件
	 */
	@ApiModelProperty("冻结信息附件")
	private List<FileAdder> freezeInformationUrl;


	//行为控制
	/**
	 * 行为限制(0-限制消费令 1-列入失信人员名单 2-拘留)
	 */
	@ApiModelProperty("行为限制(0-限制消费令 1-列入失信人员名单 2-拘留)")
	private Integer behaviorRestriction;

	/**
	 * 违法处罚(0-罚款 1-拘留)
	 */
	@ApiModelProperty("违法处罚(0-罚款 1-拘留)")
	private Integer illegalPunishment;

	/**
	 * 犯罪信息附件
	 */
	@ApiModelProperty("犯罪信息附件")
	private List<FileAdder> criminalInformationUrl;

}
