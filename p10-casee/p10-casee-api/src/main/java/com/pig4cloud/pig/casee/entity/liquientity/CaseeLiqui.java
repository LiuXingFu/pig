package com.pig4cloud.pig.casee.entity.liquientity;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.liquientity.detail.CaseeLiquiDetail;
import io.swagger.annotations.ApiModelProperty;

public class CaseeLiqui extends Casee {

	@ApiModelProperty(value="清收案件详情表")
	private CaseeLiquiDetail caseeLiquiDetail;

	public void setCaseeLiquiDetail(CaseeLiquiDetail caseeLiquiDetail) {
		this.caseeLiquiDetail = caseeLiquiDetail;
		this.setCaseeDetail(JSON.toJSONString(caseeLiquiDetail));
	}

	public void initCaseeLiquiDetail(){
		caseeLiquiDetail = JSON.parseObject(this.getCaseeDetail(), CaseeLiquiDetail.class );
	}

	public CaseeLiquiDetail getCaseeLiquiDetail() {
		if(caseeLiquiDetail==null && this.getCaseeDetail()!=null){
			caseeLiquiDetail = JSON.parseObject(this.getCaseeDetail(), CaseeLiquiDetail.class );
		}
		return caseeLiquiDetail;
	}
}
