package com.pig4cloud.pig.casee.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.casee.entity.Customer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerSubjectDTO extends Customer {

	/**
	 * 统一标识(身份证/统一社会信用代码/组织机构代码)
	 */
	@ApiModelProperty(value = "统一标识(身份证/统一社会信用代码/组织机构代码)")
	private String unifiedIdentity;

	/**
	 * 性质类型 0-个人 1-企业/公司
	 */
	@ApiModelProperty(value = "性质类型 0-个人 1-企业/公司")
	private Integer natureType;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 联系方式
	 */
	@ApiModelProperty(value = "联系方式")
	private String phone;

	/**
	 * 电子邮件
	 */
	@ApiModelProperty(value = "电子邮件")
	private String email;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 工作单位
	 */
	@ApiModelProperty(value = "工作单位")
	private String employer;

	/**
	 * 性别（1-男，2-女）默认值0-不详
	 */
	@ApiModelProperty(value = "性别（1-男，2-女）默认值0-不详")
	private Integer gender;

	/**
	 * 民族
	 */
	@ApiModelProperty(value = "民族")
	private String ethnic;

	/**
	 * 客户id
	 */
	@TableId
	@ApiModelProperty(value="客户id")
	private Integer customerId;

	/**
	 * 主体id
	 */
	@ApiModelProperty(value="主体id")
	private Integer subjectId;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;

	/**
	 * 客户类型 可推荐客户-10000 意向客户-20000 成交客户-30000 关联申请人-40000 其他关联客户-50000
	 */
	@ApiModelProperty(value="客户类型 可推荐客户-10000 意向客户-20000 成交客户-30000 关联申请人-40000 其他关联客户-50000")
	private Integer customerType;

}
