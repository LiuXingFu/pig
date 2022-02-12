package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.AssociateOutlesRe;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class AssociateOutlesReDTO {

	/**
	 * 授权网点id集合
	 */
	private List<Integer> outlesIds;

	/**
	 * 关联机构ID
	 */
	private Integer insAssociateId;

}
