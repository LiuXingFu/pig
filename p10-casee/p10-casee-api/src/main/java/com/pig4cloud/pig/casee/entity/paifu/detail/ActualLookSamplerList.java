package com.pig4cloud.pig.casee.entity.paifu.detail;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 引领看样
 *  看样人员名单
 *
 * @author Mjh
 * @date 2021-10-19 16:03:22
 */
@Data
public class ActualLookSamplerList implements Serializable {
	/**姓名*/
    private String name;
	/**联系电话*/
    private String phone;
	/**身份证*/
    private String unifiedIdentity;
	/**报名时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date registrationTime;
	/**是否实际看样 0-否 1-是*/
    private Boolean isSeeSample;
	/**备注*/
    private String remark;
}
