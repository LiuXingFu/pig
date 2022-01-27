package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.liqui.detail.ProspectingPersonnelList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 执前现勘
 */
@Data
public class LiQui_LX_ZQXK_ZQXK extends CommonalityData implements Serializable {

	/**
	 * 现勘人员信息列表
	 */
	@ApiModelProperty("现勘人员信息列表")
	List<ProspectingPersonnelList> prospectingPersonnelList = new ArrayList<>();

	/**
	 * 现勘明细表
	 */
	@ApiModelProperty("现勘明细表")
	private List<FileAdder> currentSurveysListUrl;

	/**
	 * 现勘图片
	 */
	@ApiModelProperty("现勘图片")
	private List<FileAdder> currentSurveysImageUrl;

	/**
	 * 附件
	 */
	@ApiModelProperty("附件")
	private List<FileAdder> fileUrl;

}
