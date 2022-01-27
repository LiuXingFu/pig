package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.MessageRecord;
import lombok.Data;

@Data
public class CooperationNewsVO extends MessageRecord {

	/**
	 * 合作机构名称
	 */
	private String insName;

}
