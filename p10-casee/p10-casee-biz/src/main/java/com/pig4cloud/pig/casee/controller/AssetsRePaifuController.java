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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assetsRePaifu" )
@Api(value = "assetsRePaifu", tags = "财产关联表管理")
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

}
