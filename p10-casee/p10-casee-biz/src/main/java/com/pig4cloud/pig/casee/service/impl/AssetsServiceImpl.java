/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.casee.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.admin.api.vo.SubjectVO;
import com.pig4cloud.pig.casee.SysUserEXport;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.mapper.AssetsMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.AssetsDeailsVO;
import com.pig4cloud.pig.casee.vo.AssetsOrProjectPageVO;
import com.pig4cloud.pig.casee.vo.AssetsPageVO;
import com.pig4cloud.pig.casee.vo.ExportXlsAssetsOrProjectVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ?????????
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Service
public class AssetsServiceImpl extends ServiceImpl<AssetsMapper, Assets> implements AssetsService {
	@Autowired
	RemoteAddressService remoteAddressService;
	@Autowired
	AssetsBankLoanReService assetsBankLoanReService;
	@Autowired
	JurisdictionUtilsService jurisdictionUtilsService;
	@Autowired
	MortgageAssetsRecordsService mortgageAssetsRecordsService;
	@Autowired
	MortgageAssetsReService mortgageAssetsReService;
	@Autowired
	MortgageAssetsSubjectReService mortgageAssetsSubjectReService;
	@Autowired
	private RemoteSubjectService remoteSubjectService;

	@Override
	public AssetsGetByIdDTO getByAssets(Integer assetsId) {
		return this.baseMapper.getByAssets(assetsId);
	}

	@Override
	@Transactional
	public boolean saveMortgageAssets(MortgageAssetsAllDTO mortgageAssetsAllDTO) {
		for (MortgageAssetsDTO mortgageAssetsDTO : mortgageAssetsAllDTO.getMortgageAssetsDTOList()) {
			//??????????????????
			Assets assets = new Assets();
			//????????????
			MortgageAssetsRecords mortgageAssetsRecords = new MortgageAssetsRecords();
			BeanCopyUtil.copyBean(mortgageAssetsDTO, mortgageAssetsRecords);
			mortgageAssetsRecords.setBankLoanId(mortgageAssetsAllDTO.getBankLoanId());
			mortgageAssetsRecordsService.save(mortgageAssetsRecords);

			//????????????????????????
			MortgageAssetsRe mortgageAssetsRe = new MortgageAssetsRe();


			assets.setType(20200);//????????????????????????

			if (mortgageAssetsDTO.getAssetsDTOList() != null) {
				for (AssetsDTO assetsDTO : mortgageAssetsDTO.getAssetsDTOList()) {
					List<Integer> subjectIdList = assetsDTO.getSubjectId();
					if (assetsDTO.getAssetsId() == null) {
						BeanCopyUtil.copyBean(assetsDTO, assets);
						this.save(assets);//??????????????????
						Address address = new Address();
						BeanUtils.copyProperties(assetsDTO, address);
						address.setType(4);
						address.setUserId(assets.getAssetsId());
						remoteAddressService.saveAddress(address, SecurityConstants.FROM);//????????????????????????
						mortgageAssetsRe.setAssetsId(assets.getAssetsId());
					} else {
						mortgageAssetsRe.setAssetsId(assetsDTO.getAssetsId());
					}
					mortgageAssetsRe.setMortgageAssetsRecordsId(mortgageAssetsRecords.getMortgageAssetsRecordsId());
					mortgageAssetsRe.setSubjectName(assetsDTO.getSubjectName());
					mortgageAssetsReService.save(mortgageAssetsRe);//??????????????????????????????

					//???????????????????????????
					List<MortgageAssetsSubjectRe> mortgageAssetsSubjectRes = new ArrayList<>();
					if(subjectIdList != null && subjectIdList.size()>0){
						for (Integer subjectId : subjectIdList) {
							MortgageAssetsSubjectRe mortgageAssetsSubjectRe = new MortgageAssetsSubjectRe();
							mortgageAssetsSubjectRe.setMortgageAssetsReId(mortgageAssetsRe.getMortgageAssetsReId());

							mortgageAssetsSubjectRe.setSubjectId(subjectId);

							mortgageAssetsSubjectRes.add(mortgageAssetsSubjectRe);
						}
					} else if(assetsDTO.getUnifiedIdentityList() != null && assetsDTO.getUnifiedIdentityList().size()>0){
						for (String unifiedIdentity : assetsDTO.getUnifiedIdentityList()) {
							MortgageAssetsSubjectRe mortgageAssetsSubjectRe = new MortgageAssetsSubjectRe();
							mortgageAssetsSubjectRe.setMortgageAssetsReId(mortgageAssetsRe.getMortgageAssetsReId());
							R<SubjectVO> subjectVOR =  remoteSubjectService.getByUnifiedIdentity(unifiedIdentity,SecurityConstants.FROM);
							mortgageAssetsSubjectRe.setSubjectId(subjectVOR.getData().getSubjectId());
							mortgageAssetsSubjectRes.add(mortgageAssetsSubjectRe);
						}
					}
					mortgageAssetsSubjectReService.saveBatch(mortgageAssetsSubjectRes);//?????????????????????????????????
				}
			}
		}
		return true;
	}

	/**
	 * ????????????id???????????????????????????????????????
	 *
	 * @param assetsId
	 * @return
	 */
	@Override
	public AssetsDeailsVO queryById(Integer assetsId) {
		return this.baseMapper.queryById(assetsId);
	}

	/**
	 * ???????????????????????????????????????????????????
	 *
	 * @param page
	 * @param assetsOrProjectPageDTO
	 * @return
	 */
	@Override
	public IPage<AssetsOrProjectPageVO> getPageAssetsManage(Page page, AssetsOrProjectPageDTO assetsOrProjectPageDTO) {
		return this.baseMapper.getPageAssetsManage(page, assetsOrProjectPageDTO, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

//	@Override
//	public void exportXls(HttpServletResponse response, AssetsOrProjectPageDTO assetsOrProjectPageDTO) throws Exception {
//		List<ExportXlsAssetsOrProjectVO> listAssetsManage = this.baseMapper.getListAssetsManage(assetsOrProjectPageDTO);
//		writeExcel(response, listAssetsManage, "??????????????????", "sheet", ExportXlsAssetsOrProjectVO.class);
//	}

	@Override
	public void exportXls(AssetsOrProjectPageDTO assetsOrProjectPageDTO) throws Exception {
		List<ExportXlsAssetsOrProjectVO> listAssetsManage = this.baseMapper.getListAssetsManage(assetsOrProjectPageDTO);
		ExportXlsAssetsOrProjectVO exportXlsAssetsOrProjectVO = listAssetsManage.get(0);
		SysUserEXport sysUserEXport = new SysUserEXport();
		sysUserEXport.setName("??????");
		sysUserEXport.setAge(38);
		sysUserEXport.setPhone("18575504887");
		sysUserEXport.setAddress("????????????????????????????????????");
		sysUserEXport.setSex(0);

		SysUserEXport sysUserEXport1 = new SysUserEXport();
		sysUserEXport1.setName("??????");
		sysUserEXport1.setAge(30);
		sysUserEXport1.setPhone("18675504887");
		sysUserEXport1.setAddress("????????????????????????????????????");
		sysUserEXport1.setSex(1);

		SysUserEXport sysUserEXport2 = new SysUserEXport();
		sysUserEXport2.setName("??????");
		sysUserEXport2.setAge(38);
		sysUserEXport2.setPhone("19875504887");
		sysUserEXport2.setAddress("????????????????????????????????????");
		sysUserEXport2.setSex(0);

		List<SysUserEXport> sysUserEXports = new ArrayList<>();

		sysUserEXports.add(sysUserEXport);
		sysUserEXports.add(sysUserEXport1);
		sysUserEXports.add(sysUserEXport2);

		exportXlsAssetsOrProjectVO.setSysUserList(sysUserEXports);
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"??????"),
				ExportXlsAssetsOrProjectVO.class, listAssetsManage);
		File savefile = new File("D:/excel/");
		if (!savefile.exists()) {
			savefile.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream("D:/excel/??????????????????.xls");
		workbook.write(fos);
		fos.close();
	}

	@Override
	public IPage<AssetsOrProjectPageVO> getPageDebtorAssets(Page page, AssetsOrProjectPageDTO assetsOrProjectPageDTO) {
		return this.baseMapper.getPageDebtorAssets(page, assetsOrProjectPageDTO, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

//	private void writeExcel(HttpServletResponse response, List<ExportXlsAssetsOrProjectVO> list, String fileName, String sheetName, Class<ExportXlsAssetsOrProjectVO> clazz) throws Exception {
//		response.setCharacterEncoding("utf8");
//		response.setContentType("application/vnd.ms-excel;charset=utf-8");
//		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8")); //?????????
//		response.setHeader("Cache-Control", "no-store");
//		response.addHeader("Cache-Control", "max-age=0");
//		EasyExcel.write(response.getOutputStream(), clazz)
//				.sheet(sheetName) //sheet???
//				.doWrite(list);
//	}

	/**
	 * **************************************************************************
	 */

	@Override
	public IPage<AssetsPageVO> queryPageByCaseeId(Page page, Integer caseeId) {
		return this.baseMapper.selectPageByCaseeId(page, caseeId);
	}

	@Override
	@Transactional
	public Integer saveAssets(AssetsAddDTO assetsAddDTO) {
		return 0;
	}

	@Override
	public List<AssetsDeailsVO> queryByAssetsName(String assetsName) {
		return this.baseMapper.queryByAssetsName(assetsName);
	}

	@Override
	public IPage<AssetsPageVO> queryAssetsPage(Page page, AssetsOrProjectPageDTO assetsOrProjectPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));

		assetsOrProjectPageDTO.setOrders(JsonUtils.jsonToList(assetsOrProjectPageDTO.getOrdersJson(), OrderItem.class));

		page.setOrders(assetsOrProjectPageDTO.getOrders());

		return this.baseMapper.selectAssetsPage(page, assetsOrProjectPageDTO, insOutlesDTO);
	}

	@Override
	public AssetsPageVO getByAssetsId(Integer assetsId) {
		return this.baseMapper.getByAssetsId(assetsId);
	}

	@Override
	public Assets queryAssetsByTargetId(Integer targetId) {
		return this.baseMapper.queryAssetsByTargetId(targetId);
	}


}
