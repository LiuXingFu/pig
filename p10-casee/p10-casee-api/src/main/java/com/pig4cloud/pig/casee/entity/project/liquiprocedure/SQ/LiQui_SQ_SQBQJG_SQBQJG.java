package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SQ;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 诉前阶段保全结果
 */
@Data
public class LiQui_SQ_SQBQJG_SQBQJG extends CommonalityData implements Serializable {
	/**
	 * 结案日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
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
