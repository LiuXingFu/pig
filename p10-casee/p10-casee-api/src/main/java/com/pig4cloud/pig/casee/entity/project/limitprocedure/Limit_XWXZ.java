package com.pig4cloud.pig.casee.entity.project.limitprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 行为限制
 */
@Data
public class Limit_XWXZ extends CommonalityData implements Serializable {

	/**
	 * 行为限制送达情况
	 */
	Limit_XWXZ_XWXZSDQK limit_XWXZ_XWXZSDQK = new Limit_XWXZ_XWXZSDQK();

	/**
	 * 行为限制限制撤销
	 */
	Limit_XWXZ_XWXZXZCX limit_XWXZ_XWXZXZCX = new Limit_XWXZ_XWXZXZCX();

	/**
	 * 行为限制限制撤销送达情况
	 */
	Limit_XWXZ_XWXZXZCXSDQK limit_XWXZ_XWXZXZCXSDQK = new Limit_XWXZ_XWXZXZCXSDQK();

}
