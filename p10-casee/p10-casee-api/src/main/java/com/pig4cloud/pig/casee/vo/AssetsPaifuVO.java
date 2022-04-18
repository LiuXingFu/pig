package com.pig4cloud.pig.casee.vo;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.liquientity.detail.AssetsReCaseeDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AssetsPaifuVO extends Assets {

	@ApiModelProperty(value = "地址信息")
	private Address address;

	@ApiModelProperty(value="财产关联详情")
	private String assetsReDetail;

	@ApiModelProperty(value="项目案件关联财产详情")
	private AssetsReCaseeDetail assetsReCaseeDetail;

	@ApiModelProperty(value="债务人id")
	private Integer subjectId;

	public void setAssetsReCaseeDetail(AssetsReCaseeDetail assetsReCaseeDetail) {
		this.assetsReCaseeDetail = assetsReCaseeDetail;
		this.setAssetsReDetail(JSON.toJSONString(assetsReCaseeDetail));
	}

	public void initAssetsReCaseeDetail(){
		assetsReCaseeDetail = JSON.parseObject(this.getAssetsReDetail(), AssetsReCaseeDetail.class );
	}

	public AssetsReCaseeDetail getAssetsReCaseeDetail() {
		if(assetsReCaseeDetail==null && this.getAssetsReDetail()!=null){
			assetsReCaseeDetail = JSON.parseObject(this.getAssetsReDetail(), AssetsReCaseeDetail.class );
		}
		return assetsReCaseeDetail;
	}

}
