package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.dto.TaskFlowDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifu.PaiFu_JGRZ_WLXJ_XTLR;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class PAIFU_JGRZ_WLXJ_XTLR_NODEHandler extends TaskNodeHandler {
	@Autowired
	TaskNodeService taskNodeService;

	//设置查询key列表
	private List<String> keys = new ArrayList<>();

	//初始方法
	@PostConstruct
	public void init() {
		keys.add("paiFu_JGRZ_SFYJ_YJJG");
		keys.add("paiFu_JGRZ_DXXJ_XJJG");
		keys.add("paiFu_JGRZ_PGDJ_XTLR");
	}

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {

		taskNodeService.YJ_HandlerTaskSubmit(PaiFu_JGRZ_WLXJ_XTLR.class, taskNode);

	}

	@Override
	public void handlerTaskAudit(TaskFlowDTO taskFlowDTO) {

		taskNodeService.YJ_HandlerTaskAudit(PaiFu_JGRZ_WLXJ_XTLR.class, taskFlowDTO, keys);

	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		taskNodeService.YJ_HandlerTaskMakeUp(PaiFu_JGRZ_WLXJ_XTLR.class, taskNode, keys);
	}

}
