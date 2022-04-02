package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 腾退成功
 */
@Data
public class PaiFu_STCC_TTCG_TTCG extends CommonalityData implements Serializable {

	/**
	 * 腾退日期
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date vacateDate;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;

}
