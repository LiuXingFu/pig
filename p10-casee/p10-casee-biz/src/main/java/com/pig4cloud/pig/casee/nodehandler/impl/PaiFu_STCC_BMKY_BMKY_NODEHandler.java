package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.entity.SignUpLookLike;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ReserveSeeSampleSeeSampleListDetail;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_BMKY_BMKY;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class PaiFu_STCC_BMKY_BMKY_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	SignUpLookLikeService signUpLookLikeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;


	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍辅报名看样
		PaiFu_STCC_BMKY_BMKY paiFu_stcc_bmky_bmky = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_BMKY_BMKY.class);
		List<ReserveSeeSampleSeeSampleListDetail> reserveSeeSampleSeeSampleLists = paiFu_stcc_bmky_bmky.getReserveSeeSampleSeeSampleLists();

		//同步联合拍卖财产报名看样节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_bmky_bmky.getAssetsId(),taskNode,"paiFu_STCC_BMKY_BMKY");

		for (ReserveSeeSampleSeeSampleListDetail reserveSeeSampleSeeSampleList : reserveSeeSampleSeeSampleLists) {
			Subject subject = new Subject();
			SignUpLookLike signUpLookLike=new SignUpLookLike();
			BeanUtils.copyProperties(reserveSeeSampleSeeSampleList,signUpLookLike);

			subject.setName(reserveSeeSampleSeeSampleList.getName());
			subject.setPhone(reserveSeeSampleSeeSampleList.getPhone());
			if (reserveSeeSampleSeeSampleList.getIdentityCard() != null) {
				subject.setUnifiedIdentity(reserveSeeSampleSeeSampleList.getIdentityCard());
				signUpLookLike.setIdentityCard(reserveSeeSampleSeeSampleList.getIdentityCard());
			}
			//根据手机号添加或修改主体信息
			R<Subject> phoneBySaveOrUpdateById = remoteSubjectService.getPhoneBySaveOrUpdateById(subject, SecurityConstants.FROM);
			Integer subjectId = Integer.valueOf(phoneBySaveOrUpdateById.getData().getSubjectId().toString());
			signUpLookLike.setSubjectId(subjectId);
			//添加报名看样信息
			signUpLookLikeService.save(signUpLookLike);
		}
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		super.handlerTaskMakeUp(taskNode);
	}
}
