package com.pig4cloud.pig.casee.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//测试工作流实体
@Data
public class PfzxTask implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;//办理人
	private String auditName;//审核人
	private Date beginDate;//开始时间
	private Date endDate;//结束时间
	private Integer isAudit;//是否审核（0-否 1-是）
	private Integer approveType;//审核（0-驳回 1-成功）
	private Integer status;//审核状态（111-驳回 403-成功）

}
