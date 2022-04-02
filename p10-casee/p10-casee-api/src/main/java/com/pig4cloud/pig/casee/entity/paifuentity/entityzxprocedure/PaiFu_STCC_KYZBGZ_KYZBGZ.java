package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 看样准备工作
 */
@Data
public class PaiFu_STCC_KYZBGZ_KYZBGZ extends CommonalityData implements Serializable {
	/**预约看样时间*/
	private LocalDateTime reserveSeeSampleTime;
	/**钥匙联系人*/
	private String keyContact;
	/**联系电话*/
	private String phone;
	/**钥匙位置*/
	private String keyPosition;
}
