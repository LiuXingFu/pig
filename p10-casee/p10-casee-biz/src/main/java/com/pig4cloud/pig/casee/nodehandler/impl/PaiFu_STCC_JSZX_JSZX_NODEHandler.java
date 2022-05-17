package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.CustomerSubjectDTO;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
	@Autowired
	CustomerService customerService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		//拍辅接受咨询
		PaiFu_STCC_JSZX_JSZX paiFu_stcc_jszx_jszx = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_JSZX_JSZX.class);
		List<ReserveSeeSampleConsultingListDetail> reserveSeeSampleConsultingLists = paiFu_stcc_jszx_jszx.getReserveSeeSampleConsultingLists();

		setReserveSeeSampleConsultingListDetailList(reserveSeeSampleConsultingLists,taskNode);

		paiFu_stcc_jszx_jszx.setReserveSeeSampleConsultingLists(reserveSeeSampleConsultingLists);

		String json = JsonUtils.objectToJson(paiFu_stcc_jszx_jszx);

		taskNode.setFormData(json);

		// 4.保存任务数据
		this.taskNodeService.updateById(taskNode);

		//同步联合拍卖财产接受咨询节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(taskNode, "paiFu_STCC_JSZX_JSZX");

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode, null);
	}

	/**
	 * 循环添加咨询名单信息与咨询问题信息
	 * @param reserveSeeSampleConsultingLists
	 */
	private void setReserveSeeSampleConsultingListDetailList(List<ReserveSeeSampleConsultingListDetail> reserveSeeSampleConsultingLists,TaskNode taskNode) {
		for (ReserveSeeSampleConsultingListDetail reserveSeeSampleConsulting : reserveSeeSampleConsultingLists) {
			Subject subject = new Subject();
			ReceiveConsultation receiveConsultation = new ReceiveConsultation();
			BeanUtils.copyProperties(reserveSeeSampleConsulting,subject);

			//根据手机号添加或修改主体信息
			R<Subject> phoneBySaveOrUpdateById = remoteSubjectService.getPhoneAndUnifiedIdentityBySaveOrUpdateById(subject, SecurityConstants.FROM);
			Integer subjectId = Integer.valueOf(phoneBySaveOrUpdateById.getData().getSubjectId().toString());

			receiveConsultation.setUnifiedIdentity(reserveSeeSampleConsulting.getUnifiedIdentity());
			receiveConsultation.setSubjectId(subjectId);
			receiveConsultation.setName(reserveSeeSampleConsulting.getName());
			receiveConsultation.setPhone(reserveSeeSampleConsulting.getPhone());
			receiveConsultation.setRemark(reserveSeeSampleConsulting.getRemark());
			receiveConsultationService.save(receiveConsultation);//添加咨询名单信息

			reserveSeeSampleConsulting.setReceiveConsultationId(receiveConsultation.getReceiveConsultationId());

			List<String> askQuestions = reserveSeeSampleConsulting.getAskQuestions();
			for (String askQuestion : askQuestions) {
				ReceiveConsultationQuestionRe receiveConsultationQuestionRe = new ReceiveConsultationQuestionRe();
				receiveConsultationQuestionRe.setReceiveConsultationId(receiveConsultation.getReceiveConsultationId());
				receiveConsultationQuestionRe.setAskQuestions(askQuestion);
				receiveConsultationQuestionReService.save(receiveConsultationQuestionRe);//添加咨询问题信息
			}

			CustomerSubjectDTO customerSubjectDTO=new CustomerSubjectDTO();
			BeanUtils.copyProperties(reserveSeeSampleConsulting,customerSubjectDTO);
			customerSubjectDTO.setProjectId(taskNode.getProjectId());
			customerSubjectDTO.setCaseeId(taskNode.getCaseeId());
			customerSubjectDTO.setCustomerType(10000);
			customerSubjectDTO.setNatureType(0);
			//添加客户信息
			customerService.saveCustomer(customerSubjectDTO);
		}
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {

		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));

		PaiFu_STCC_JSZX_JSZX paiFu_STCC_JSZX_JSZX = JsonUtils.jsonToPojo(node.getFormData(), PaiFu_STCC_JSZX_JSZX.class);
		if (paiFu_STCC_JSZX_JSZX!=null){
			//		咨询名单id集合
			List<Integer> collect = paiFu_STCC_JSZX_JSZX.getReserveSeeSampleConsultingLists().stream().map(ReserveSeeSampleConsultingListDetail::getReceiveConsultationId).collect(Collectors.toList());

			//删除接收咨询数据
			receiveConsultationService.removeByIds(collect);

			//删除接受咨询关联咨询问题数据
			receiveConsultationQuestionReService.remove(new LambdaQueryWrapper<ReceiveConsultationQuestionRe>().in(ReceiveConsultationQuestionRe::getReceiveConsultationId, collect));
		}

		//拍辅接受咨询
		PaiFu_STCC_JSZX_JSZX paiFu_stcc_jszx_jszx = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_JSZX_JSZX.class);

		List<ReserveSeeSampleConsultingListDetail> reserveSeeSampleConsultingLists = paiFu_stcc_jszx_jszx.getReserveSeeSampleConsultingLists();

		setReserveSeeSampleConsultingListDetailList(reserveSeeSampleConsultingLists,taskNode);

		paiFu_stcc_jszx_jszx.setReserveSeeSampleConsultingLists(reserveSeeSampleConsultingLists);

		String json = JsonUtils.objectToJson(paiFu_stcc_jszx_jszx);

		taskNode.setFormData(json);

		taskNodeService.updateById(taskNode);

		//同步联合拍卖财产接受咨询节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(taskNode, "paiFu_STCC_JSZX_JSZX");

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode, null);
	}
}
