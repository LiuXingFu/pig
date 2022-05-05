package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.entity.ReceiveConsultation;
import com.pig4cloud.pig.casee.entity.ReceiveConsultationQuestionRe;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ReserveSeeSampleConsultingListDetail;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_JSZX_JSZX;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PaiFu_STCC_JSZX_JSZX_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	ReceiveConsultationService receiveConsultationService;
	@Autowired
	ReceiveConsultationQuestionReService receiveConsultationQuestionReService;
	@Autowired
	RemoteSubjectService remoteSubjectService;
	@Autowired
	TargetService targetService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍辅接受咨询
		PaiFu_STCC_JSZX_JSZX paiFu_stcc_jszx_jszx = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_JSZX_JSZX.class);
		List<ReserveSeeSampleConsultingListDetail> reserveSeeSampleConsultingLists = paiFu_stcc_jszx_jszx.getReserveSeeSampleConsultingLists();

		//同步联合拍卖财产接受咨询节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_jszx_jszx.getAssetsId(),taskNode,"paiFu_STCC_JSZX_JSZX");
		for (ReserveSeeSampleConsultingListDetail reserveSeeSampleConsulting : reserveSeeSampleConsultingLists) {
			Subject subject = new Subject();
			ReceiveConsultation receiveConsultation=new ReceiveConsultation();

			subject.setName(reserveSeeSampleConsulting.getName());
			subject.setPhone(reserveSeeSampleConsulting.getPhone());
			if (reserveSeeSampleConsulting.getIdentityCard() != null) {
				subject.setUnifiedIdentity(reserveSeeSampleConsulting.getIdentityCard());
				receiveConsultation.setIdentityCard(reserveSeeSampleConsulting.getIdentityCard());
			}
			//根据手机号添加或修改主体信息
			R<Subject> phoneBySaveOrUpdateById = remoteSubjectService.getPhoneBySaveOrUpdateById(subject, SecurityConstants.FROM);
			Integer subjectId = Integer.valueOf(phoneBySaveOrUpdateById.getData().getSubjectId().toString());

			receiveConsultation.setSubjectId(subjectId);
			receiveConsultation.setName(reserveSeeSampleConsulting.getName());
			receiveConsultation.setPhone(reserveSeeSampleConsulting.getPhone());
			receiveConsultation.setRemark(reserveSeeSampleConsulting.getRemark());
			receiveConsultationService.save(receiveConsultation);//添加咨询名单信息

			List<String> askQuestions = reserveSeeSampleConsulting.getAskQuestions();
			for (String askQuestion : askQuestions) {
				ReceiveConsultationQuestionRe receiveConsultationQuestionRe=new ReceiveConsultationQuestionRe();
				receiveConsultationQuestionRe.setReceiveConsultationId(receiveConsultation.getReceiveConsultationId());
				receiveConsultationQuestionRe.setAskQuestions(askQuestion);
				receiveConsultationQuestionReService.save(receiveConsultationQuestionRe);//添加咨询问题信息
			}
		}
	}
}
