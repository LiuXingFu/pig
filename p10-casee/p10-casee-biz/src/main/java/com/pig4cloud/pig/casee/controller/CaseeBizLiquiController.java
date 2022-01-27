package com.pig4cloud.pig.casee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.CaseeBizBaseDTO;
import com.pig4cloud.pig.casee.dto.CaseeBizLiquiDTO;
import com.pig4cloud.pig.casee.dto.CaseeOrTargetTaskFlowDTO;
import com.pig4cloud.pig.casee.dto.CaseeRepaymentAddDTO;
import com.pig4cloud.pig.casee.service.CaseeBizLiquiService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/caseeBizLiqui" )
@Api(value = "caseeBizLiqui", tags = "清收案件表管理")
public class CaseeBizLiquiController {

	private final CaseeBizLiquiService caseeBizLiquiService;
	private final SecurityUtilsService securityUtilsService;
	/**
	 * 分页查询（受托案件+协办案件）
	 * @param page 分页对象
	 * @param caseeBizLiquiDTO 案件表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
	public R getCaseePage(Page page, CaseeBizLiquiDTO caseeBizLiquiDTO) {
		PigUser pigUser= securityUtilsService.getCacheUser();
		caseeBizLiquiDTO.setInsId(pigUser.getInsId());
		caseeBizLiquiDTO.setOutlesId(pigUser.getOutlesId());
		return R.ok(caseeBizLiquiService.queryLiquidateCaseePage(page, caseeBizLiquiDTO));
	}

	/**
	 * 分页查询(委托案件列表)
	 * @param page 分页对象
	 * @param caseeBizLiquiDTO 案件表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/entrustPage" )
	public R getCaseeEntrustPage(Page page, CaseeBizLiquiDTO caseeBizLiquiDTO) {
		PigUser pigUser= securityUtilsService.getCacheUser();
		caseeBizLiquiDTO.setInsId(pigUser.getInsId());
		caseeBizLiquiDTO.setOutlesId(pigUser.getOutlesId());
		return R.ok(caseeBizLiquiService.queryLiquidateCaseeEntrustPage(page, caseeBizLiquiDTO));
	}
	/**
	 * 新增案件表
	 * @param caseeBizLiquiDTO 案件表
	 * @return R
	 */
	@ApiOperation(value = "新增案件表", notes = "新增案件表")
	@SysLog("新增案件表" )
	@PostMapping
	public R save(@RequestBody CaseeBizLiquiDTO caseeBizLiquiDTO) throws Exception{
		return  R.ok(caseeBizLiquiService.addCasee(caseeBizLiquiDTO));
	}
	/**
	 * 修改案件表
	 * @param caseeBizLiquiDTO 案件表
	 * @return R
	 */
	@ApiOperation(value = "修改案件表", notes = "修改案件表")
	@SysLog("修改案件表" )
	@PutMapping
	public R updateById(@RequestBody CaseeBizLiquiDTO caseeBizLiquiDTO) {
		return R.ok(caseeBizLiquiService.updateCasee(caseeBizLiquiDTO));
	}

	/**
	 * 查询协办网点相关信息
	 * @param caseeId 案件id
	 * @return R
	 */
	@ApiOperation(value = "查询协办网点相关信息", notes = "查询协办网点相关信息")
	@GetMapping("/queryCaseeAssistOutlets/{caseeId}" )
	public R queryCaseeAssistOutlets(@PathVariable("caseeId" )Integer caseeId) {
		return R.ok(caseeBizLiquiService.queryCaseeAssistOutlets(caseeId));
}

	/**
	 * 查询案件审核详情信息
	 * @param caseeId 案件id
	 * @return R
	 */
	@ApiOperation(value = "查询案件审核详情信息", notes = "查询案件审核详情信息")
	@GetMapping("/queryAuditCaseeDetails/{caseeId}" )
	public R queryAuditCaseeDetails(@PathVariable("caseeId" )Integer caseeId) {
		return R.ok(caseeBizLiquiService.queryAuditCaseeDetails(caseeId));
	}

	/**
	 * 查询案件详情
	 * @param caseeId 案件id
	 * @return R
	 */
	@ApiOperation(value = "查询案件详情", notes = "查询案件详情")
	@GetMapping("/queryCaseeDetails/{caseeId}" )
	public R queryCaseeDetails(@PathVariable("caseeId" )Integer caseeId) {
		return R.ok(caseeBizLiquiService.queryCaseeDetails(caseeId));
	}
	/**
	 * 财产信息修改
	 * @param caseeBizLiquiDTO 财产信息修改
	 * @return R
	 */
	@ApiOperation(value = "财产信息修改", notes = "财产信息修改")
	@SysLog("财产信息修改" )
	@PostMapping("/updateProperty")
	public R updatePropertyByCaseId(@RequestBody CaseeBizLiquiDTO caseeBizLiquiDTO) {

		return  R.ok(caseeBizLiquiService.updateProperty(caseeBizLiquiDTO));
	}
	/**
	 * 协助网点修改
	 * @param caseeBizLiquiDTO 协助网点修改
	 * @return R
	 */
	@ApiOperation(value = "协助网点修改", notes = "协助网点修改")
	@SysLog("协助网点修改" )
	@PostMapping("/updateOutles")
	public R updateOutlesByCaseId(@RequestBody CaseeBizLiquiDTO caseeBizLiquiDTO) {

		return  R.ok(caseeBizLiquiService.updateOutles(caseeBizLiquiDTO));
	}


	/**
	 * 修改案件待确认状态(添加消息中心推送)
	 * @param caseeId 案件id
	 * @param status 案件状态：1-开启、2-暂缓、3-中止
	 * @return R
	 */
	@ApiOperation(value = "修改案件待确认状态", notes = "修改案件待确认状态")
	@SysLog("修改案件待确认状态" )
	@PutMapping("/modifyCaseStatus")
	public R modifyCaseStatus(Integer caseeId,Integer status,String explain) {
		return R.ok(caseeBizLiquiService.modifyCaseStatus(caseeId,status,explain));
	}

	/**
	 * 案件结案开启(添加消息中心推送)
	 *   caseeId 案件id
	 *  closeTime 结案时间
	 * @return R
	 */
	@ApiOperation(value = "案件结案开启", notes = "案件结案开启")
	@SysLog("案件结案开启" )
	@PostMapping("/caseClosedOpen")
	public R caseClosedOpen(@RequestBody CaseeBizBaseDTO caseeBizBaseDTO) {
		return R.ok(caseeBizLiquiService.caseClosedOpen(caseeBizBaseDTO.getCaseeId(),caseeBizBaseDTO.getCloseTime(),caseeBizBaseDTO.getExplain()));
	}

	/**
	 * 委托方案件状态确认(添加消息中心推送)
	 * @param caseeOrTargetTaskFlowDTO
	 *  	  statusConfirm 状态确认：2-已确认，3-已拒绝
	 * @return R
	 */
	@ApiOperation(value = "委托方案件状态确认", notes = "委托方案件状态确认")
	@SysLog("委托方案件状态确认" )
	@PutMapping("/confirmCaseStatus")
	public R confirmCaseStatus(CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO) {
		Integer statusConfirm=null;
		if (caseeOrTargetTaskFlowDTO.getStatus()==403){
			statusConfirm=2;
		}else if (caseeOrTargetTaskFlowDTO.getStatus()==111){
			statusConfirm=3;
		}
		Integer integer = caseeBizLiquiService.confirmCaseStatus(caseeOrTargetTaskFlowDTO, statusConfirm);
		if (integer==-1){
			return R.failed("请选择网点!");
		}
		return R.ok(integer);
	}

	/**
	 * 添加案件还款信息
	 * @param caseeRepaymentAddDTO
	 * @return R
	 */
	@ApiOperation(value = "修改案件表", notes = "修改案件表")
	@SysLog("添加案件还款信息" )
	@PostMapping("/addRepaymentDetail")
	public R addRepaymentDetail(@RequestBody  CaseeRepaymentAddDTO caseeRepaymentAddDTO) throws Exception{

		return R.ok(caseeBizLiquiService.addRepaymentDetail(caseeRepaymentAddDTO));
//		return R.ok(caseeBizLiquiService.addRepaymentDetail(caseeBizLiquiDTO));
	}
}
