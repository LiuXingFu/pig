package com.pig4cloud.pig.casee.entity.liquientity;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.ProjectUrge;
import com.pig4cloud.pig.casee.entity.liquientity.detail.CaseeLiquiDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.ProjectUrgeLiquiDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectUrgeLiqui extends ProjectUrge {

	@ApiModelProperty(value="项目督促详情表")
	private ProjectUrgeLiquiDetail projectUrgeLiquiDetail;

	public void setProjectUrgeLiquiDetail(ProjectUrgeLiquiDetail projectUrgeLiquiDetail) {
		this.projectUrgeLiquiDetail = projectUrgeLiquiDetail;
		this.setUrgeDetail(JSON.toJSONString(projectUrgeLiquiDetail));
	}

	public void initProjectUrgeLiquiDetail(){
		projectUrgeLiquiDetail = JSON.parseObject(this.getUrgeDetail(), ProjectUrgeLiquiDetail.class );
	}

	public ProjectUrgeLiquiDetail getProjectUrgeLiquiDetail() {
		if(projectUrgeLiquiDetail==null && this.getUrgeDetail()!=null){
			projectUrgeLiquiDetail = JSON.parseObject(this.getUrgeDetail(), ProjectUrgeLiquiDetail.class );
		}
		return projectUrgeLiquiDetail;
	}
}
