package com.pig4cloud.pig.casee.entity.paifuentity.detail;


import lombok.Data;

import java.io.Serializable;

/**
 * 报名看样
 *  咨询名单
 *
 * @author Mjh
 * @date 2021-10-19 16:03:22
 */
@Data
public class ReserveSeeSampleConsultingListDetail implements Serializable {
	/**姓名*/
    private String name;
	/**联系电话*/
    private String phone;
	/**身份证*/
	private String identityCard;
	/**咨询问题*/
    private String askQuestions;
}
