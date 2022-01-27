package com.pig4cloud.pig.admin.api.entity;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.service.CourtService;
import com.pig4cloud.pig.admin.service.RegionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CourtTest {

	@Autowired
	private CourtService courtService;

	@Autowired
	private RegionService regionService;

	@Test
	public void testImport() {
		Page<Court> page = new Page<Court>();
		page.setCurrent(1);
		page.setSize(100);
		QueryWrapper<Court> courtQUery=new QueryWrapper<>();
		courtQUery.isNull("region_code");

		int times = 0;
		do {

			page = courtService.page(page, courtQUery);

			for (Court court : page.getRecords()) {
				String courName = court.getCourtName();
				courName = courName.replace("高级人民法院", "");
				courName = courName.replace("中级人民法院 ", "");
				courName = courName.replace("人民法院", "");
				courName = courName.replace("法院", "");
				String lastName = courName.substring(courName.length() - 1, courName.length());
				courName = courName.substring(0, courName.length() - 1);
				if (lastName .equals("区") ) {
					courName = courName.substring(courName.lastIndexOf("市"), courName.length());
				}
				if (lastName .equals("县") ) {
					courName = courName.substring(courName.lastIndexOf("市"), courName.length());
				}
				if (lastName .equals("市") ) {
					courName = courName.substring(courName.lastIndexOf("省"), courName.length());
				}

				QueryWrapper<Region> regionQUery = new QueryWrapper<>();
				regionQUery.like("name", "%" + courName + "%");
				List<Region> regionList = regionService.list(regionQUery);
				if (regionList.size() > 0) {
					court.setRegionCode(regionList.get(0).getCode());
				}
			}

			courtService.updateBatchById(page.getRecords());
			times++;
		}while(times<20 && page.getTotal() >= 100);


	}


}

