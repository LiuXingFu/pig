package com.pig4cloud.pig.casee.vo.paifu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeMessageRecordVO {

	/**
	 * 节点 ID
	 */
	@TableId(value="node_id",type = IdType.INPUT)
	@ApiModelProperty(value="节点 ID")
	private String nodeId;

	/**
	 * 节点key
	 */
	@ApiModelProperty(value="节点key")
	private String nodeKey;

	/**
	 * 节点名称
	 */
	@ApiModelProperty(value="节点名称")
	private String nodeName;

	/**
	 * 表单ID
	 */
	@ApiModelProperty(value="表单ID")
	private Integer formId;

	/**
	 * 机构id
	 */
	@ApiModelProperty(value="机构id")
	private Integer insId;

	/**
	 * 网点id
	 */
	@ApiModelProperty(value="网点id")
	private Integer outlesId;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;


	/**
	 * 程序id
	 */
	@ApiModelProperty(value="程序id")
	private Integer targetId;

	/**
	 * 财产id
	 */
	@ApiModelProperty(value = "财产id")
	private Integer assetsId;

	/**
	 * 财产性质
	 */
	@ApiModelProperty(value = "财产性质")
	private Integer assetsType;

	/**
	 * 节点状态 节点状态 0-未提交 101-待审核 111-驳回 301-跳过 401-提交不审核 403-审核通过 500-可跳过 600-已委托
	 */
	@ApiModelProperty(value="节点状态 节点状态 0-未提交 101-待审核 111-驳回 301-跳过 401-提交不审核 403-审核通过 500-可跳过 600-已委托")
	private Integer status;

	/**
	 * 表单填充数据
	 */
	@ApiModelProperty(value="表单填充数据")
	private String formData;

}
