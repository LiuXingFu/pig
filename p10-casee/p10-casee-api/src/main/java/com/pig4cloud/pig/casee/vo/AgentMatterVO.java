package com.pig4cloud.pig.casee.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pig4cloud.pig.casee.entity.TaskNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 代办事项
 *
 * @author xiaojun
 * @date 2021-09-07 17:01:38
 */
@Data
public class AgentMatterVO extends TaskNode{
	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 标的名称
	 */
	@ApiModelProperty(value="标的名称")
	private String targetName;

	/**
	 * 任务类型
	 */
	@ApiModelProperty(value="任务类型")
	private Integer taskType;

	/**
	 * 案件类型(0-清收,1-拍辅,2-财产核查)
	 */
	@ApiModelProperty(value="案件类型(100-清收,200-拍辅,300-财产核查)")
	private Integer caseeType;
	/**
	 * 状态确认(0-默认状态，1-待确认,2-已确认，3-已拒绝)
	 */
	@ApiModelProperty(value="状态确认(0-默认状态，1-待确认,2-已确认，3-已拒绝)")
	private Integer targetStatusConfirm;

	/**
	 * 任务记录状态(0-未提交 101-待审核 111-审核不通过  403-审核通过 600-已委托)
	 */
	@ApiModelProperty(value="任务记录状态(0-未提交 101-待审核 111-审核不通过  403-审核通过 600-已委托")
	private Integer taskRecordStatus;

	/**
	 * 任务记录委托状态(0-委托中 1-拒绝)
	 */
	@ApiModelProperty(value="任务记录委托状态(0-委托中 1-拒绝)")
	private Integer recordTrusteeStatus;

	/**
	 * 委托类型 0-办理任务 1-审核任务
	 */
	@ApiModelProperty(value="委托类型 0-办理任务 1-审核任务")
	private Integer recordTrusteeType;

	/**
	 * 任务创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime taskRecordCommitTime;

	/**
	 * 任务更新时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime taskRecordCreateTime;

	/**
	 * 任务审核时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime taskRecordUpdateTime;

	/**
	 * 委托人
	 */
	@ApiModelProperty(value="委托人")
	private String commissionNickName;

	/**
	 * 委托网点
	 */
	@ApiModelProperty(value="委托网点")
	private String commissionOutlesName;

	/**
	 * 委托机构
	 */
	@ApiModelProperty(value="委托机构")
	private String commissionInsName;

	/**
	 * 受托人(提交人)
	 */
	@ApiModelProperty(value="受托人(提交人)")
	private String entrustedNickName;

	/**
	 * 受托网点(提交网点)
	 */
	@ApiModelProperty(value="受托网点(提交网点)")
	private String entrustedOutlesName;

	/**
	 * 受托机构(提交机构)
	 */
	@ApiModelProperty(value="受托机构(提交机构)")
	private String entrustedInsName;

	/**
	 * 审核人(审核人)
	 */
	@ApiModelProperty(value="审核人")
	private String auditorNickName;

	/**
	 * 审核网点
	 */
	@ApiModelProperty(value="审核网点")
	private String auditorOutlesName;

	/**
	 * 审核机构
	 */
	@ApiModelProperty(value="审核机构")
	private String auditorInsName;

	@ApiModelProperty(value="'业务类型（100-清收标的，200-拍辅标的物）")
	private Integer businessType;
	/**
	 * 描述
	 */
	@ApiModelProperty(value="描述")
	private String described;

	@ApiModelProperty(value="'标的性质（清收标的：(10101:金钱，10102：其它)，清收标的物：（10201：房产，10202：车辆，10203：股权，10204：土地，10205：其它））")
	private Integer targetType;

}
