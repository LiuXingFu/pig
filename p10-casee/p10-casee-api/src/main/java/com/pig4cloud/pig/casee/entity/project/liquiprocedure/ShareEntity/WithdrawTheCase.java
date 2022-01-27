package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 撤案
 */
@Data
public class WithdrawTheCase extends CommonalityData implements Serializable {
	/**
	 * 撤案日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date withdrawTheCaseTime;


	/**
	 * 撤案文书
	 */
	private List<FileAdder> withdrawalDocumentList;

	/**
	 * 备注
	 */
	private String remark;
}
