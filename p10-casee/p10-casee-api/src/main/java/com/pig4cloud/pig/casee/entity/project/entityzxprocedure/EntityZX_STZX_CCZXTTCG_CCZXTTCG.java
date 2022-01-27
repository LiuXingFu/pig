package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 财产执行腾退成功
 */
@Data
public class EntityZX_STZX_CCZXTTCG_CCZXTTCG extends CommonalityData implements Serializable {

	/**
	 * 腾退日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date vacateDate;

	/**
	 * 备注
	 */
	private String remark;

}
