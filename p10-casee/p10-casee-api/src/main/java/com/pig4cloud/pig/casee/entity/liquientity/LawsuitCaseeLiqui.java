package com.pig4cloud.pig.casee.entity.liquientity;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.liquientity.detail.LawsuitCaseeLiquiDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LawsuitCaseeLiqui extends Casee {

	@ApiModelProperty(value="清收案件详情表")
	private LawsuitCaseeLiquiDetail lawsuitCaseeLiquiDetail;

	public void setLawsuitCaseeLiquiDetail(LawsuitCaseeLiquiDetail lawsuitCaseeLiquiDetail) {
		this.lawsuitCaseeLiquiDetail = lawsuitCaseeLiquiDetail;
		this.setCaseeDetail(JSON.toJSONString(lawsuitCaseeLiquiDetail));
	}

	public void initLawsuitCaseeLiquiDetail(){
		lawsuitCaseeLiquiDetail = JSON.parseObject(this.getCaseeDetail(), LawsuitCaseeLiquiDetail.class );
	}

	public LawsuitCaseeLiquiDetail getLawsuitCaseeLiquiDetail() {
		if(lawsuitCaseeLiquiDetail==null && this.getCaseeDetail()!=null){
			lawsuitCaseeLiquiDetail = JSON.parseObject(this.getCaseeDetail(), LawsuitCaseeLiquiDetail.class );
		}
		return lawsuitCaseeLiquiDetail;
	}
}
