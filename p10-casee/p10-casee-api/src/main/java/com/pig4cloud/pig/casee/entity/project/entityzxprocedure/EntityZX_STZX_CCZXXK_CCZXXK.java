package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;


import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * 财产执行现勘
 */
@Data
public class EntityZX_STZX_CCZXXK_CCZXXK extends CommonalityData implements Serializable {

	/**
	 * 现勘日期
	 */
	private LocalDate explorationTime;

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
	 * 产权证号
	 */
	private String ownershipCertificates;

	/**
	 * 建成日期
	 */
	private LocalDate builtDate;

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
	 * 车牌号
	 */
	private String numberPlate;

	/**
	 * 车辆出产日期
	 */
	private LocalDate productionDate;

	/**
	 * 初次登记日期
	 */
	private LocalDate registrationDate;

	/**
	 * 发动机号
	 */
	private String engineNumber;

	/**
	 * 品牌车型
	 */
	private String brandModel;

	/**
	 * 行车里程
	 */
	private Double mileage;

	/**
	 * 土地证号
	 */
	private String landCertificateNumber;

	/**
	 * 土地面积
	 */
	private Double landArea;

	/**
	 * 使用期限
	 */
	private Integer periodOfUse;

	/**
	 * 土地性质(0-商业 1-住宅 2-工业 3-综合)
	 */
	private Integer landNature;

	/**
	 * 社会信用代码
	 */
	private String socialCreditCode;

	/**
	 * 企业名称
	 */
	private String companyName;

	/**
	 * 法定代表人
	 */
	private String legalRepresentative;

	/**
	 * 成立日期
	 */
	private LocalDate dateOfEstablishment;

	/**
	 * 注册资本
	 */
	private Double registeredCapital;

	/**
	 * 注册地址
	 */
	private String registeredAddress;

	/**
	 * 资产编号
	 */
	private String assetNumber;

	/**
	 * 资产数量
	 */
	private Integer numberOfAssets;

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
