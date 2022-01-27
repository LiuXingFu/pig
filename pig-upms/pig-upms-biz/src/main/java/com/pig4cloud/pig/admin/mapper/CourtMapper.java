package com.pig4cloud.pig.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.Court;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourtMapper extends BaseMapper<Court> {

	List<Court> getByRegionCodeOrCourtName(@Param("regionCode") Integer regionCode,@Param("courtName") String courtName);

	IPage<Court> getCourtPageList(Page page, @Param("query") Court court);
}
