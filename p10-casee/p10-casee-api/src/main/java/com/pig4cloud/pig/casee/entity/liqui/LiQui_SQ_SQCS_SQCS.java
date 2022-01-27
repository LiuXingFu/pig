package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.liqui.detail.CollectionRecordsList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
/**
 * 诉前催收 只录入了电话催收的字段
 */
public class LiQui_SQ_SQCS_SQCS extends CommonalityData implements Serializable {

	/**
	 * 催收记录列表
	 */
	@ApiModelProperty("催收记录列表")
	List<CollectionRecordsList> collectionRecordsList = new ArrayList<>();

}
