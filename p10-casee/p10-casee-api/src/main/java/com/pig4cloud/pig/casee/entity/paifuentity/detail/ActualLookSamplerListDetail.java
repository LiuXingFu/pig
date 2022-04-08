package com.pig4cloud.pig.casee.entity.paifuentity.detail;


import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 引领看样
 *  看样人员名单
 *
 * @author Mjh
 * @date 2021-10-19 16:03:22
 */
@Data
public class ActualLookSamplerListDetail implements Serializable {
	/**姓名*/
    private String name;
	/**联系电话*/
    private String phone;
	/**身份证*/
    private String identityCard;
	/**报名时间*/
    private LocalDateTime registrationTime;
	/**是否实际看样 0-否 1-是*/
    private Integer isSeeSample;
	/**备注*/
    private String remark;
}
