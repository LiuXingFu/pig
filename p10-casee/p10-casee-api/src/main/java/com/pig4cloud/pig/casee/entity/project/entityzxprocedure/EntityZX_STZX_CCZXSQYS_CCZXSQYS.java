package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 财产执行商请移送
 */
@Data
public class EntityZX_STZX_CCZXSQYS_CCZXSQYS extends CommonalityData implements Serializable {

	/**
	 * 商请移送日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date pleaseTransferDate;

	/**
	 * 商请移送情况(0-移送处置权成功 1-移送处置权失败)
	 */
	private Integer businessPleaseTransfer;

	/**
	 * 备注
	 */
	private String remark;
}
