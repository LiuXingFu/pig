package com.pig4cloud.pig.casee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.TargetAssetsReAssetsCaseePageDTO;
import com.pig4cloud.pig.casee.service.AssetsRePaifuService;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assetsRePaifu" )
@Api(value = "assetsRePaifu", tags = "拍辅财产关联表管理")
public class AssetsRePaifuController {

	@Autowired
	private AssetsRePaifuService assetsRePaifuService;

	/**
	 * 分页查询标的列表
	 * @param page 分页对象
	 * @param targetAssetsReAssetsCaseePageDTO
	 * @return
	 */
	@ApiOperation(value = "分页查询标的列表", notes = "分页查询标的列表")
	@GetMapping("/queryTargetPage")
	public R queryTargetPage(Page page, TargetAssetsReAssetsCaseePageDTO targetAssetsReAssetsCaseePageDTO) {
		return R.ok(assetsRePaifuService.queryTargetPage(page, targetAssetsReAssetsCaseePageDTO));
	}

	/**
	 * 根据财产id、项目id和案件id查询财产详情
	 * @param assetsId
	 * @param projectId
	 * @param caseeId
	 * @return
	 */
	@ApiOperation(value = "根据财产id查询财产详情", notes = "根据财产id查询财产详情")
	@GetMapping("/queryAssetsPaifuById")
	public R queryAssetsPaifuById(Integer assetsId, Integer projectId, Integer caseeId) {
		return R.ok(this.assetsRePaifuService.queryAssetsPaifuById(assetsId, projectId, caseeId));
	}

}
