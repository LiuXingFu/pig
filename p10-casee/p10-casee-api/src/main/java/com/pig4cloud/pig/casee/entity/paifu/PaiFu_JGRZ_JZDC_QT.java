package com.pig4cloud.pig.casee.entity.paifu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
/**
 * 尽职调查 其它
 */
public class PaiFu_JGRZ_JZDC_QT implements Serializable {
	/**现勘时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date siteSurveyTime;
	/**附件地址*/
	private String fileUrl;
}
