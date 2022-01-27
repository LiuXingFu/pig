package com.pig4cloud.pig.admin.api.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 法院表
 *
 * @author Mjh
 * @date 2021-09-02 16:24:58
 */
@Data
@TableName("p10_court")
@ApiModel(value = "网点表")
public class Court implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 法院id 主键自增
	 */
	@TableId(value = "court_id")
	@ApiModelProperty(value = "法院id 主键自增")
	private Integer courtId;

	/**
	 * 法院名称
	 */
	@ApiModelProperty(value = "法院名称")
	private String courtName;

	/**
	 * 地区id
	 */
	@ApiModelProperty(value = "地区id")
	private String regionCode;

	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = "详细地址")
	private String address;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String descr;

	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	private String delFlag;
}
