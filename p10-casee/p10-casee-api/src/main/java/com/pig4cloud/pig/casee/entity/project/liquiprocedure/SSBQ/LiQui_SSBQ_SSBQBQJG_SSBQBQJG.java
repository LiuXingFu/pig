package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSBQ;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 诉讼阶段保全保全结果
 */
@Data
public class LiQui_SSBQ_SSBQBQJG_SSBQBQJG extends CommonalityData implements Serializable {
	/**
	 * 结案日期
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date caseClosedTime;

	/**
	 * 保全是否足额(0-否 1-是)
	 */
	private Integer preservationWhetherFullAmount;

	/**
	 * 附件上传
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
