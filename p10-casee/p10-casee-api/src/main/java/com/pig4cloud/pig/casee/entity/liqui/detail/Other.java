package com.pig4cloud.pig.casee.entity.liqui.detail;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 其他
 */
@Data
public class Other {

	/**
	 * 其他
	 */
	@ApiModelProperty("其他")
	private List<FileAdder> otherUrl_QT;

	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark_QT;

}
