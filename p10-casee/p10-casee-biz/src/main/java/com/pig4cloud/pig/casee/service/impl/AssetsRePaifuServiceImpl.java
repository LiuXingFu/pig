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
import com.pig4cloud.pig.casee.dto.ProjectStatusSaveDTO;
import com.pig4cloud.pig.casee.dto.TargetAddDTO;
import com.pig4cloud.pig.casee.dto.paifu.*;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.AssetsRePaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.AssetsRePaifuDetail;
import com.pig4cloud.pig.casee.mapper.AssetsRePaifuMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.AssetsPaifuVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePageVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePaifuDetailVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsReTargetPageVO;
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
	@Autowired
	ProjectPaifuService projectPaifuService;
	@Autowired
	ProjectStatusService projectStatusService;

	@Override
	public IPage<AssetsRePageVO> queryAssetsRePageByProjectId(Page page, AssetsRePageDTO assetsRePageDTO) {
		return this.baseMapper.queryAssetsRePageByProjectId(page, assetsRePageDTO);
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

		//添加任务数据以及程序信息
		Project project = projectPaifuService.getById(assetsRePaifuSaveDTO.getProjectId());
		TargetAddDTO targetAddDTO = new TargetAddDTO();
		targetAddDTO.setCaseeId(assetsRePaifuSaveDTO.getCaseeId());
		targetAddDTO.setProcedureNature(6060);
		targetAddDTO.setOutlesId(project.getOutlesId());
		targetAddDTO.setProjectId(assetsRePaifuSaveDTO.getProjectId());
		targetAddDTO.setGoalId(assets.getAssetsId());
		targetAddDTO.setGoalType(20001);
		try {
			targetService.saveTargetAddDTO(targetAddDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return save;
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

	@Override
	public IPage<AssetsReTargetPageVO> queryTargetPage(Page page, AssetsReTargetPageDTO assetsReTargetPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryTargetPage(page,assetsReTargetPageDTO,insOutlesDTO);
	}

	@Override
	@Transactional
	public Integer modifyAssetsReStatus(AssetsReModifyStatusDTO assetsReModifyStatusDTO){
		ProjectStatusSaveDTO projectStatusSaveDTO = new ProjectStatusSaveDTO();
		BeanCopyUtil.copyBean(assetsReModifyStatusDTO,projectStatusSaveDTO);
		projectStatusSaveDTO.setStatusVal(assetsReModifyStatusDTO.getStatus());
		projectStatusSaveDTO.setSourceId(assetsReModifyStatusDTO.getAssetsReId());
		projectStatusSaveDTO.setType(3);
		projectStatusService.saveStatusRecord(projectStatusSaveDTO);

		AssetsRePaifu assetsRePaifu = new AssetsRePaifu();
		assetsRePaifu.setAssetsReId(assetsReModifyStatusDTO.getAssetsReId());
		assetsRePaifu.setStatus(assetsReModifyStatusDTO.getStatus());
		return this.baseMapper.updateById(assetsRePaifu);
	}
}
