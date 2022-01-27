package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 诉前保全
 */
@Data
public class LiQui_SQ_SQBQ_SQBQ extends CommonalityData implements Serializable {

	/**
	 * 保全申请书
	 */
	@ApiModelProperty("保全申请书")
	private List<FileAdder> preservationApplicationUrl;

	/**
	 * 保全对象
	 */
	@ApiModelProperty("保全对象")
	private String preservationObject;

	/**
	 * 申请时间
	 */
	@ApiModelProperty("申请时间")
	private Date applicationTime;

	/**
	 * 查封时间
	 */
	@ApiModelProperty("查封时间")
	private Date seizureTime;

	/**
	 * 到期时间
	 */
	@ApiModelProperty("到期时间")
	private Date expireTime;

	/**
	 * 财产所在地(地址表id)
	 */
	@ApiModelProperty("财产所在地")
	private String propertyLocationId;

	/**
	 * 续封 0-否 1-是
	 */
	@ApiModelProperty("续封 0-否 1-是")
	private Integer canRenew;

	/**
	 * 续封时间
	 */
	@ApiModelProperty("续封时间")
	private Date renewTime;

	/**
	 * 解封 0-否 1-是
	 */
	@ApiModelProperty("解封 0-否 1-是")
	private Integer canUnblock;

	/**
	 * 解封时间
	 */
	@ApiModelProperty("解封时间")
	private Date unblockTime;

}
