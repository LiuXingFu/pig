package com.pig4cloud.pig.casee.entity.paifu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
/**
 * 预约看样 看样报名
 */
public class PaiFu_PMFZ_YYKY_KYBM implements Serializable {
	/**预约看样时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date reserveSeeSampleTime;
	/**钥匙联系人*/
	private String keyContact;
	/**联系电话*/
	private String phone;
	/**钥匙位置*/
	private String keyPosition;
}
