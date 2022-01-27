package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 结案
 */
@Data
public class CaseClosed extends CommonalityData implements Serializable {

	/**
	 * 结案日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date caseClosedTime;


	/**
	 * 结案类型(0-退案 1-完成)
	 */
	private Integer caseClosedType;

	/**
	 * 备注
	 */
	private String remark;
}
