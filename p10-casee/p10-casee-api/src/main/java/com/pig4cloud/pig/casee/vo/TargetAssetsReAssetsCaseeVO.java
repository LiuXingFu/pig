package com.pig4cloud.pig.casee.vo;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.casee.entity.assets.detail.AssetsReCaseeDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TargetAssetsReAssetsCaseeVO {

	//财产关联id
	private Integer assetsReId;

	//关联财产详情
	private String assetsReDetail;

	@ApiModelProperty(value="项目案件关联财产详情")
	private AssetsReCaseeDetail assetsReCaseeDetail;

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

	//财产id
	private Integer assetsId;

	//财产名称
	private String assetsName;

	//所有权人
	private String owner;

	//财产类型
	private Integer type;

	//财产性质
	private Integer assetsType;

	//财产编号
	private String accountNumber;

	//案件id
	private Integer caseeId;

	//案件状态
	private Integer status;

}
