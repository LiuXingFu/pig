package com.pig4cloud.pig.casee.entity.paifuentity.detail;

import lombok.Data;

import java.io.Serializable;

//看样人员名单
@Data
public class ListOfSamplers implements Serializable {
	/**姓名*/
	private String name;
	/**联系电话*/
	private String phone;
	/**身份证*/
	private String identityCard;
	/**是否实际看样 0-否 1-是*/
	private Integer isSeeSample;
	/**备注*/
	private String remark;
}
