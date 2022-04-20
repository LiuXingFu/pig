package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *	财产执行成交裁定
 */
@Data
public class EntityZX_STZX_CCZXCJCD_CCZXCJCD extends CommonalityData implements Serializable {

	/**
	 * 裁定日期
	 */
	private LocalDate dateOfAdjudication;

	/**
	 * 拍卖结束执行案号
	 */
	private String caseeNumber;

	/**
	 * 成交裁定文书
	 */
	private String completionRulingFile;

	/**
	 * 备注
	 */
	private String remark;
}
