/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pig.casee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.casee.entity.SubjectBankLoanRe;
import com.pig4cloud.pig.casee.vo.SubjectInformationVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 主体关联银行借贷表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:44
 */
@Mapper
public interface SubjectBankLoanReMapper extends BaseMapper<SubjectBankLoanRe> {

	List<String> getSubjectBankLoanRe(Integer bankLoanId);

	List<SubjectInformationVO> getSubjectBankLoanReInformationVO(Integer bankLoanId);

	List<Integer> selectSubjectId(Integer bankLoanId);

	Integer countSubjectBankLoanReById(Integer bankLoanId);
}
