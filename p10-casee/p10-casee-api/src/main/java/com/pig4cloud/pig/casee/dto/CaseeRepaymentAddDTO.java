package com.pig4cloud.pig.casee.dto;



import com.alibaba.fastjson.JSONObject;
import com.pig4cloud.pig.casee.entity.liqui.RepaymentDetails;
import com.pig4cloud.pig.common.core.util.JsonUtils;


public class CaseeRepaymentAddDTO {
	public Integer getCaseeId() {
		return caseeId;
	}

	public void setCaseeId(Integer caseeId) {
		this.caseeId = caseeId;
	}

	public String getRepaymentSum() {
		return repaymentSum;
	}

	public void setRepaymentSum(String repaymentSum) {
		this.repaymentSum = repaymentSum;
	}

	public double getRepaymentRate() {
		return repaymentRate;
	}

	public void setRepaymentRate(double repaymentRate) {
		this.repaymentRate = repaymentRate;
	}

	/**
	 * caseeId
	 */

	private Integer caseeId;
	/**
	 * 还款详情
	 */

	private RepaymentDetails repaymentDetails;



	/**
	 * 还款总金额
	 */

	private String  repaymentSum;
	/**
	 * 回款率
	 */

	private double repaymentRate;

	public void setRepaymentDetails(String repaymentDetailsStr) {
		JsonUtils.jsonToPojo(repaymentDetailsStr, RepaymentDetails.class);
//		RepaymentDetails repaymentDetails = JSONObject.parseObject(repaymentDetailsStr, RepaymentDetails.class);
		RepaymentDetails repaymentDetails=JsonUtils.jsonToPojo(repaymentDetailsStr, RepaymentDetails.class);
		this.repaymentDetails = repaymentDetails;

	}

	public RepaymentDetails getRepaymentDetails() {

		return repaymentDetails;
	}

	@Override
	public String toString() {
		return "CaseeRepaymentAddDTO{" +
				"caseeId=" + caseeId +
				", repaymentDetails=" + repaymentDetails +
				", repaymentSum='" + repaymentSum + '\'' +
				", repaymentRate=" + repaymentRate +
				'}';
	}
}
