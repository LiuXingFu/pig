package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.OtherPanelMembers;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 诉讼阶段一审庭审信息
 */
@Data
public class LiQui_SSYS_SSYSTSXX_SSYSTSXX extends CommonalityData implements Serializable {
	/**
	 * 开庭日期
	 */
	private LocalDate courtSessionTime;

	/**
	 * 到场日期
	 */
	private LocalDate turnUpTime;

	/**
	 * 审判庭
	 */
	private String tribunal;

	/**
	 * 法官助理
	 */
	private String assistantJudge;

	/**
	 * 书记员
	 */
	private String clerk;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 审判长
	 */
	private String presidingJudge;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 其他合议庭成员
	 */
	private List<OtherPanelMembers> otherPanelMembersList;

}
