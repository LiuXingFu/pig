package com.pig4cloud.pig.casee.dto;

import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.CaseeSubjectRe;
import com.pig4cloud.pig.casee.entity.detail.CaseeOtherDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CaseeModifyDTO {
	/**
	 * caseeId
	 */
	@ApiModelProperty(value="caseeId")
	private Integer caseeId;

	/**
	 * 案件状态(0-待立案 1-在办 2- 撤案 3-结案 4-终结)
	 */
	@ApiModelProperty(value="案件状态(0-待立案 1-在办 2- 撤案 3-结案 4-终结) ")
	private Integer status;

	/**
	 * 结案日期
	 */
	@ApiModelProperty(value="结案日期")
	private LocalDate closeTime;

}
