package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 财产执行腾退成功
 */
@Data
public class EntityZX_STZX_CCZXTTCG_CCZXTTCG extends CommonalityData implements Serializable {

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
