package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.CustomerSubjectDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaiFu_STCC_BMKY_BMKY_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	SignUpLookLikeService signUpLookLikeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;
	@Autowired
	CustomerService customerService;


	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		//拍辅报名看样
		setPaiFuStccBmkyBmky(taskNode);

		//同步联合拍卖财产报名看样节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(taskNode, "paiFu_STCC_BMKY_BMKY");
	}

	private void setPaiFuStccBmkyBmky(TaskNode taskNode) {
		PaiFu_STCC_BMKY_BMKY paiFu_stcc_bmky_bmky = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_BMKY_BMKY.class);
		List<ReserveSeeSampleSeeSampleListDetail> reserveSeeSampleSeeSampleLists = paiFu_stcc_bmky_bmky.getReserveSeeSampleSeeSampleLists();

		List<CustomerSubjectDTO> customerSubjectDTOList=new ArrayList<>();
		for (ReserveSeeSampleSeeSampleListDetail reserveSeeSampleSeeSampleList : reserveSeeSampleSeeSampleLists) {
			Subject subject = new Subject();
			BeanUtils.copyProperties(reserveSeeSampleSeeSampleList,subject);

			SignUpLookLike signUpLookLike = new SignUpLookLike();
			BeanUtils.copyProperties(reserveSeeSampleSeeSampleList, signUpLookLike);

			//根据手机号添加或修改主体信息
			R<Subject> phoneBySaveOrUpdateById = remoteSubjectService.getPhoneAndUnifiedIdentityBySaveOrUpdateById(subject, SecurityConstants.FROM);
			Integer subjectId = Integer.valueOf(phoneBySaveOrUpdateById.getData().getSubjectId().toString());
			signUpLookLike.setSubjectId(subjectId);
			//添加报名看样信息
			signUpLookLikeService.save(signUpLookLike);

			reserveSeeSampleSeeSampleList.setSignUpLookLikeId(signUpLookLike.getSignUpLookLikeId());

			CustomerSubjectDTO customerSubjectDTO=new CustomerSubjectDTO();
			BeanUtils.copyProperties(reserveSeeSampleSeeSampleList,customerSubjectDTO);
			customerSubjectDTO.setProjectId(taskNode.getProjectId());
			customerSubjectDTO.setCaseeId(taskNode.getCaseeId());
			customerSubjectDTO.setCustomerType(20000);
			customerSubjectDTO.setNatureType(0);
			customerSubjectDTOList.add(customerSubjectDTO);
		}
		//添加客户信息
		customerService.saveCustomerBatch(customerSubjectDTOList);

		paiFu_stcc_bmky_bmky.setReserveSeeSampleSeeSampleLists(reserveSeeSampleSeeSampleLists);

		String json = JsonUtils.objectToJson(paiFu_stcc_bmky_bmky);

		taskNode.setFormData(json);

		taskNodeService.updateById(taskNode);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode, null);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));

		PaiFu_STCC_BMKY_BMKY paiFu_STCC_BMKY_BMKY = JsonUtils.jsonToPojo(node.getFormData(), PaiFu_STCC_BMKY_BMKY.class);

		if (paiFu_STCC_BMKY_BMKY!=null){
			List<Integer> collect = paiFu_STCC_BMKY_BMKY.getReserveSeeSampleSeeSampleLists().stream().map(ReserveSeeSampleSeeSampleListDetail::getSignUpLookLikeId).collect(Collectors.toList());

			signUpLookLikeService.removeByIds(collect);
		}

		//拍辅报名看样
		setPaiFuStccBmkyBmky(taskNode);

		//同步联合拍卖财产报名看样节点数据
		taskNodeService.synchronizeJointAuctionTaskNode( taskNode, "paiFu_STCC_BMKY_BMKY");
	}
}
