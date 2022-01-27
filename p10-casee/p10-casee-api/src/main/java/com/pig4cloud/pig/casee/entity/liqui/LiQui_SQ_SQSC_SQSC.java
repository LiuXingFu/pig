package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 诉前审查
 */
@Data
public class LiQui_SQ_SQSC_SQSC extends CommonalityData implements Serializable {

	/**
	 * 借款日期
	 */
	@ApiModelProperty("借款日期")
	private Date borrowingDate;

	/**
	 * 到期日期
	 */
	@ApiModelProperty("到期日期")
	private Date maturityDate;

	/**
	 * 合同诉讼时效
	 */
	@ApiModelProperty("合同诉讼时效")
	private Date statuteLimitationsTime;

	/**
	 * 诉讼证据
	 */
	@ApiModelProperty("诉讼证据")
	private List<FileAdder> litigationEvidenceUrl;

	/**
	 * 诉前调解书
	 */
	@ApiModelProperty("诉前调解书")
	private List<FileAdder> preLitigationMediationUrl;

}
