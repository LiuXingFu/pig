package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 结案
 */
@Data
public class LiQui_ZH_JA extends CommonalityData implements Serializable {

	/**
	 * 结案
	 */
	private LiQui_ZH_JA_JA liQui_ZH_JA_JA = new LiQui_ZH_JA_JA();

}
