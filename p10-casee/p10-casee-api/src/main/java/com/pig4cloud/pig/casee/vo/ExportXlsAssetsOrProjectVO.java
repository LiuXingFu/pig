package com.pig4cloud.pig.casee.vo;

//import com.alibaba.excel.annotation.ExcelProperty;
//import com.alibaba.excel.annotation.write.style.ColumnWidth;
//import com.alibaba.excel.metadata.BaseRowModel;
//import io.swagger.annotations.ApiModelProperty;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.casee.SysUserEXport;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo
 * @ClassNAME: ExportXlsAssetsOrProjectVO
 * @Author: yd
 * @DATE: 2022/2/18
 * @TIME: 17:21
 * @DAY_NAME_SHORT: 周五
 */
@Data
//public class ExportXlsAssetsOrProjectVO extends BaseRowModel {
public class ExportXlsAssetsOrProjectVO {


//	/**
//	 * 财产id
//	 */
//	@ColumnWidth(20)
//	@ExcelProperty(value = {"财产id"}, index = 0)
//	@ApiModelProperty(value = "财产id")
//	private Integer assetsId;
//
//	/**
//	 * 财产名称
//	 */
//	@ColumnWidth(20)
//	@ExcelProperty(value = {"财产id"}, index = 1)
//	@ApiModelProperty(value = "财产id")
//	private String assetsName;
//
//	/**
//	 * 财产类型
//	 */
//	@ColumnWidth(20)
//	@ExcelProperty(value = {"财产类型"}, index = 2)
//	@ApiModelProperty(value = "财产类型")
//	private Integer type;
//
//	/**
//	 * 所有权人
//	 */
//	@ColumnWidth(100)
//	@ExcelProperty(value = {"所有权人"}, index = 3)
//	@ApiModelProperty(value = "所有权人")
//	private String owner;
//
//
//	/**
//	 * 财产编号/账号
//	 */
//	@ColumnWidth(20)
//	@ExcelProperty(value = {"财产编号/账号"}, index = 4)
//	@ApiModelProperty(value = "财产编号/账号")
//	private String accountNumber;
//
//	/**
//	 * 项目id
//	 */
//	@ColumnWidth(20)
//	@ExcelProperty(value = {"项目id"}, index = 5)
//	@ApiModelProperty(value = "项目id")
//	private Integer projectId;
//
//	/**
//	 * 公司业务案号
//	 */
//	@ColumnWidth(40)
//	@ExcelProperty(value = {"公司业务案号"}, index = 6)
//	@ApiModelProperty(value = "公司业务案号")
//	private String companyCode;
//
//	/**
//	 * 项目状态
//	 */
//	@ColumnWidth(20)
//	@ExcelProperty(value = {"项目状态"}, index = 7)
//	@ApiModelProperty(value = "项目状态")
//	private Integer status;
//
//	/**
//	 * 是否抵押
//	 */
//	@ColumnWidth(20)
//	@ExcelProperty(value = {"是否抵押"}, index = 8)
//	@ApiModelProperty(value = "是否抵押")
//	private Integer assetsSource;
//
//	/**
//	 * 财产关联表id
//	 */
//	@ColumnWidth(20)
//	@ExcelProperty(value = {"财产关联表id"}, index = 9)
//	@ApiModelProperty(value = "财产关联表id")
//	private Integer assetsReId;
//
//	/**
//	 * 是否首冻(0-否 1-是)
//	 */
//	@ColumnWidth(20)
//	@ExcelProperty(value = {"是否首冻(0-否 1-是)"}, index = 10)
//	@ApiModelProperty(value = "是否首冻(0-否 1-是)")
//	private String whetherFirstFrozen;
//
//	/**
//	 * 查封情况(0-首封 1-轮候)
//	 */
//	@ColumnWidth(20)
//	@ExcelProperty(value = {"查封情况(0-首封 1-轮候)"}, index = 11)
//	@ApiModelProperty(value = "查封情况(0-首封 1-轮候)")
//	private String sealUpCondition;

	/**
	 * 财产id
	 */
//	@ColumnWidth(20)
//	@ExcelProperty(value = {"财产id"}, index = 0)
//	@ApiModelProperty(value = "财产id")
	@Excel(name = "财产id", orderNum = "1", width = 25, needMerge = true)
	private Integer assetsId;

	/**
	 * 财产名称
	 */
	@Excel(name = "财产名称", orderNum = "2", width = 25, needMerge = true)
	private String assetsName;

	/**
	 * 财产类型（20100-资金财产 20200-实体财产）
	 */
	@Excel(name = "财产类型", replace = { "资金财产_20100", "实体财产_20200" }, orderNum = "3", width = 25, needMerge = true)
	private Integer type;

	/**
	 * 所有权人
	 */
	@Excel(name = "所有权人", orderNum = "3", width = 25, needMerge = true)
	private String owner;

	/**
	 * 财产编号/账号
	 */
	@Excel(name = "财产编号/账号", orderNum = "4", width = 25, needMerge = true)
	private String accountNumber;

	/**
	 * 项目id
	 */
	@Excel(name = "项目id", orderNum = "5", width = 25, needMerge = true)
	private Integer projectId;

	/**
	 * 公司业务案号
	 */
	@Excel(name = "公司业务案号", orderNum = "6", width = 25, needMerge = true)
	private String companyCode;

	/**
	 * 项目状态(1000-在办、2000-暂缓、3000-和解、4000-退出)
	 */
	@Excel(name = "项目状态", replace = { "在办_1000", "暂缓_2000", "和解_3000", "退出_4000" }, orderNum = "7", width = 25, needMerge = true)
	private Integer status;

	/**
	 * 是否抵押
	 */
	@Excel(name = "是否抵押", replace = { "是_1", "否_2" }, orderNum = "8", width = 25, needMerge = true)
	private Integer assetsSource;

	/**
	 * 财产关联表id
	 */
	@Excel(name = "财产关联表id", orderNum = "9", width = 25, needMerge = true)
	private Integer assetsReId;

	/**
	 * 是否首冻(0-否 1-是)
	 */
	@Excel(name = "是否首冻", replace = {"未冻结_null", "否_0", "是_1" }, orderNum = "10", width = 25, needMerge = true)
	private String whetherFirstFrozen;

	/**
	 * 查封情况(0-首封 1-轮候)
	 */
	@Excel(name = "查封情况", replace = { "未查封_null", "首封_0", "轮候_1" }, orderNum = "11", width = 25, needMerge = true)
	private String sealUpCondition;

	@ExcelCollection(name = "查封用户信息", orderNum = "12")
	private List<SysUserEXport> sysUserList;
}
