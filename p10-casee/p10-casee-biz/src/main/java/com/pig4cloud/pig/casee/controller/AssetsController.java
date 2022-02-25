package com.pig4cloud.pig.casee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.casee.dto.AssetsOrProjectPageDTO;
import com.pig4cloud.pig.casee.dto.BankLoanDTO;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.AssetsBankLoanRe;
import com.pig4cloud.pig.casee.service.AssetsBankLoanReService;
import com.pig4cloud.pig.casee.service.AssetsService;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 财产表
 *
 * @author Mjh
 * @date 2022-02-10 09:39:52
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/assets" )
@Api(value = "assets", tags = "财产表管理")
public class AssetsController {

	private final AssetsService assetsService;

	@Autowired
	private AssetsBankLoanReService assetsBankLoanReService;

	@Autowired
	private RemoteAddressService remoteAddressService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param assets 财产表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
	@PreAuthorize("@pms.hasPermission('casee_assets_get')" )
	public R getAssetsPage(Page page, Assets assets) {
		return R.ok(assetsService.page(page, Wrappers.query(assets)));
	}


	/**
	 * 通过id查询财产以及地址信息接口
	 * @param assetsId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询财产以及地址信息接口", notes = "通过id查询财产以及地址信息接口")
	@GetMapping("/{assetsId}" )
	public R getByAssets(@PathVariable("assetsId" ) Integer assetsId) {
		return R.ok(assetsService.getByAssets(assetsId));
	}

	/**
	 * 新增财产表
	 * @param bankLoanDTO 财产表
	 * @return R
	 */
	@ApiOperation(value = "新增财产表", notes = "新增财产表")
	@SysLog("新增财产表" )
	@PostMapping
	public R saveAssets(@RequestBody BankLoanDTO bankLoanDTO) {
		return R.ok(assetsService.saveAssets(bankLoanDTO));
	}

	/**
	 * 修改财产表
	 * @param assets 财产表
	 * @return R
	 */
	@ApiOperation(value = "修改财产表", notes = "修改财产表")
	@SysLog("修改财产表" )
	@PutMapping
	@PreAuthorize("@pms.hasPermission('casee_assets_edit')" )
	public R updateById(@RequestBody Assets assets) {
		return R.ok(assetsService.updateById(assets));
	}

	/**
	 * 通过id删除财产表以及财产关联银行借贷表
	 * @param assetsIds id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除财产表以及财产关联银行借贷表", notes = "通过id删除财产表以及财产关联银行借贷表")
	@SysLog("通过id删除财产表以及财产关联银行借贷表" )
	@DeleteMapping("removeByAssetsIds" )
	public R removeByAssetsIds(List<Integer> assetsIds) {
		for (Integer assetsId : assetsIds) {
			//删除财产关联银行借贷信息
			assetsBankLoanReService.remove(new LambdaQueryWrapper<AssetsBankLoanRe>().eq(AssetsBankLoanRe::getAssetsId,assetsId));
			//删除财产地址信息
			remoteAddressService.removeUserIdAndType(assetsId,4, SecurityConstants.FROM);
		}
		return R.ok(assetsService.removeByIds(assetsIds));
	}

	/**
	 * 根据财产id查询财产信息与财产地址信息
	 * @param assetsId
	 * @return
	 */
	@ApiOperation(value = "根据财产id查询财产信息与财产地址信息", notes = "根据财产id查询财产信息与财产地址信息")
	@GetMapping("/queryById/{assetsId}" )
	public R queryById(@PathVariable Integer assetsId){
		return R.ok(assetsService.queryById(assetsId));
	}

	/**
	 * 根据债务人id分页查询财产与项目数据
	 * @param page
	 * @param assetsOrProjectPageDTO
	 * @return
	 */
	@ApiOperation(value = "根据特定条件分页查询财产与项目数据", notes = "根据特定条件分页查询财产与项目数据")
	@GetMapping("/getPageDebtorAssets" )
	public R getPageDebtorAssets(Page page, AssetsOrProjectPageDTO assetsOrProjectPageDTO) {
		return R.ok(assetsService.getPageDebtorAssets(page, assetsOrProjectPageDTO));
	}

	/**
	 * 查询财产项目相关数据
	 * @param page
	 * @param assetsOrProjectPageDTO
	 * @return
	 */
	@ApiOperation(value = "查询财产项目相关数据", notes = "查询财产项目相关数据")
	@GetMapping("/getPageAssetsManage" )
	public R getPageAssetsManage(Page page, AssetsOrProjectPageDTO assetsOrProjectPageDTO) {
		return R.ok(assetsService.getPageAssetsManage(page, assetsOrProjectPageDTO));
	}

	/**
	 * 根据特定条件导出.xls文件
	 * @param assetsOrProjectPageDTO
	 * @return
	 */
	@ApiOperation(value = "根据特定条件导出.xls文件", notes = "根据特定条件导出.xls文件")
	@GetMapping("/exportXls" )
	public void exportXls(HttpServletResponse response, AssetsOrProjectPageDTO assetsOrProjectPageDTO) throws Exception{
		this.assetsService.exportXls(response, assetsOrProjectPageDTO);
	}

	/**
	 * ******************************************************************
	 */
	/**
	 * 根据案件id分页查询财产
	 * @param page 分页对象
	 * @param caseeId 案件id
	 * @return
	 */
	@ApiOperation(value = "根据案件id分页查询财产", notes = "根据案件id分页查询财产")
	@GetMapping("/queryPageByCaseeId" )
	public R queryPageByCaseeId(Page page, Integer caseeId) {
		return R.ok(assetsService.queryPageByCaseeId(page, caseeId));
	}

	/**
	 * 验证财产名称
	 * @param assetsName 财产名称
	 * @return
	 */
	@ApiOperation(value = "验证财产名称", notes = "验证财产名称")
	@GetMapping("/verifyAssetsName" )
	public R verifyAssetsName(String assetsName) {
		QueryWrapper<Assets> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(Assets::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.lambda().eq(Assets::getAssetsName, assetsName);
		return R.ok(assetsService.getOne(queryWrapper));
	}

	/**
	 * 模糊查询财产名称
	 * @param assetsName 财产名称
	 * @return
	 */
	@ApiOperation(value = "模糊查询财产名称", notes = "模糊查询财产名称")
	@GetMapping("/queryAssetsName" )
	public R queryAssetsName(String assetsName) {
		QueryWrapper<Assets> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(Assets::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.lambda().like(Assets::getAssetsName, assetsName);
		return R.ok(assetsService.list(queryWrapper));
	}

}
