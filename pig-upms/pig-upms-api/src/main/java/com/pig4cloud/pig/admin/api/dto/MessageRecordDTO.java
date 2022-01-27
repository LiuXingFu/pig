package com.pig4cloud.pig.admin.api.dto;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class MessageRecordDTO  {
	/**
	 * 创建者
	 */
	@TableField(fill = FieldFill.INSERT)
	private Integer createBy;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 消息类型(0-普通消息 100-任务消息 200-拍辅消息 300-清收消息 400-合作消息)
	 */
	private Integer messageType;
	/**
	 * 消息标题
	 */
	private String messageTitle;
	/**
	 * 消息内容
	 */
	private String messageContent;
	/**
	 * 接收人id
	 */
	private Integer receiverUserId;
	/**
	 * 接收人机构id
	 */
	private Integer receiverInsId;
	/**
	 * 接收人网点id
	 */
	private Integer receiverOutlesId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 目标参数
	 */
	private String targetValue;
	/**
	 * 发送人
	 */
	@ApiModelProperty(value="发送人")
	private String senderUserName;
	/**
	 * 状态 未读-0 已读-200
	 */
	@ApiModelProperty(value="状态 未读-0 已读-200")
	private Integer readFlag;

	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd HH:mm:ss")

	private LocalDateTime beginDate;//开始时间

	@JsonFormat(timezone = "GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDate;//结束时间


	//分页对象
	private Page page;
}

