package com.pig4cloud.pig.casee.entity.paifuentity.detail;

import com.pig4cloud.pig.admin.api.entity.Subject;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 *  看样准备工作
 *
 * @author Mjh
 * @date 2021-10-19 16:03:22
 */
@Data
public class SamplePreparationWorkListDetail implements Serializable {
	/**预约看样时间*/
	private LocalDateTime reserveSeeSampleTime;
	/**钥匙联系人*/
	private String keyContact;
	/**联系电话*/
	private String phone;
	/**钥匙位置*/
	private String keyPosition;
	/**备注*/
	private String remark;
	/**附件*/
	private String fileUrl;
	/**带看人*/
	private List<Integer> userIdList;
	/**所有带看人名称*/
	private String userNames;
	/**预约看样人员信息*/
	private List<Subject> subjectList;
	/**预约看样财产信息*/
	private List<Integer> assetsIdList;
	/**所有看样财产名称*/
	private String assetsNames;
	/**看样准备工作表id*/
	private Integer samplePreparationWorkId;
}
