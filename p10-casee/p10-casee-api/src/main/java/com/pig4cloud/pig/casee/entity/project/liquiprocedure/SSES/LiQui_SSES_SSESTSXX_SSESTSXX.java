package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.OtherPanelMembers;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 诉讼阶段二审庭审信息
 */
@Data
public class LiQui_SSES_SSESTSXX_SSESTSXX extends CommonalityData implements Serializable {
	/**
	 * 开庭日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date courtSessionTime;

	/**
	 * 到场日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date turnUpTime;

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
