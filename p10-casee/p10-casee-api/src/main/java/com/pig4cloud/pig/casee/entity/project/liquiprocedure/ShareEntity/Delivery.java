package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 送达情况
 */
@Data
public class Delivery extends CommonalityData implements Serializable {
	/**
	 * 送达日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date deliveryTime;

	/**
	 * 送达对象(多个债务人)
	 */
	private List<String> executedNameList;

	/**
	 * 送达方式(0-邮寄送达 1-当面送达 2-留置送达 3-短信送达 4-公告送达 5-其它送达方式)
	 */
	private Integer deliveryMethod;

	/**
	 * 送达结果(0-当面送达 1-本人签收 2-公司签收 3-家人签字 4-有地址确认书/约定地址,拒收 5-公告送达)
	 */
	private Integer deliveryResult;

	/**
	 * 送达文书名称
	 */
	private List<DeliveryDocumentName> deliveryDocumentNameList;

	/**
	 * 备注
	 */
	private String remark;

}
