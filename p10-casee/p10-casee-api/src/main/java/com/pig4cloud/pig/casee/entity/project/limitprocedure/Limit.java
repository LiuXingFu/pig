package com.pig4cloud.pig.casee.entity.project.limitprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 行为限制程序
 */
@Data
public class Limit extends CommonalityData implements Serializable {

	/**
	 * 行为限制
	 */
	Limit_XWXZ limit_XWXZ = new Limit_XWXZ();

}
