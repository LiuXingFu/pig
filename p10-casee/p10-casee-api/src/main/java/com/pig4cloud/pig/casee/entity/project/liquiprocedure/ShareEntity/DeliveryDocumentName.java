package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 送达文书名称
 */
@Data
public class DeliveryDocumentName extends CommonalityData implements Serializable {

	/**
	 * 文书名称
	 */
	private String documentName;

	/**
	 * 送达时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date deliveryTime;
}
