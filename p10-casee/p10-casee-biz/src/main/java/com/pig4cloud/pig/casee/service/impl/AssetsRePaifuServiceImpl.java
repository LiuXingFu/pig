package com.pig4cloud.pig.casee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.TargetAssetsReAssetsCaseePageDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.AssetsReSubject;
import com.pig4cloud.pig.casee.entity.MortgageAssetsRe;
import com.pig4cloud.pig.casee.entity.MortgageAssetsSubjectRe;
import com.pig4cloud.pig.casee.mapper.AssetsRePaifuMapper;
import com.pig4cloud.pig.casee.service.AssetsRePaifuService;
import com.pig4cloud.pig.casee.service.AssetsReSubjectService;
import com.pig4cloud.pig.casee.service.MortgageAssetsReService;
import com.pig4cloud.pig.casee.service.MortgageAssetsRecordsService;
import com.pig4cloud.pig.casee.vo.AssetsPaifuVO;
import com.pig4cloud.pig.casee.vo.TargetAssetsReAssetsCaseeVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AssetsRePaifuServiceImpl extends ServiceImpl<AssetsRePaifuMapper, AssetsRe> implements AssetsRePaifuService {

	@Autowired
	RemoteAddressService remoteAddressService;

	@Autowired
	RemoteSubjectService remoteSubjectService;

	@Autowired
	MortgageAssetsSubjectReServiceImpl mortgageAssetsSubjectReService;

	@Autowired
	MortgageAssetsReService mortgageAssetsReService;

	@Autowired
	AssetsReSubjectService assetsReSubjectService;

	@Override
	public IPage<TargetAssetsReAssetsCaseeVO> queryTargetPage(Page page, TargetAssetsReAssetsCaseePageDTO targetAssetsReAssetsCaseePageDTO) {
		return this.baseMapper.queryTargetPage(page, targetAssetsReAssetsCaseePageDTO);
	}


	@Override
	public AssetsPaifuVO queryAssetsPaifuById(Integer assetsId, Integer projectId, Integer caseeId) {
		AssetsPaifuVO assetsPaifuVO = this.baseMapper.queryAssetsPaifuById(assetsId, projectId, caseeId);
		Address address = remoteAddressService.queryAssetsByTypeIdAndType(assetsPaifuVO.getAssetsId(), 4, SecurityConstants.FROM).getData();
		if (Objects.nonNull(address)) {
			assetsPaifuVO.setAddressId(address.getAddressId());
			assetsPaifuVO.setProvince(address.getProvince());
			assetsPaifuVO.setArea(address.getArea());
			assetsPaifuVO.setCity(address.getCity());
			assetsPaifuVO.setCode(address.getCode());
		}

		List<Integer> subjectIds = this.assetsReSubjectService.list(new LambdaQueryWrapper<AssetsReSubject>()
				.eq(AssetsReSubject::getAssetsReId, assetsPaifuVO.getAssetsReId())).stream().map(AssetsReSubject::getSubjectId).collect(Collectors.toList());

		assetsPaifuVO.setSubjectIds(subjectIds);

		return assetsPaifuVO;
	}
}
