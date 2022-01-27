package com.pig4cloud.pig.casee.service;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoJobHandler {

	@XxlJob("demoJobHandler")
	public ReturnT<String> demoJobHandler(String s){
		return ReturnT.SUCCESS;
	}

//	public void demoJobHandler() {
//		String param = XxlJobHelper.getJobParam(); // 获取参数
//		int shardIndex = XxlJobHelper.getShardIndex();// 获取分片参数
//		XxlJobHelper.handleSuccess(); // v2.3.0 设置任务结果
//	}
}
