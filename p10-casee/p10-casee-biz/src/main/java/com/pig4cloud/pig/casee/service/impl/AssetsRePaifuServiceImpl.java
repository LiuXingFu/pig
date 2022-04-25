package com.pig4cloud.pig.casee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.DelAssetsTransferDTO;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePageDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePaifuModifyDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePaifuSaveDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.paifuentity.AssetsRePaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.AssetsRePaifuDetail;
import com.pig4cloud.pig.casee.mapper.AssetsRePaifuMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.AssetsPaifuVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePageVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePaifuDetailVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
	AssetsRePaifuService assetsRePaifuService;
	@Autowired
	private TargetService targetService;
	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	CaseeHandlingRecordsService caseeHandlingRecordsService;
	@Autowired
	AssetsLiquiTransferRecordReService assetsLiquiTransferRecordReService;

	@Override
	public IPage<AssetsRePageVO> queryAssetsRePageByProjectId(Page page, AssetsRePageDTO assetsRePageDTO) {
		return this.baseMapper.queryAssetsRePageByProjectId(page, assetsRePageDTO);
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

	/**
	 * 删除移交财产相关信息
	 * @param delAssetsTransferDTO
	 * @return
	 */
	@Override
	public int deleteAssetsTransfer(DelAssetsTransferDTO delAssetsTransferDTO) {

		int delete = 0;

		AssetsRe assetsRe = assetsRePaifuService.getById(delAssetsTransferDTO.getAssetsReDTO().getAssetsReId());

		//查询该财产程序信息
		Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getCaseeId, assetsRe.getCaseeId()).eq(Target::getGoalId, assetsRe.getAssetsId()).eq(Target::getGoalType, 20001).eq(Target::getProcedureNature, 4040));

		TaskNode entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, assetsRe.getProjectId()).eq(TaskNode::getCaseeId, assetsRe.getCaseeId()).eq(TaskNode::getTargetId, target.getTargetId())
				.eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ"));

		entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.setStatus(0);

		taskNodeService.updateById(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ);

		caseeHandlingRecordsService.remove(new LambdaQueryWrapper<CaseeHandlingRecords>()
				.eq(CaseeHandlingRecords::getProjectId, entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getProjectId())
				.eq(CaseeHandlingRecords::getCaseeId, entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getCaseeId())
				.eq(CaseeHandlingRecords::getTargetId, entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getTargetId())
				.eq(CaseeHandlingRecords::getNodeId, entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getNodeId()));

		assetsLiquiTransferRecordReService.remove(new LambdaQueryWrapper<AssetsLiquiTransferRecordRe>()
				.eq(AssetsLiquiTransferRecordRe::getLiquiTransferRecordId, delAssetsTransferDTO.getLiquiTransferRecordId())
				.eq(AssetsLiquiTransferRecordRe::getAssetsReId, delAssetsTransferDTO.getAssetsReDTO().getAssetsReId()));

		return delete+=1;
	}

	@Override
	public AssetsRePaifuDetailVO getAssetsReById(Integer assetsReId){
		return this.baseMapper.selectByAssetsReId(assetsReId);
	}

	@Override
	@Transactional
	public 	Integer modifyByAssetsReId(AssetsRePaifuModifyDTO assetsRePaifuModifyDTO){
		AssetsRePaifu assetsRePaifu = new AssetsRePaifu();
		BeanCopyUtil.copyBean(assetsRePaifuModifyDTO,assetsRePaifu);
		if(assetsRePaifuModifyDTO.getAssetsRePaifuDetail().getMortgagee()==0){
			assetsRePaifu.setAssetsSource(1);
		}else{
			assetsRePaifu.setAssetsSource(2);
		}
		Integer modify = this.baseMapper.updateById(assetsRePaifu);

		QueryWrapper<AssetsReSubject> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsReSubject::getAssetsReId,assetsRePaifuModifyDTO.getAssetsReId());
		assetsReSubjectService.remove(queryWrapper);

		List<AssetsReSubject> assetsReSubjects = new ArrayList<>();
		for(Integer subjectId :assetsRePaifuModifyDTO.getSubjectIdList()){
			AssetsReSubject assetsReSubject = new AssetsReSubject();
			assetsReSubject.setSubjectId(subjectId);
			assetsReSubject.setAssetsReId(assetsRePaifuModifyDTO.getAssetsReId());
			assetsReSubjects.add(assetsReSubject);
		}
		assetsReSubjectService.saveBatch(assetsReSubjects);

		Assets assets = new Assets();
		BeanCopyUtil.copyBean(assetsRePaifuModifyDTO,assets);
		assetsService.updateById(assets);

		Address address = new Address();
		BeanCopyUtil.copyBean(assetsRePaifuModifyDTO,address);
		address.setUserId(assetsRePaifuModifyDTO.getAssetsId());
		address.setType(4);
		addressService.saveOrUpdate(address,SecurityConstants.FROM);

		return modify;
	}
}
