package com.pig4cloud.pig.casee.dto;

import com.pig4cloud.pig.casee.entity.Target;
import lombok.Data;

@Data
public class AuditTargetDTO extends Target {

//	//第三层父节点key
//	private String threeParentNodeKey;
//
//	//第二层父节点key
//	private String twoParentNodeKey;
//
//	//第一层父节点key
//	private String oneParentNodeKey;

	//层级节点key拼接key
	private String key;

	//添加数据
	private String formData;

	//任务最终进度
	private Integer finalProcess;

	//当前子节点进度
	private Integer process;
}
