package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 财产执行商请移送
 */
@Data
public class EntityZX_STZX_CCZXSQYS_CCZXSQYS extends CommonalityData implements Serializable {

	/**
	 * 商请移送日期
	 */
	private LocalDate pleaseTransferDate;

	/**
	 * 商请移送情况(0-移送处置权成功 1-移送处置权失败)
	 */
	private Integer businessPleaseTransfer;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 附件
	 */
	private String appendixFile;
}
