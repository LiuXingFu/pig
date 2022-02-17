package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *	财产执行资产处置移交
 */
@Data
public class EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ extends CommonalityData implements Serializable {

	/**
	 * 拍卖申请书
	 */
	private String auctionApplicationFile;

	/**
	 * 申请提交时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date applicationSubmissionTime;

	/**
	 * 备注
	 */
	private String remark;
}
