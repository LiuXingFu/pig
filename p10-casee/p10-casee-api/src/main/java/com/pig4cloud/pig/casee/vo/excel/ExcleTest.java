package com.pig4cloud.pig.casee.vo.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ExcleTest {

	/**
	 * 序号
	 */
	@Excel(name = "序号", orderNum = "1", width = 25, needMerge = true)
	private Integer XH;

	@Excel(name = "移交日期", orderNum = "2",isImportField = "true",exportFormat = "yyyy/MM/dd", importFormat =  "yyyy/MM/dd" ,databaseFormat = "yyyy/MM/dd")
	private Date YJRQ;

	/**
	 * 支行
	 */
	@Excel(name = "支行", orderNum = "3", width = 25, needMerge = true)
	private String ZH;

	/**
	 * 类型
	 */
	@Excel(name = "类型", replace = { "判决_0", "调解_1" }, orderNum = "4", width = 25, needMerge = true)
	private Integer LX;

	/**
	 * 诉讼案号
	 */
	@Excel(name = "诉讼案号", orderNum = "5", width = 25, needMerge = true)
	private String SSAH;

	/**
	 * 案件批次
	 */
	@Excel(name = "案件批次", orderNum = "6", width = 25, needMerge = true)
	private Integer AJPC;

	/**
	 * 新编案号
	 */
	@Excel(name = "新编案号", orderNum = "7", width = 25, needMerge = true)
	private String XBAH;

	/**
	 * 公司案号
	 */
	@Excel(name = "公司案号", orderNum = "8", width = 25, needMerge = true)
	private String GSAH;

	/**
	 * 案件当事人
	 */
	@Excel(name = "案件当事人", orderNum = "9", width = 25, needMerge = true)
	private String AJBSR;

	/**
	 * 本金
	 */
	@Excel(name = "本金", orderNum = "10", width = 25, needMerge = true)
	private BigDecimal BJ;

	/**
	 * 已判决利息
	 */
	@Excel(name = "已判决利息", orderNum = "11", width = 25, needMerge = true)
	private BigDecimal YBJLX;


	/**
	 * 判决金额
	 */
	@Excel(name = "判决金额", orderNum = "12", width = 25, needMerge = true)
	private String BJJE;


	/**
	 * 诉讼费
	 */
	@Excel(name = "诉讼费", orderNum = "13", width = 25, needMerge = true)
	private String SSF;


	/**
	 * 利息计算方式
	 */
	@Excel(name = "利息计算方式", orderNum = "14", width = 25, needMerge = true)
	private String LXJSFS;

	/**
	 * 抵押否
	 */
	@Excel(name = "抵押否", orderNum = "15", width = 25, needMerge = true)
	private String DYF;

	/**
	 * 抵押情况
	 */
	@Excel(name = "抵押情况", orderNum = "16", width = 25, needMerge = true)
	private String DYQK;

	/**
	 * 未判决利息起算日
	 */
	@Excel(name = "未判决利息起算日", orderNum = "17",isImportField = "true",exportFormat = "yyyy-MM-dd", importFormat =  "yyyy-MM-dd" ,databaseFormat = "yyyy-MM-dd")
	private Date WBJLXQSR;


	/**
	 * 主动履行到期日
	 */
	@Excel(name = "主动履行到期日", orderNum = "18",isImportField = "true",exportFormat = "yyyy-MM-dd", importFormat =  "yyyy-MM-dd" ,databaseFormat = "yyyy-MM-dd")
	private Date ZDLXDQR;

}
