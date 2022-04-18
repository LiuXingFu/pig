package com.pig4cloud.pig.casee.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.TargetAssetsReAssetsCaseePageDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.mapper.AssetsRePaifuMapper;
import com.pig4cloud.pig.casee.service.AssetsRePaifuService;
import com.pig4cloud.pig.casee.vo.AssetsPaifuVO;
import com.pig4cloud.pig.casee.vo.TargetAssetsReAssetsCaseeVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetsRePaifuServiceImpl extends ServiceImpl<AssetsRePaifuMapper, AssetsRe> implements AssetsRePaifuService {

	@Autowired
	RemoteAddressService remoteAddressService;

	@Autowired
	RemoteSubjectService remoteSubjectService;

	@Override
	public IPage<TargetAssetsReAssetsCaseeVO> queryTargetPage(Page page, TargetAssetsReAssetsCaseePageDTO targetAssetsReAssetsCaseePageDTO) {
		return this.baseMapper.queryTargetPage(page, targetAssetsReAssetsCaseePageDTO);
	}


	@Override
	public AssetsPaifuVO queryAssetsPaifuById(Integer assetsId, Integer projectId, Integer caseeId) {
		AssetsPaifuVO assetsPaifuVO = this.baseMapper.queryAssetsPaifuById(assetsId, projectId, caseeId);
		Address address = remoteAddressService.queryAssetsByTypeIdAndType(assetsPaifuVO.getAssetsId(), 4, SecurityConstants.FROM).getData();
		assetsPaifuVO.setAddress(address);
		return assetsPaifuVO;
	}
}
