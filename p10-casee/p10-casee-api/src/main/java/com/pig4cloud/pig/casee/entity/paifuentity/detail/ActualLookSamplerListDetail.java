package com.pig4cloud.pig.casee.entity.paifuentity.detail;


import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 引领看样
 *
 * @author Mjh
 * @date 2021-10-19 16:03:22
 */
@Data
public class ActualLookSamplerListDetail implements Serializable {
	/**看样人员名单*/
	List<ListOfSamplers> subjectList=new ArrayList<>();
	/**看样准备工作表id*/
	private Integer samplePreparationWorkId;
	/**带看人*/
	private List<Integer> userIdList;
	/**附件地址*/
	private String fileUrl;
	/**图片地址*/
	private String imageUrl;
}
