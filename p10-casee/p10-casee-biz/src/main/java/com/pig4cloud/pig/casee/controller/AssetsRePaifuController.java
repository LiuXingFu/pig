package com.pig4cloud.pig.casee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.DelAssetsTransferDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePageDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePaifuModifyDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePaifuSaveDTO;
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
	@SysLog("根据项目案件财产关联更新财产基本信息" )
	@PutMapping("/modifyByAssetsReId")
	public R modifyByAssetsReId(@RequestBody AssetsRePaifuModifyDTO assetsRePaifuModifyDTO) {
		return R.ok(assetsRePaifuService.modifyByAssetsReId(assetsRePaifuModifyDTO));
	}

}
