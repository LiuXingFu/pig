package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 预约看样 接受咨询
 */
public class PaiFu_PMFZ_YYKY_JSZX implements Serializable {
	/**姓名*/
	private String name;
	/**联系电话*/
	private String phone;
	/**咨询问题*/
	private String askQuestions;
}
