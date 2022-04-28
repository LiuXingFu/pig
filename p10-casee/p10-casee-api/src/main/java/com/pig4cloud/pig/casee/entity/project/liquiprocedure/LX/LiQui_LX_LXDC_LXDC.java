package com.pig4cloud.pig.casee.entity.project.liquiprocedure.LX;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 履行阶段督促
 */
@Data
public class LiQui_LX_LXDC_LXDC extends CommonalityData implements Serializable {
	/**
	 * 督促日期
	 */
	private LocalDate urgeTime;

	/**
	 * 督促依据(0-判决书/调解书 1-和解协议)
	 */
	private Integer supervisionBasis;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 督促形式(0-当面 1-电话 2-短信)
	 */
	private Integer superviseTheForm;

	/**
	 * 督促结果(0-已履行 1-不能履行 2-推迟履行)
	 */
	private Integer superviseResult;

	/**
	 * 债务人
	 */
	private List<String> executedNameList;

	/**
	 * 附件
	 */
	private String appendixFile;
}
