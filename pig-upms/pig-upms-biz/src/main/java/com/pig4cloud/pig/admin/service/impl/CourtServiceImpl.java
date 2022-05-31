package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Court;
import com.pig4cloud.pig.admin.api.entity.InsOutlesCourtRe;
import com.pig4cloud.pig.admin.api.entity.Outles;
import com.pig4cloud.pig.admin.api.entity.RelationshipAuthenticate;
import com.pig4cloud.pig.admin.api.vo.InsOutlesCourtReVO;
import com.pig4cloud.pig.admin.mapper.CourtMapper;
import com.pig4cloud.pig.admin.service.CourtService;
import com.pig4cloud.pig.admin.service.InsOutlesCourtReService;
import com.pig4cloud.pig.admin.service.OutlesService;
import com.pig4cloud.pig.admin.service.RelationshipAuthenticateService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourtServiceImpl extends ServiceImpl<CourtMapper, Court> implements CourtService {

	@Autowired
	InsOutlesCourtReService insOutlesCourtReService;

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
}
