package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实际执结
 */
@Data
public class ActualExecution extends CommonalityData implements Serializable {
	/**
	 * 结案日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date caseClosedTime;

	/**
	 * 执结类型(0-自动履行完毕 1-强制执行完毕 2-和解履行完毕 3-半强制执行半自动履行)
	 */
	private Integer executionType;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 结案申请文书
	 */
	private String closingApplicationDocumentsFile;

}
