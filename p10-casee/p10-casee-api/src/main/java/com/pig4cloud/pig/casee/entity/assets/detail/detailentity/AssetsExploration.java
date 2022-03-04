package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.assets.detail.detailentity
 * @ClassNAME: 实体资产现勘
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 15:03
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class AssetsExploration extends CommonalityData implements Serializable {

	/**
	 * 现勘日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date explorationTime;

	/**
	 * 现勘经办人(0-否 1-是 2-自动履行到申请人)
	 */
	private Integer currentSurveyManager;

	/**
	 * 拍辅机构名称
	 */
	private String insId;

	/**
	 * 拍辅网点名称
	 */
	private String outlesId;

	/**
	 * 拍辅办理人名称
	 */
	private String nickName;

	/**
	 * 是否毛坯房(0-否 1-是)
	 */
	private Integer whetherRoughHousing;

	/**
	 * 是否入户现勘(0-未入户现勘 1-已入户现勘)
	 */
	private Integer whetherHomeInspection;

	/**
	 * 财产省市区
	 */

	/**
	 * 省
	 */
	private String province;

	/**
	 * 市
	 */
	private String city;

	/**
	 * 区
	 */
	private String area;

	/**
	 * 行政区划编号
	 */
	private String code;

	/**
	 * 财产详细地址
	 */
	private String address;

	/**
	 * 套内面积
	 */
	private Double insideArea;

	/**
	 * 建筑面积
	 */
	private Double constructionArea;

	/**
	 * 朝向
	 */
	private String towards;

	/**
	 * 欠费情况
	 */
	private String arrears;

	/**
	 * 户型
	 */
	private String houseType;

	/**
	 * 图片
	 */
	private String pictureFile;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * VR
	 */
	private String vrFile;

	/**
	 * 备注
	 */
	private String remark;

}