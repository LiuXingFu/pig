package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.CustomerSubjectDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.SamplePreparationWorkListDetail;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_KYZBGZ_KYZBGZ;
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
public class PaiFu_STCC_KYZBGZ_KYZBGZ_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	SamplePreparationWorkService samplePreparationWorkService;
	@Autowired
	SamplePreparationWorkSubjectReService samplePreparationWorkSubjectReService;
	@Autowired
	SamplePreparationWorkUsetReService samplePreparationWorkUsetReService;
	@Autowired
	SamplePreparationWorkAssetsReService samplePreparationWorkAssetsReService;
	@Autowired
	RemoteSubjectService remoteSubjectService;
	@Autowired
	CustomerService customerService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		//拍辅看样准备工作
		setPaiFuStccKyzbgzKyzbgz(taskNode);

		//同步联合拍卖财产看样准备工作节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(taskNode, "paiFu_STCC_KYZBGZ_KYZBGZ");
	}

	/**
	 * 新增并且同步联合拍卖看样准备工作
	 * @param taskNode
	 * @return
	 */
	private void setPaiFuStccKyzbgzKyzbgz(TaskNode taskNode) {
		PaiFu_STCC_KYZBGZ_KYZBGZ paiFu_stcc_kyzbgz_kyzbgz = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_KYZBGZ_KYZBGZ.class);

		List<SamplePreparationWorkListDetail> samplePreparationWorkListDetails = paiFu_stcc_kyzbgz_kyzbgz.getSamplePreparationWorkListDetails();
		List<SamplePreparationWorkAssetsRe> samplePreparationWorkAssetsReList = new ArrayList<>();
		List<SamplePreparationWorkUsetRe> samplePreparationWorkUsetReList = new ArrayList<>();
		List<SamplePreparationWorkSubjectRe> samplePreparationWorkSubjectReList = new ArrayList<>();
		for (SamplePreparationWorkListDetail samplePreparationWorkListDetail : samplePreparationWorkListDetails) {
			SamplePreparationWork samplePreparationWork = new SamplePreparationWork();
			BeanUtils.copyProperties(samplePreparationWorkListDetail, samplePreparationWork);
			samplePreparationWorkService.save(samplePreparationWork);

			samplePreparationWorkListDetail.setSamplePreparationWorkId(samplePreparationWork.getSamplePreparationWorkId());
			//预约看样财产信息
			List<Integer> assetsIdList = samplePreparationWorkListDetail.getAssetsIdList();
			for (Integer assetsId : assetsIdList) {
				SamplePreparationWorkAssetsRe samplePreparationWorkAssetsRe = new SamplePreparationWorkAssetsRe();
				samplePreparationWorkAssetsRe.setAssetsId(assetsId);
				samplePreparationWorkAssetsRe.setSamplePreparationWorkId(samplePreparationWork.getSamplePreparationWorkId());
				samplePreparationWorkAssetsReList.add(samplePreparationWorkAssetsRe);
			}

			//业务人员信息
			List<Integer> userIdList = samplePreparationWorkListDetail.getUserIdList();
			for (Integer integer : userIdList) {
				SamplePreparationWorkUsetRe samplePreparationWorkUsetRe = new SamplePreparationWorkUsetRe();
				samplePreparationWorkUsetRe.setUsetId(integer);
				samplePreparationWorkUsetRe.setSamplePreparationWorkId(samplePreparationWork.getSamplePreparationWorkId());
				samplePreparationWorkUsetReList.add(samplePreparationWorkUsetRe);
			}

			//预约看样人员信息
			List<Subject> subjectList = samplePreparationWorkListDetail.getSubjectList();
			for (Subject subject : subjectList) {
				R<Subject> saveOrUpdateById = remoteSubjectService.getPhoneAndUnifiedIdentityBySaveOrUpdateById(subject, SecurityConstants.FROM);
				Integer subjectId = Integer.valueOf(saveOrUpdateById.getData().getSubjectId().toString());

				SamplePreparationWorkSubjectRe samplePreparationWorkSubjectRe = new SamplePreparationWorkSubjectRe();
				samplePreparationWorkSubjectRe.setSubjectId(subjectId);
				samplePreparationWorkSubjectRe.setSamplePreparationWorkId(samplePreparationWork.getSamplePreparationWorkId());
				samplePreparationWorkSubjectReList.add(samplePreparationWorkSubjectRe);

				CustomerSubjectDTO customerSubjectDTO=new CustomerSubjectDTO();
				BeanUtils.copyProperties(subject,customerSubjectDTO);
				customerSubjectDTO.setProjectId(taskNode.getProjectId());
				customerSubjectDTO.setCaseeId(taskNode.getCaseeId());
				customerSubjectDTO.setCustomerType(20000);
				customerSubjectDTO.setNatureType(0);
				//添加客户信息
				customerService.saveCustomer(customerSubjectDTO);
			}
		}
		samplePreparationWorkSubjectReService.saveBatch(samplePreparationWorkSubjectReList);
		samplePreparationWorkUsetReService.saveBatch(samplePreparationWorkUsetReList);
		samplePreparationWorkAssetsReService.saveBatch(samplePreparationWorkAssetsReList);
		paiFu_stcc_kyzbgz_kyzbgz.setSamplePreparationWorkListDetails(samplePreparationWorkListDetails);

		String formData = JsonUtils.objectToJson(paiFu_stcc_kyzbgz_kyzbgz);
		taskNode.setFormData(formData);

		taskNodeService.updateById(taskNode);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {

		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));

		PaiFu_STCC_KYZBGZ_KYZBGZ paiFu_STCC_KYZBGZ_KYZBGZ = JsonUtils.jsonToPojo(node.getFormData(), PaiFu_STCC_KYZBGZ_KYZBGZ.class);

		if (paiFu_STCC_KYZBGZ_KYZBGZ!=null){
			List<Integer> collect = paiFu_STCC_KYZBGZ_KYZBGZ.getSamplePreparationWorkListDetails().stream().map(SamplePreparationWorkListDetail::getSamplePreparationWorkId).collect(Collectors.toList());

			this.samplePreparationWorkService.removeByIds(collect);

			this.samplePreparationWorkSubjectReService.remove(new LambdaQueryWrapper<SamplePreparationWorkSubjectRe>().in(SamplePreparationWorkSubjectRe::getSamplePreparationWorkId, collect));

			this.samplePreparationWorkUsetReService.remove(new LambdaQueryWrapper<SamplePreparationWorkUsetRe>().in(SamplePreparationWorkUsetRe::getSamplePreparationWorkId, collect));

			this.samplePreparationWorkAssetsReService.remove(new LambdaQueryWrapper<SamplePreparationWorkAssetsRe>().in(SamplePreparationWorkAssetsRe::getSamplePreparationWorkId, collect));
		}

		//拍辅看样准备工作
		setPaiFuStccKyzbgzKyzbgz(taskNode);

		//同步联合拍卖财产看样准备工作节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(taskNode, "paiFu_STCC_KYZBGZ_KYZBGZ");

	}
}
