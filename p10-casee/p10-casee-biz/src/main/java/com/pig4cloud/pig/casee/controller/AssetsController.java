package com.pig4cloud.pig.casee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.casee.dto.AssetsDTO;
import com.pig4cloud.pig.casee.dto.BankLoanDTO;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.AssetsBankLoanRe;
import com.pig4cloud.pig.casee.service.AssetsBankLoanReService;
import com.pig4cloud.pig.casee.service.AssetsService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
	 * 根据主体id分页查询
	 * @param page 分页对象
	 * @param subjectId 主体id
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/queryPageAssetsOrProject" )
	public R queryPageAssetsOrProject(Page page, Integer subjectId) {
		return R.ok(assetsService.queryPageAssetsOrProject(page, subjectId));
	}

}
