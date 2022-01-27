package com.pig4cloud.pig.casee.dto;

import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.CaseeSubjectRe;
import com.pig4cloud.pig.casee.entity.detail.CaseeOtherDetail;
import lombok.Data;

import java.util.List;

@Data
public class CaseeAddDTO extends Casee {
	/**
	 * 代理律师
	 */
	private CaseeOtherDetail caseeOtherDetail;
	/**
	 * 案件主体关联信息
	 */
	private List<CaseeSubjectRe> caseeSubjectReList;
}
