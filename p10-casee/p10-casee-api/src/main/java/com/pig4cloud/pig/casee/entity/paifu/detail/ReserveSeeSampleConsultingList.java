package com.pig4cloud.pig.casee.entity.paifu.detail;


import lombok.Data;
import java.io.Serializable;

/**
 * 预约看样
 *  咨询名单
 *
 * @author Mjh
 * @date 2021-10-19 16:03:22
 */
@Data
public class ReserveSeeSampleConsultingList implements Serializable {
	/**姓名*/
    private String name;
	/**联系电话*/
    private String phone;
	/**咨询问题*/
    private String askQuestions;
}
