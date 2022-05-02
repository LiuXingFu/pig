package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.AssociateOutlesRe;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.lang.model.type.IntersectionType;
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

	/**
	 * 关联机构类型
	 */
	private Integer insType;

}
