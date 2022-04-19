package com.pig4cloud.pig.casee.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePageDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePaifuSaveDTO;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.AssetsReSubject;
import com.pig4cloud.pig.casee.entity.paifuentity.AssetsRePaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.AssetsRePaifuDetail;
import com.pig4cloud.pig.casee.entity.AssetsReSubject;
import com.pig4cloud.pig.casee.entity.MortgageAssetsRe;
import com.pig4cloud.pig.casee.entity.MortgageAssetsSubjectRe;
import com.pig4cloud.pig.casee.mapper.AssetsRePaifuMapper;
import com.pig4cloud.pig.casee.service.AssetsRePaifuService;
import com.pig4cloud.pig.casee.service.AssetsReSubjectService;
import com.pig4cloud.pig.casee.service.MortgageAssetsReService;
import com.pig4cloud.pig.casee.service.MortgageAssetsRecordsService;
import com.pig4cloud.pig.casee.vo.AssetsPaifuVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePageVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AssetsRePaifuServiceImpl extends ServiceImpl<AssetsRePaifuMapper, AssetsRe> implements AssetsRePaifuService {
	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;
	@Autowired
	RemoteAddressService remoteAddressService;

	@Autowired
	RemoteSubjectService remoteSubjectService;
	@Autowired
	AssetsService assetsService;
	@Autowired
	AssetsReSubjectService assetsReSubjectService;
	@Autowired
	RemoteAddressService addressService;

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

	@Override
	@Transactional
	public	Integer saveAssetsRe(AssetsRePaifuSaveDTO assetsRePaifuSaveDTO){
		Assets assets = new Assets();
		BeanCopyUtil.copyBean(assetsRePaifuSaveDTO,assets);
		assetsService.saveOrUpdate(assets);

		AssetsRePaifu assetsRePaifu = new AssetsRePaifu();
		BeanCopyUtil.copyBean(assetsRePaifuSaveDTO,assetsRePaifu);
		AssetsRePaifuDetail assetsRePaifuDetail = new AssetsRePaifuDetail();
		BeanCopyUtil.copyBean(assetsRePaifuSaveDTO,assetsRePaifuDetail);
		assetsRePaifu.setAssetsRePaifuDetail(assetsRePaifuDetail);
		assetsRePaifu.setAssetsId(assets.getAssetsId());
		assetsRePaifu.setCreateCaseeId(assetsRePaifuSaveDTO.getCaseeId());
		if(assetsRePaifuSaveDTO.getMortgagee()==0){
			assetsRePaifu.setAssetsSource(1);
		}else{
			assetsRePaifu.setAssetsSource(2);
		}
		Integer save = this.baseMapper.insert(assetsRePaifu);

		// 批量保存财产债务人
		List<AssetsReSubject> assetsReSubjects = new ArrayList<>();
		for(Integer subjectId:assetsRePaifuSaveDTO.getSubjectIdList()){
			AssetsReSubject assetsReSubject = new AssetsReSubject();
			assetsReSubject.setSubjectId(subjectId);
			assetsReSubject.setAssetsReId(assetsRePaifu.getAssetsReId());
			assetsReSubjects.add(assetsReSubject);
		}
		assetsReSubjectService.saveBatch(assetsReSubjects);

		if(assetsRePaifuSaveDTO.getAssetsId()==null && assetsRePaifuSaveDTO.getCode()==null){
			Address address = new Address();
			BeanCopyUtil.copyBean(assetsRePaifuSaveDTO,address);
			address.setType(4);
			address.setUserId(assets.getAssetsId());
			addressService.saveAddress(address,SecurityConstants.FROM);
		}
		return save;
	}
}
