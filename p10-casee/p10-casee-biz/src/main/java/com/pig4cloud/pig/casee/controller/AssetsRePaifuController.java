package com.pig4cloud.pig.casee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.DelAssetsTransferDTO;
import com.pig4cloud.pig.casee.dto.paifu.*;
import com.pig4cloud.pig.casee.service.AssetsRePaifuService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assetsRePaifu" )
@Api(value = "assetsRePaifu", tags = "拍辅财产关联表管理")
public class AssetsRePaifuController {

	@Autowired
	private AssetsRePaifuService assetsRePaifuService;

	/**
	 * 根据项目id分页查询标的物列表
	 * @param page 分页对象
	 * @param assetsRePageDTO
	 * @return
	 */
	@ApiOperation(value = "根据项目id分页查询标的物列表", notes = "根据项目id分页查询标的物列表")
	@GetMapping("/queryAssetsRePageByProjectId")
	public R queryAssetsRePageByProjectId(Page page, AssetsRePageDTO assetsRePageDTO) {
		return R.ok(assetsRePaifuService.queryAssetsRePageByProjectId(page, assetsRePageDTO));
	}

	/**
	 * 保存财产及项目案件关联财产
	 * @param assetsRePaifuSaveDTO
	 * @return R
	 */
	@ApiOperation(value = "保存财产及项目案件关联财产", notes = "保存财产及项目案件关联财产")
	@SysLog("保存财产及项目案件关联财产" )
	@PostMapping("/saveAssetsRe")
	public R saveAssetsRe(@RequestBody AssetsRePaifuSaveDTO assetsRePaifuSaveDTO) {
		return R.ok(assetsRePaifuService.saveAssetsRe(assetsRePaifuSaveDTO));
	}

	/**
	 * 根据财产id、根据id查询项目案件财产关联和财产详情
	 * @param assetsReId
	 * @return
	 */
	@ApiOperation(value = "根据id查询项目案件财产关联和财产详情", notes = "根据id查询项目案件财产关联和财产详情")
	@GetMapping("/getAssetsReById/{assetsReId}")
	public R getAssetsReById(@PathVariable Integer assetsReId) {
		return R.ok(this.assetsRePaifuService.getAssetsReById(assetsReId));
	}

	/**
	 * 根据项目案件财产关联更新财产基本信息
	 * @param assetsRePaifuModifyDTO
	 * @return R
	 */
	@ApiOperation(value = "根据项目案件财产关联更新财产基本信息", notes = "根据项目案件财产关联更新财产基本信息")
	@SysLog("根据项目案件财产关联更新财产基本信息 " )
	@PutMapping("/modifyByAssetsReId")
	public R modifyByAssetsReId(@RequestBody AssetsRePaifuModifyDTO assetsRePaifuModifyDTO) {
		return R.ok(assetsRePaifuService.modifyByAssetsReId(assetsRePaifuModifyDTO));
	}

	/**
	 * 分页查询标的物列表
	 * @param page 分页对象
	 * @param assetsReTargetPageDTO
	 * @return
	 */
	@ApiOperation(value = "根据项目id分页查询标的物列表", notes = "根据项目id分页查询标的物列表")
	@GetMapping("/queryTargetPage")
	public R queryTargetPage(Page page, AssetsReTargetPageDTO assetsReTargetPageDTO) {
		return R.ok(assetsRePaifuService.queryTargetPage(page, assetsReTargetPageDTO));
	}

	/**
	 * 修改项目案件财产关联状态
	 * @param assetsReModifyStatusDTO
	 * @return R
	 */
	@ApiOperation(value = "修改项目案件财产关联状态", notes = "修改项目案件财产关联状态")
	@SysLog("修改项目案件财产关联状态 " )
	@PutMapping("/modifyAssetsReStatus")
	public R modifyAssetsReStatus(@RequestBody AssetsReModifyStatusDTO assetsReModifyStatusDTO) {
		return R.ok(assetsRePaifuService.modifyAssetsReStatus(assetsReModifyStatusDTO));
	}

	/**
	 * 根据项目id查询财产关联信息
	 * @return
	 */
	@ApiOperation(value = "根据项目id查询财产关联信息", notes = "根据项目id查询财产关联信息")
	@GetMapping("/queryByProjectId")
	public R queryByProjectId(Integer projectId,@RequestParam(value = "assetsName",required = false)String assetsName) {
		return R.ok(assetsRePaifuService.queryByProjectId(projectId, assetsName));
	}

	/**
	 * 查询可拍卖财产集合
	 * @return
	 */
	@ApiOperation(value = "查询可拍卖财产集合", notes = "查询可拍卖财产集合")
	@GetMapping("/queryPostAnAuctionList")
	public R queryPostAnAuctionList(Integer projectId,Integer caseeId,Integer assetsId) {
		return R.ok(assetsRePaifuService.queryPostAnAuctionList(projectId, caseeId,assetsId));
	}
}
