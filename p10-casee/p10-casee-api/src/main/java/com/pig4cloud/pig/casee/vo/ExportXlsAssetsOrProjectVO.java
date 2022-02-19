package com.pig4cloud.pig.casee.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo
 * @ClassNAME: ExportXlsAssetsOrProjectVO
 * @Author: yd
 * @DATE: 2022/2/18
 * @TIME: 17:21
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class ExportXlsAssetsOrProjectVO extends BaseRowModel {

	/**
	 * 财产id
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {"财产id"}, index = 0)
	@ApiModelProperty(value = "财产id")
	private String assetsId;

	/**
	 * 财产名称
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {"财产id"}, index = 1)
	@ApiModelProperty(value = "财产id")
	private String assetsName;

	/**
	 * 财产类型
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {"财产类型"}, index = 2)
	@ApiModelProperty(value = "财产类型")
	private Integer type;

	/**
	 * 所有权人
	 */
	@ColumnWidth(100)
	@ExcelProperty(value = {"所有权人"}, index = 3)
	@ApiModelProperty(value = "所有权人")
	private String owner;


	/**
	 * 财产编号/账号
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {"财产编号/账号"}, index = 4)
	@ApiModelProperty(value = "财产编号/账号")
	private String accountNumber;

	/**
	 * 项目id
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {"项目id"}, index = 5)
	@ApiModelProperty(value = "项目id")
	private Integer projectId;

	/**
	 * 公司业务案号
	 */
	@ColumnWidth(40)
	@ExcelProperty(value = {"公司业务案号"}, index = 6)
	@ApiModelProperty(value = "公司业务案号")
	private String companyCode;

	/**
	 * 项目状态
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {"项目状态"}, index = 7)
	@ApiModelProperty(value = "项目状态")
	private Integer status;

	/**
	 * 是否抵押
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {"是否抵押"}, index = 8)
	@ApiModelProperty(value = "是否抵押")
	private Integer assetsSource;

	/**
	 * 财产关联表id
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {"财产关联表id"}, index = 9)
	@ApiModelProperty(value = "财产关联表id")
	private Integer assetsReId;

	/**
	 * 是否首冻(0-否 1-是)
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {"是否首冻(0-否 1-是)"}, index = 10)
	@ApiModelProperty(value = "是否首冻(0-否 1-是)")
	private String whetherFirstFrozen;

	/**
	 * 查封情况(0-首封 1-轮候)
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {"查封情况(0-首封 1-轮候)"}, index = 11)
	@ApiModelProperty(value = "查封情况(0-首封 1-轮候)")
	private String sealUpCondition;

}
