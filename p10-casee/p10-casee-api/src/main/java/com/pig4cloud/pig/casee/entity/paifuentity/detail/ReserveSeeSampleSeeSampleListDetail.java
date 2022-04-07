package com.pig4cloud.pig.casee.entity.paifuentity.detail;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 报名看样
 *  报名看样名单
 *
 * @author Mjh
 * @date 2021-10-19 16:03:22
 */
@Data
public class ReserveSeeSampleSeeSampleListDetail implements Serializable {
	/**姓名*/
    private String name;
	/**联系电话*/
    private String phone;
	/**身份证*/
    private String identityCard;
	/**报名时间*/
    private LocalDateTime registrationTime;
}
