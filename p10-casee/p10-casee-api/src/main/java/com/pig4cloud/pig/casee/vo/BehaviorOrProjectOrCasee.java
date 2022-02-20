package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.entity.Behavior;
import lombok.Data;

import java.util.List;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo
 * @ClassNAME: BehaviorOrProjectOrCasee
 * @Author: yd
 * @DATE: 2022/2/17
 * @TIME: 17:46
 * @DAY_NAME_SHORT: 周四
 */
@Data
public class BehaviorOrProjectOrCasee extends Behavior {

	/**
	 * 项目id
	 */
	private Integer projectId;

	/**
	 * 公司业务案号
	 */
	private String companyCode;

	/**
	 * 执行案号
	 */
	private String caseeNumber;

	/**
	 * 债务人
	 */
	private String projectDetail;

}
