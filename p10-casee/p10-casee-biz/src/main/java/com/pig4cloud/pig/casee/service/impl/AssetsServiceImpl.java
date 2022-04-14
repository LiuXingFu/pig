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

//import com.alibaba.excel.EasyExcel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.casee.SysUserEXport;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.AssetsBankLoanRe;
import com.pig4cloud.pig.casee.mapper.AssetsMapper;
import com.pig4cloud.pig.casee.service.AssetsBankLoanReService;
import com.pig4cloud.pig.casee.service.AssetsService;
import com.pig4cloud.pig.casee.vo.AssetsDeailsVO;
import com.pig4cloud.pig.casee.vo.AssetsOrProjectPageVO;
import com.pig4cloud.pig.casee.vo.AssetsPageVO;
import com.pig4cloud.pig.casee.vo.ExportXlsAssetsOrProjectVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 财产表
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

	@Override
	public AssetsGetByIdDTO getByAssets(Integer assetsId) {
		return this.baseMapper.getByAssets(assetsId);
	}

	@Override
	public boolean saveAssets(BankLoanDTO bankLoanDTO) {
		//抵押财产信息
		Assets assets = new Assets();
		//财产关联银行借贷信息
		AssetsBankLoanRe assetsBankLoanRe = new AssetsBankLoanRe();

		assets.setType(20200);//默认实体财产类型

		if (bankLoanDTO.getAssetsDTOList() != null) {
			for (AssetsDTO assetsDTO : bankLoanDTO.getAssetsDTOList()) {
				if (assetsDTO.getAssetsId() == null) {
					BeanCopyUtil.copyBean(assetsDTO, assets);
					this.save(assets);//添加财产信息

					Address address = new Address();
					BeanUtils.copyProperties(assetsDTO, address);
					address.setType(4);
					address.setUserId(assets.getAssetsId());
					remoteAddressService.saveAddress(address, SecurityConstants.FROM);//添加财产地址信息
				}
				if (assetsDTO.getAssetsId() == null) {
					assetsBankLoanRe.setAssetsId(assets.getAssetsId());
				} else {
					assetsBankLoanRe.setAssetsId(assetsDTO.getAssetsId());
				}
				//添加财产关联银行借贷信息
				assetsBankLoanRe.setBankLoanId(assetsDTO.getBankLoanId());
				assetsBankLoanRe.setSubjectId(assetsDTO.getSubjectId());
				assetsBankLoanRe.setMortgageTime(assetsDTO.getMortgageTime());
				assetsBankLoanRe.setMortgageAmount(assetsDTO.getMortgageAmount());
				assetsBankLoanReService.save(assetsBankLoanRe);
			}
		}
		return true;
	}

	/**
	 * 根据财产id查询财产信息与财产地址信息
	 *
	 * @param assetsId
	 * @return
	 */
	@Override
	public AssetsDeailsVO queryById(Integer assetsId) {
		return this.baseMapper.queryById(assetsId);
	}

	/**
	 * 根据特定条件分页查询财产与项目数据
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
//		writeExcel(response, listAssetsManage, "财产信息列表", "sheet", ExportXlsAssetsOrProjectVO.class);
//	}

	@Override
	public void exportXls(AssetsOrProjectPageDTO assetsOrProjectPageDTO) throws Exception {
		List<ExportXlsAssetsOrProjectVO> listAssetsManage = this.baseMapper.getListAssetsManage(assetsOrProjectPageDTO);
		ExportXlsAssetsOrProjectVO exportXlsAssetsOrProjectVO = listAssetsManage.get(0);
		SysUserEXport sysUserEXport = new SysUserEXport();
		sysUserEXport.setName("张三");
		sysUserEXport.setAge(38);
		sysUserEXport.setPhone("18575504887");
		sysUserEXport.setAddress("湖南省郴州市交警第三支队");
		sysUserEXport.setSex(0);

		SysUserEXport sysUserEXport1 = new SysUserEXport();
		sysUserEXport1.setName("李四");
		sysUserEXport1.setAge(30);
		sysUserEXport1.setPhone("18675504887");
		sysUserEXport1.setAddress("湖南省郴州市交警第三支队");
		sysUserEXport1.setSex(1);

		SysUserEXport sysUserEXport2 = new SysUserEXport();
		sysUserEXport2.setName("王五");
		sysUserEXport2.setAge(38);
		sysUserEXport2.setPhone("19875504887");
		sysUserEXport2.setAddress("湖南省郴州市交警第三支队");
		sysUserEXport2.setSex(0);

		List<SysUserEXport> sysUserEXports = new ArrayList<>();

		sysUserEXports.add(sysUserEXport);
		sysUserEXports.add(sysUserEXport1);
		sysUserEXports.add(sysUserEXport2);

		exportXlsAssetsOrProjectVO.setSysUserList(sysUserEXports);
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"测试"),
				ExportXlsAssetsOrProjectVO.class, listAssetsManage);
		File savefile = new File("D:/excel/");
		if (!savefile.exists()) {
			savefile.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream("D:/excel/财产导出测试.xls");
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
//		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8")); //文件名
//		response.setHeader("Cache-Control", "no-store");
//		response.addHeader("Cache-Control", "max-age=0");
//		EasyExcel.write(response.getOutputStream(), clazz)
//				.sheet(sheetName) //sheet名
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
		return this.baseMapper.selectAssetsPage(page, assetsOrProjectPageDTO, insOutlesDTO);
	}

	@Override
	public AssetsPageVO getByAssetsId(Integer assetsId) {
		return this.baseMapper.getByAssetsId(assetsId);
	}


}
