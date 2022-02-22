package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 财产执行不动产现勘入户
 */
@Data
public class EntityZX_STZX_CCZXBDCXKRH_CCZXBDCXKRH extends CommonalityData implements Serializable {

	/**
	 * 装修情况(0-毛坯 1-简装 2-中装 3-精装 4-豪装)
	 */
	private Integer renovationCondition;

	/**
	 * 实际户型
	 */
	private String houseType;

	/**
	 * 是否租赁(0-否 1-是)
	 */
	private Integer whetherLease;

	/**
	 * 是否居住(0-否 1-是)
	 */
	private Integer whetherLive;

	/**
	 * 财产所有人配合度
	 */
	private Integer propertyOwnerCooperation;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 附件
	 */
	private String appendixFile;
}
