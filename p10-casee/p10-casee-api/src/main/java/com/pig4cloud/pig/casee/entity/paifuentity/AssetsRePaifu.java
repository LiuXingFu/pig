package com.pig4cloud.pig.casee.entity.paifuentity;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.liquientity.detail.AssetsReCaseeDetail;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.AssetsRePaifuDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 案件财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
public class AssetsRePaifu extends AssetsRe {

	@ApiModelProperty(value="拍辅项目案件关联财产详情")
	private AssetsRePaifuDetail assetsRePaifuDetail;

	public void setAssetsRePaifuDetail(AssetsRePaifuDetail assetsRePaifuDetail) {
		this.assetsRePaifuDetail = assetsRePaifuDetail;
		this.setAssetsReDetail(JSON.toJSONString(assetsRePaifuDetail));
	}

	public void initAssetsRePaifuDetail(){
		assetsRePaifuDetail = JSON.parseObject(this.getAssetsReDetail(), AssetsRePaifuDetail.class );
	}

	public AssetsRePaifuDetail getAssetsRePaifuDetail() {
		if(assetsRePaifuDetail==null && this.getAssetsReDetail()!=null){
			assetsRePaifuDetail = JSON.parseObject(this.getAssetsReDetail(), AssetsRePaifuDetail.class );
		}
		return assetsRePaifuDetail;
	}
}
