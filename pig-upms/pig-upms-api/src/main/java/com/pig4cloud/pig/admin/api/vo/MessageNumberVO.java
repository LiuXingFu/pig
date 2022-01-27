package com.pig4cloud.pig.admin.api.vo;

import lombok.Data;

@Data
public class MessageNumberVO {
	//消息通知数
	private Integer messageNumber;
	//代办数
	private Integer auditTaskNumber;
	//委托数
	private Integer taskCommissionNumber;
}
