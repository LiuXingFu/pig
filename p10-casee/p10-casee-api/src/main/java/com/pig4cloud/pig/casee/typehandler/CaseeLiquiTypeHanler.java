package com.pig4cloud.pig.casee.typehandler;

import com.pig4cloud.pig.casee.entity.detail.CaseeLiQuiDateDetail;
import com.pig4cloud.pig.common.mybatis.base.CommObjectTypeHandler;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes({CaseeLiQuiDateDetail.class})
public class CaseeLiquiTypeHanler extends CommObjectTypeHandler<CaseeLiQuiDateDetail> {
	public CaseeLiquiTypeHanler(Class<CaseeLiQuiDateDetail> type) {
		super(type);
	}
}
