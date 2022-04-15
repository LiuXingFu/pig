package com.pig4cloud.pig.casee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.TargetAssetsReAssetsCaseePageDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.vo.AssetsPaifuVO;
import com.pig4cloud.pig.casee.vo.TargetAssetsReAssetsCaseeVO;

public interface AssetsRePaifuService extends IService<AssetsRe> {

	IPage<TargetAssetsReAssetsCaseeVO> queryTargetPage(Page page, TargetAssetsReAssetsCaseePageDTO targetAssetsReAssetsCaseePageDTO);

	AssetsPaifuVO queryAssetsPaifuById(Integer assetsId, Integer projectId, Integer caseeId);
}
