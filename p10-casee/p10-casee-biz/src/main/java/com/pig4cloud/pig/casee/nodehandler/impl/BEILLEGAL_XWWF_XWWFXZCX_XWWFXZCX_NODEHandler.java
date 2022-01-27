package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.beillegalprocedure.BeIllegal_XWWF_XWWFXZCX_XWWFXZCX;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.stereotype.Component;

@Component
public class BEILLEGAL_XWWF_XWWFXZCX_XWWFXZCX_NODEHandler extends TaskNodeHandler {

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		BeIllegal_XWWF_XWWFXZCX_XWWFXZCX beIllegal_xwwf_xwwfxzcx_xwwfxzcx = JsonUtils.jsonToPojo(taskNode.getFormData(), BeIllegal_XWWF_XWWFXZCX_XWWFXZCX.class);
	}
}
