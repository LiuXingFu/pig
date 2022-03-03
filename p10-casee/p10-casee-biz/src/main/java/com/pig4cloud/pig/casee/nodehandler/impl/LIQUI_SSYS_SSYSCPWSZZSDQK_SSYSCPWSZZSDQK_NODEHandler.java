package com.pig4cloud.pig.casee.nodehandler.impl;

import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.pig4cloud.pig.casee.dto.SaveCaseeLiQuiDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS.LiQui_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.ReceiptRecord;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.CaseeLiquiService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.nodehandler.impl
 * @ClassNAME: LIQUI_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK_NODEHandler
 * @Author: yd
 * @DATE: 2022/3/3
 * @TIME: 15:20
 * @DAY_NAME_SHORT: 周四
 */
@Component
public class LIQUI_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Autowired
	CaseeLiquiService caseeLiquiService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);

		if (taskNode.getNeedAudit() == 0) {
			LiQui_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK liQui_ssys_ssyscpwszzsdqk_ssyscpwszzsdqk = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK.class);

			List<ReceiptRecord> receiptRecordList = liQui_ssys_ssyscpwszzsdqk_ssyscpwszzsdqk.getReceiptRecordList();

			Date date = new Date();

			for (int i = 0; i < receiptRecordList.size(); i++) {
				if(i == 0){
					date = receiptRecordList.get(i).getFinalReceiptTime();
				} else {
					if (receiptRecordList.get(i).getFinalReceiptTime().compareTo(date) > 0){
						date = receiptRecordList.get(i).getFinalReceiptTime();
					}
				}
			}

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

			HashMap<String, Object> map = new HashMap<>();

			map.put("finalReceiptTime", simpleDateFormat.format(date));

			String jsonObject = JsonUtils.objectToJsonObject(map);

			SaveCaseeLiQuiDTO saveCaseeLiQuiDTO = new SaveCaseeLiQuiDTO();

			saveCaseeLiQuiDTO.setKey("$.finalReceiptTime");

			saveCaseeLiQuiDTO.setFormData(jsonObject);

			saveCaseeLiQuiDTO.setCaseeId(taskNode.getCaseeId());

			this.caseeLiquiService.updateCaseeDetail(saveCaseeLiQuiDTO);
		}

	}
}
