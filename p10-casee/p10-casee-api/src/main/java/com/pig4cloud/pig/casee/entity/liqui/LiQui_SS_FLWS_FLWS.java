package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 法律⽂书
 */
@Data
public class LiQui_SS_FLWS_FLWS extends CommonalityData implements Serializable {

	/**
	 * 委托协议
	 */
	@ApiModelProperty("委托协议")
	private List<FileAdder> trustAgreementUrl;

	/**
	 * 授权委托书
	 */
	@ApiModelProperty("授权委托书")
	private List<FileAdder> powerOfAttorneyUrl;

	/**
	 * 民事起诉书
	 */
	@ApiModelProperty("民事起诉书")
	private List<FileAdder> civilIndictmentUrl;

	/**
	 * 出庭函
	 */
	@ApiModelProperty("出庭函")
	private List<FileAdder> appearanceLetterUrl;

	/**
	 * 代理词
	 */
	@ApiModelProperty("代理词")
	private List<FileAdder> proxyWordUrl;

	/**
	 * 民事答辩或反诉状 (civilDefenseOrCounterclaim)
	 */
	@ApiModelProperty("民事答辩或反诉状")
	private List<FileAdder> CDORCounterclaimUrl;

	/**
	 * 财产保全担保书 (propertyPreservationGuarantee)
	 */
	@ApiModelProperty("财产保全担保书")
	private List<FileAdder> PPGuaranteeUrl;

	/**
	 * 证据保全申请书 (evidencePreservationApplication)
	 */
	@ApiModelProperty("证据保全申请书")
	private List<FileAdder> EPApplicationUrl;

	/**
	 * 财产诉讼保全申请书
	 */
	@ApiModelProperty("财产诉讼保全申请书")
	private List<FileAdder> PLPApplicationUrl;

	/**
	 * 其他
	 */
	@ApiModelProperty("其他")
	private List<FileAdder> otherUrl;

}
