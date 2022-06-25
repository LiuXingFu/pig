package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.vo.AddressVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 	债务人信息DTO
 */
@Data
public class SubjectInformationVO extends Subject {

	/**
	 * 主体关联银行借贷表id
	 */
	private Integer subjectBankLoanId;

	/**
	 * 债务类型(1-贷款人 2-担保人)
	 */
	private Integer debtType;

	/**
	 * 地址信息
	 */
	private List<AddressVO> addressList=new ArrayList<>();

	/**
	 * 描述
	 */
	private String describes;
}
