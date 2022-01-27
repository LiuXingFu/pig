package com.pig4cloud.pig.casee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.pig4cloud.pig.casee.dto.CaseeBizLiquiDTO;
import com.pig4cloud.pig.casee.entity.CaseeBizBase;
import com.pig4cloud.pig.casee.entity.CaseeBizLiqui;
import com.pig4cloud.pig.casee.vo.CaseeBizLiquiVO;
import com.pig4cloud.pig.casee.vo.CaseeDetailsVO;
import com.pig4cloud.pig.casee.vo.CaseePersonnelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CaseeBizLiquiMapper extends BaseMapper<CaseeBizBase> {
	/**
	 * 查询清收列表
	 * @param page
	 * @param caseeBizLiquiDTO
	 * @return
	 */
	IPage<CaseeBizLiquiVO> selectLiquidateCasee(Page page, @Param("query") CaseeBizLiquiDTO caseeBizLiquiDTO);

	/**
	 * 清收委托案件列表
	 * @param page
	 * @param caseeBizLiquiDTO
	 * @return
	 */
	IPage<CaseeBizLiquiVO> selectLiquidateCaseeEntrust(Page page, @Param("query") CaseeBizLiquiDTO caseeBizLiquiDTO);


	/**
	 * 查询受托详情
	 * @param caseeId
	 * @return
	 */
	CaseeDetailsVO selectEntrustedDetails(@Param("caseeId")Integer caseeId);

	/**
	 * 查询案件审核详情信息
	 * @param caseeId 案件id
	 * @return R
	 */
	CaseeBizLiquiVO queryAuditCaseeDetails(@Param("caseeId")Integer caseeId);

	/**
	 * 查询委托详情
	 * @param caseeId
	 * @return
	 */
	CaseeDetailsVO selectCommissionDetails(@Param("caseeId")Integer caseeId);

	CaseeBizLiquiVO selectCaseeBizLiquiDetail(@Param("caseeId")Integer caseeId);
}
