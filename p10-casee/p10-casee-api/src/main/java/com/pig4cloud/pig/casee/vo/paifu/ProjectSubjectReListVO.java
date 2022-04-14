package com.pig4cloud.pig.casee.vo.paifu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectSubjectReListVO {

	/**
	 * 项目主体关联id
	 */
	@ApiModelProperty(value = "项目主体关联id")
	private Integer subjectReId;

	/**
	 * 主体id
	 */
	@ApiModelProperty(value = "主体id")
	private Integer subjectId;

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
	 * 法人
	 */
	@ApiModelProperty(value = "法人")
	private String legalPerson;

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
	 * 工作单位
	 */
	@ApiModelProperty(value = "工作单位")
	private String employer;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 类型（0-申请人，1-借款人，2-共同借款人，3-担保人）
	 */
	@ApiModelProperty(value="类型（0-申请人，1-借款人，2-共同借款人，3-担保人）")
	private Integer type;

}
