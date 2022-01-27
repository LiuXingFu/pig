package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 财产执行抵偿裁定
 */
@Data
public class EntityZX_STZX_CCZXDCCD_CCZXDCCD extends CommonalityData implements Serializable {
	/**
	 * 裁定日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date dateOfAdjudication;

	/**
	 * 抵偿裁定文书
	 */
	private List<FileAdder> compensationRulingList;

	/**
	 * 备注
	 */
	private String remark;

}
