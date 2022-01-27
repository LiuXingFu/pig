package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS.LiQui_SSYS_SSYSCPJG_SSYSCPJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.stereotype.Component;

@Component
public class LIQUI_SSYS_SSYSCPJG_SSYSCPJG_NODEHandler extends TaskNodeHandler {



	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		LiQui_SSYS_SSYSCPJG_SSYSCPJG liQui_SSYS_SSYSCPJG_SSYSCPJG = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSYS_SSYSCPJG_SSYSCPJG.class);

		//更改立案录入的诉讼费


	}
}
