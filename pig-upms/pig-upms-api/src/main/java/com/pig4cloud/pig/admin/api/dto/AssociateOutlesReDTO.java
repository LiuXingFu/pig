package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.AssociateOutlesRe;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AssociateOutlesReDTO extends AssociateOutlesRe {

	private List<Integer> outless;

}
