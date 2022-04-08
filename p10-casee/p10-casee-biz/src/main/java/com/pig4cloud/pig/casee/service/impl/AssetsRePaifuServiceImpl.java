package com.pig4cloud.pig.casee.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.TargetAssetsReAssetsCaseePageDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.mapper.AssetsRePaifuMapper;
import com.pig4cloud.pig.casee.service.AssetsRePaifuService;
import com.pig4cloud.pig.casee.vo.TargetAssetsReAssetsCaseeVO;
import org.springframework.stereotype.Service;

@Service
public class AssetsRePaifuServiceImpl extends ServiceImpl<AssetsRePaifuMapper, AssetsRe> implements AssetsRePaifuService {

	@Override
	public IPage<TargetAssetsReAssetsCaseeVO> queryTargetPage(Page page, TargetAssetsReAssetsCaseePageDTO targetAssetsReAssetsCaseePageDTO) {
		return this.baseMapper.queryTargetPage(page, targetAssetsReAssetsCaseePageDTO);
	}
}
