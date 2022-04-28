package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 腾退成功
 */
@Data
public class PaiFu_STCC_TTCG_TTCG extends CommonalityData implements Serializable {

	/**
	 * 腾退日期
	 */
	private LocalDate vacateDate;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;

}
