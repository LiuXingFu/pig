package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSQT;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 诉讼阶段其他庭审信息
 */
@Data
public class LiQui_SSQT_SSQTCPJGSX_SSQTCPJGSX extends CommonalityData implements Serializable {

	/**
	 * 生效日期
	 */
	private LocalDate effectiveDate;


	/**
	 * 备注
	 */
	private String remark;


	/**
	 * 附件
	 */
	private String appendixFile;

}
