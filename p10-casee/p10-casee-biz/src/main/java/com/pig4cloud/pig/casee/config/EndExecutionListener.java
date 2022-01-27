package com.pig4cloud.pig.casee.config;


import com.pig4cloud.pig.casee.utils.MessageRecordUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class EndExecutionListener implements ExecutionListener {


	@Override
	public void notify(DelegateExecution delegateExecution) {
		MessageRecordUtils.pushTaskMessageRecord(delegateExecution," 发送了审核任务委托：");
		System.out.println("审核任务委托");
	}
}
