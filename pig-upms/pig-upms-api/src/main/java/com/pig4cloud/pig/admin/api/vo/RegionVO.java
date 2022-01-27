package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.Region;
import lombok.Data;

import java.util.List;

@Data
public class RegionVO extends Region {

	List<RegionVO> regionVOList;

}
