package com.pig4cloud.pig.casee.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 任务记录表
 *
 * @author Mjh
 * @date 2021-11-05 16:28:49
 */
@Data
@TableName("p10_task_record")
@ApiModel(value = "任务记录表")
public class TaskRecord {

	@TableId
	@ApiModelProperty(value="任务记录id")
	private Integer taskRecordId;
	@ApiModelProperty(value="节点id")
	private String nodeId;
	@ApiModelProperty(value="标的id")
	private Integer targetId;
	@ApiModelProperty(value="案件id")
	private Integer caseeId;
	@ApiModelProperty(value="业务委托 0-未委托 1-委托")
	private Integer canEntrust;
	@ApiModelProperty(value="委托状态(0-委托中 1-拒绝)")
	private Integer trusteeStatus;
	@ApiModelProperty(value="委托类型 0-办理任务 1-审核任务")
	private Integer trusteeType;
	@ApiModelProperty(value="提交机构id")
	private Integer submitInsId;
	@ApiModelProperty(value="提交网点id")
	private Integer submitOutlesId;
	@ApiModelProperty(value="提交人id")
	private Integer submitId;
	@ApiModelProperty(value="委托机构id")
	private Integer trusteeInsId;
	@ApiModelProperty(value="委托网点id")
	private Integer trusteeOutlesId;
	@ApiModelProperty(value="委托人id")
	private Integer trusteeId;
	@ApiModelProperty(value="任务状态 0-未提交 101-待审核 111-审核不通过  403-审核通过 600-已委托")
	private Integer status;
	@ApiModelProperty(value="描述")
	private String described;
	@ApiModelProperty(value="审核人机构id")
	private Integer auditorInsId;
	@ApiModelProperty(value="审核人网点id")
	private Integer auditorOutlesId;
	@ApiModelProperty(value="审核人id")
	private Integer auditorId;
	@ApiModelProperty(value="处理人id")
	private Integer handlerId;

	/**
	 * 创建者
	 */
	@TableField(fill = FieldFill.INSERT)
	private Integer createBy;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新者
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Integer updateBy;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime updateTime;


}
