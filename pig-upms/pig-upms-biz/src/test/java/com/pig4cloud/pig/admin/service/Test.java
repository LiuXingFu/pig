package com.pig4cloud.pig.admin.service;

import com.pig4cloud.pig.admin.api.entity.Region;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

	@Autowired
	RegionService regionService;

	@org.junit.Test
	public void test1() {
		String selectAreaCode = "110000";
		String selectCityCode = String.valueOf((int)Math.floor((int)(110000/ 100)) * 100);
		String selectProvinceCode = String.valueOf((int)Math.floor((int)(110000/ 10000)) * 10000);
		List<Region> provinceList = regionService.getProvinceList(Integer.valueOf(-1));
		List<Region> cityList = regionService.getProvinceList(Integer.valueOf(selectProvinceCode));
		List<Region> areaList = regionService.getProvinceList(Integer.valueOf(selectCityCode));

		if (cityList.get(0).getCode().equals(areaList.get(0).getCode())) {
			areaList = new ArrayList<>();
		}

		String province = null;

		String city = null;

		String area = null;

		for (Region region : provinceList) {
			if (region.getCode().equals(selectProvinceCode)) {
				province = region.getName();
			}
		}

		for (Region region : cityList) {
			if (region.getCode().equals(selectProvinceCode)) {
				city = region.getName();
			}
		}

		for (Region region : areaList) {
			if (region.getCode().equals(selectProvinceCode)) {
				area = region.getName();
			}
		}

		System.out.println("province" + province);
		System.out.println("city" + city);
		System.out.println("area" + area);

	}
}
