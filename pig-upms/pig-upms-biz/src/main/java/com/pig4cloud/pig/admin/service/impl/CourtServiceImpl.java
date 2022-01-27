package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Court;
import com.pig4cloud.pig.admin.mapper.CourtMapper;
import com.pig4cloud.pig.admin.service.CourtService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtServiceImpl extends ServiceImpl<CourtMapper, Court> implements CourtService {
	@Override
	public List<Court> getByRegionCodeOrCourtName(Integer regionCode,String courtName) {
		return this.baseMapper.getByRegionCodeOrCourtName(regionCode,courtName);
	}

	@Override
	public IPage<Court> getCourtPageList(Page page, Court court) {
		return this.baseMapper.getCourtPageList(page, court);
	}
}
