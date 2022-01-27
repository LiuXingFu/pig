package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSQT.LiQui_SSQT_SSQTCPJG_SSQTCPJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.stereotype.Component;

@Component
public class LIQUI_SSQT_SSQTCPJG_SSQTCPJG_NODEHandler extends TaskNodeHandler {

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		LiQui_SSQT_SSQTCPJG_SSQTCPJG liQui_ssqt_ssqtcpjg_ssqtcpjg = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSQT_SSQTCPJG_SSQTCPJG.class);

		//更改立案录入的诉讼费
	}
}
