package com.pig4cloud.pig.casee.entity.paifu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
/**
 * 评估定价 系统录入
 */
public class PaiFu_JGRZ_PGDJ_XTLR implements Serializable {
	/**询价成功 0-否 1-是 2-价格不采用*/
	private Integer bargainInquirySuccess;
	/**评估机构名称*/
	private String evaluationAgency;
	/**评估价*/
	private BigDecimal appraisalPrice;
	/**评估基准日*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date evaluationBaseTime;
	/**附件地址*/
	private String fileUrl;
	/**图片地址*/
	private String imageUrl;
}
