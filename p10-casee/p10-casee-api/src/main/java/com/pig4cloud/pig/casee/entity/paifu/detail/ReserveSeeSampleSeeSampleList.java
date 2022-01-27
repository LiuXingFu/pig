package com.pig4cloud.pig.casee.entity.paifu.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约看样
 *  报名看样名单
 *
 * @author Mjh
 * @date 2021-10-19 16:03:22
 */
@Data
public class ReserveSeeSampleSeeSampleList implements Serializable {
	/**姓名*/
    private String name;
	/**联系电话*/
    private String phone;
	/**身份证*/
    private String identityCard;
	/**报名时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date registrationTime;
}
