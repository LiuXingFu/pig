package com.pig4cloud.pig.casee.entity.assets;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.assets.detail.AssetsReCaseeDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 案件财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
public class AssetsReCasee extends AssetsRe {

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
}
