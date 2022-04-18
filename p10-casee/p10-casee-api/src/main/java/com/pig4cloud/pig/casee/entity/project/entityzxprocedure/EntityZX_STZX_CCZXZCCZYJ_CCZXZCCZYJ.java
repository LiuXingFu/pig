package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.dto.AssetsReDTO;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 *	财产执行资产处置移交
 */
@Data
public class EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ extends CommonalityData implements Serializable {

	/**
	 * 拍辅机构id
	 */
	private Integer entrustedInsId;

	/**
	 * 拍辅网点id
	 */
	private Integer entrustedOutlesId;

	/**
	 * 移交的财产信息
	 */
	List<AssetsReDTO> assetsReDTOList;

	/**
	 * 拍卖申请书
	 */
	private String auctionApplicationFile;

	/**
	 * 申请提交时间
	 */
	private LocalDate applicationSubmissionTime;

	/**
	 * 移交时间
	 */
	@ApiModelProperty(value="移交时间")
	private LocalDate handoverTime;

	/**
	 * 备注
	 */
	private String remark;
}
