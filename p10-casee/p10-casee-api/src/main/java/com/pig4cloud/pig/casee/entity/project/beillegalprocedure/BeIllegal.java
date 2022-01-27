package com.pig4cloud.pig.casee.entity.project.beillegalprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 行为违法程序
 */
@Data
public class BeIllegal extends CommonalityData implements Serializable {

	/**
	 * 行为违法
	 */
	BeIllegal_XWWF beIllegal_XWWF = new BeIllegal_XWWF();

}
