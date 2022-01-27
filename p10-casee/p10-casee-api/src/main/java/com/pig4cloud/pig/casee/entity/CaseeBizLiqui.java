package com.pig4cloud.pig.casee.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.entity.detail.CaseeLiQuiDateDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


@TableName("p10_casee")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "清收案件表")
public class CaseeBizLiqui extends CaseeBizBase {


	//单对象父类copy子类方法
	public static CaseeBizLiqui genFromCaseeBizBase(CaseeBizBase caseeBizBase){
		if(caseeBizBase==null){
			return null;
		}
//		return JSONObject.parseObject(JSONObject.toJSONString(caseeBizBase), CaseeBizLiqui.class);
		CaseeBizLiqui caseeBizLiqui =new CaseeBizLiqui();
		Copyer.fatherToChild(caseeBizBase,caseeBizLiqui);
		return caseeBizLiqui;
	}
	//list对象 父类copy子类方法
	public static List<CaseeBizLiqui> genFromCaseeBizBaseList(List<CaseeBizBase> caseeBizBaseList){
		List<CaseeBizLiqui> caseeBizLiquiList = new ArrayList<>();
		for (int i=0;i<caseeBizBaseList.size();i++){
			CaseeBizLiqui caseeBizLiqui=new CaseeBizLiqui();
			Copyer.fatherToChild(caseeBizBaseList.get(i),caseeBizLiqui);
			caseeBizLiquiList.add(caseeBizLiqui);
		}
		return caseeBizLiquiList;
	}


	/**
	 * 案件详情数据
	 */
	@ApiModelProperty(value="清收案件详情数据")
	private CaseeLiQuiDateDetail caseeLiquiDetail;

	public void setCaseeLiquiDetail(CaseeLiQuiDateDetail caseeLiquiDetail) {
		this.caseeLiquiDetail = caseeLiquiDetail;
		this.setCaseeDetail(JSON.toJSONString(caseeLiquiDetail));
	}

	public void initCaseeLiquiDetail(){
		caseeLiquiDetail = JSON.parseObject(this.getCaseeDetail(), CaseeLiQuiDateDetail.class );
	}

	public CaseeLiQuiDateDetail getCaseeLiquiDetail() {
		if(caseeLiquiDetail==null && this.getCaseeDetail()!=null){
			caseeLiquiDetail = JSON.parseObject(this.getCaseeDetail(), CaseeLiQuiDateDetail.class );
		}
		return caseeLiquiDetail;
	}


}
