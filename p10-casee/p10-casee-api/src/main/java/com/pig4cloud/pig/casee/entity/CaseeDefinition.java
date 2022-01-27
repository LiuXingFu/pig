package com.pig4cloud.pig.casee.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CaseeDefinition implements Serializable {
	private static final long serialVersionUID = 1L;


	private String taskKey;//流程定义key
	private String taskName;//任务办理人名字
	private String inspectorName;//督察员名字
	private Integer needAudit;//是否审核（0-提交不审核 1-提交计费审核）
	private Integer status;//审核状态（0-驳回 1-审核通过）


}
