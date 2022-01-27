package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 其他合议庭成员
 */
@Data
public class OtherPanelMembers extends CommonalityData implements Serializable {

	/**
	 * 合议庭类型(0-审判员 1-陪判员)
	 */
	private Integer collegiateCourtType;

	/**
	 * 名称
	 */
	private String name;

}
