package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.CourtExcelDTO;
import com.pig4cloud.pig.admin.api.entity.*;
import com.pig4cloud.pig.admin.api.vo.InsOutlesCourtReVO;
import com.pig4cloud.pig.admin.mapper.CourtMapper;
import com.pig4cloud.pig.admin.service.*;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CourtServiceImpl extends ServiceImpl<CourtMapper, Court> implements CourtService {

	@Autowired
	InsOutlesCourtReService insOutlesCourtReService;

	@Autowired
	RegionService regionService;

	@Override
	public List<Court> getByRegionCodeOrCourtName(Integer regionCode, String courtName) {
		return this.baseMapper.getByRegionCodeOrCourtName(regionCode, courtName);
	}

	@Override
	public IPage<Court> getCourtPageList(Page page, Court court) {
		return this.baseMapper.getCourtPageList(page, court);
	}

	@Override
	public IPage<Court> queryCourtPage(Page page, String courtName) {
		return this.baseMapper.queryCourtPage(page, courtName, null);
	}

	@Override
	public IPage<Court> queryCourtPageByInsIdAndOutlesId(Page page, Integer insId, Integer outlesId, String courtName) {
		List<Integer> courIds = this.insOutlesCourtReService.list(new LambdaQueryWrapper<InsOutlesCourtRe>()
				.eq(InsOutlesCourtRe::getInsId, insId)
				.eq(InsOutlesCourtRe::getOutlesId, outlesId))
				.stream().map(InsOutlesCourtRe::getCourtId)
				.collect(Collectors.toList());
		return this.baseMapper.queryCourtPage(page, courtName, courIds);
	}

	@Override
	public void importCourt(List<CourtExcelDTO> courtExcelDTOList) {
		for (CourtExcelDTO courtExcelDTO : courtExcelDTOList) {
			Court court = this.getOne(new LambdaQueryWrapper<Court>().eq(Court::getCourtName, courtExcelDTO.getCourtName()));
			if (Objects.nonNull(court)) {
				BeanCopyUtil.copyBean(courtExcelDTO, court);
				this.updateById(court);
			}
		}
	}

	@Override
	public void setRegion() {
		List<Court> list = this.list();
		for (Court court : list) {
			String selectAreaCode = court.getRegionCode();
			String selectCityCode = String.valueOf((int)Math.floor((int)(Integer.valueOf(selectAreaCode)/ 100)) * 100);
			String selectProvinceCode = String.valueOf((int)Math.floor((int)(Integer.valueOf(selectAreaCode)/ 10000)) * 10000);
			List<Region> provinceList = regionService.getProvinceList(Integer.valueOf(-1));
			List<Region> cityList = regionService.getProvinceList(Integer.valueOf(selectProvinceCode));
			List<Region> areaList = regionService.getProvinceList(Integer.valueOf(selectCityCode));

			if (cityList.size() > 0 && areaList.size() > 0){
				if (cityList.get(0).getCode().equals(areaList.get(0).getCode())) {
					areaList = new ArrayList<>();
				}
			}

			String province = null;

			String city = null;

			String area = null;

			String information_address = null;

			for (Region region : provinceList) {
				if (region.getCode().equals(selectProvinceCode)) {
					province = region.getName();
				}
			}

			for (Region region : cityList) {
				if (region.getCode().equals(selectCityCode)) {
					city = region.getName();
				}
			}

			for (Region region : areaList) {
				if (region.getCode().equals(selectAreaCode)) {
					area = region.getName();
				}
			}

			if (Objects.nonNull(province)) {
				information_address = province;
			}

			if (Objects.nonNull(city)) {
				information_address += city;
			}

			if (Objects.nonNull(area)) {
				information_address += area;
			}

			System.out.println(province);
			System.out.println(city);
			System.out.println(area);
			System.out.println(information_address);

			court.setProvince(province);
			court.setCity(city);
			court.setArea(area);
			court.setInformationAddress(information_address);

			this.updateById(court);

		}

	}
}
