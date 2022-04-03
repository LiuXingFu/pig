package com.pig4cloud.pig.casee.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.Target;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.assets.AssetsReLiqui;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.*;
import com.pig4cloud.pig.casee.service.AssetsReLiquiService;
import com.pig4cloud.pig.casee.service.CaseeLiquiService;
import com.pig4cloud.pig.casee.service.TargetService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.casee.vo.TaskNodeVO;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class FindNodeTemplateChildrenUtils {

	/**
	 * 遍历节点数据拿到父级节点保存到voList中
	 *
	 * @param list
	 * @return
	 */
	public static void getGrandParentChildren(List<TaskNodeVO> list, List<TaskNodeVO> voList, String parentId, Integer procedureNature) {
		for (TaskNodeVO taskNodeVO : list) {
			if (StringUtils.isBlank(parentId)) {
				if (StringUtils.isBlank(taskNodeVO.getParentNodeId())) {
					voList.add(taskNodeVO);
				}
			}
		}
		getGrandChildrenAssets(list, voList);

//		if (procedureNature.equals(4040)||procedureNature.equals(4041)){//程序类型
//			getGrandChildrenAssets(list, voList);
//		}else {
//			getGrandChildren(list, voList);
//		}
	}

	/**
	 * 递归将子节点存储在父节点的children集合中
	 *
	 * @param list
	 * @return
	 */
	private static void getGrandChildren(List<TaskNodeVO> list, List<TaskNodeVO> voList) {
		for (int i = 0; i < voList.size(); i++) {
			TaskNodeVO taskManagementVO = voList.get(i);
			for (int j = 0; j < list.size(); j++) {
				TaskNodeVO managementVO = list.get(j);
				if (managementVO.getParentNodeId() != null && managementVO.getParentNodeId().equals(taskManagementVO.getNodeId())) {
					taskManagementVO.getChildren().add(managementVO);
				}
			}
			getGrandChildren(list, taskManagementVO.getChildren());
		}
	}

	/**
	 * 递归财产程序将子节点存储在父节点的children集合中
	 *
	 * @param list
	 * @return
	 */
	private static void getGrandChildrenAssets(List<TaskNodeVO> list, List<TaskNodeVO> voList) {
		for (int i = 0; i < voList.size(); i++) {
			TaskNodeVO taskManagementVO = voList.get(i);
			TargetService targetService = SpringUtils.getObject(TargetService.class);
			//查询财产程序信息
			Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getTargetId, taskManagementVO.getTargetId()).eq(Target::getGoalType, 20001));
			for (int j = 0; j < list.size(); j++) {
				TaskNodeVO managementVO = new TaskNodeVO();
				if (list.get(j).getNodeAttributes() == 400) {//如果当前节点是认为节点则判断改节点是否显示
					managementVO = (nodeJudgment(list.get(j), target));
				} else {
					managementVO = list.get(j);
				}
				if (managementVO != null) {
					if (managementVO.getParentNodeId() != null && managementVO.getParentNodeId().equals(taskManagementVO.getNodeId())) {
						taskManagementVO.getChildren().add(managementVO);
					}
				} else {
					list.remove(j);
				}
			}
			getGrandChildren(list, taskManagementVO.getChildren());
		}
	}

	/**
	 * 财产程序流程节点显示判断
	 *
	 * @param taskNodeVO
	 * @return
	 */
	private static TaskNodeVO nodeJudgment(TaskNodeVO taskNodeVO, Target target) {
		CaseeLiquiService caseeLiquiService = SpringUtils.getObject(CaseeLiquiService.class);

		Casee casee = caseeLiquiService.getById(taskNodeVO.getCaseeId());
		if ((casee.getCaseeType().equals(1010) || casee.getCaseeType().equals(2010)) && target != null) {//如果是诉前保全案件或者诉讼保全案件控制财产程序只显示查封或者冻结环节
			if (taskNodeVO.getStatus() == 0) {
				if (taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXCF_CCZXCF") || taskNodeVO.getNodeKey().equals("fundingZX_ZJZX_ZJZXDJ_ZJZXDJ")) {
					return taskNodeVO;
				}
			} else {
				return null;
			}
		} else {//其它程序判断
			if (taskNodeVO.getStatus() == 0) {
				AssetsReLiquiService assetsReLiquiService = SpringUtils.getObject(AssetsReLiquiService.class);
				TaskNodeService taskNodeService = SpringUtils.getObject(TaskNodeService.class);

				if (taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXSQYS_CCZXSQYS") || taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ")) {//商情移送、资产处置移交
					AssetsReLiqui assetsReLiqui = assetsReLiquiService.queryAssetsMortgage(taskNodeVO.getProjectId(), taskNodeVO.getCaseeId(), target.getGoalId());
					Integer mortgagee = assetsReLiqui.getAssetsReCaseeDetail().getMortgagee();
					TaskNode taskNode = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXCF_CCZXCF").eq(TaskNode::getProjectId, taskNodeVO.getProjectId()).eq(TaskNode::getCaseeId, taskNodeVO.getCaseeId()).eq(TaskNode::getTargetId, taskNodeVO.getTargetId()));
					if (taskNode!=null){
						EntityZX_STZX_CCZXCF_CCZXCF entityZX_stzx_cczxcf_cczxcf = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXCF_CCZXCF.class);
						if (taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXSQYS_CCZXSQYS")) {
							if (entityZX_stzx_cczxcf_cczxcf != null && mortgagee == 0 && entityZX_stzx_cczxcf_cczxcf.getSealUpCondition() == 1) {//有抵押权查封为轮候
								return taskNodeVO;
							}
						} else {
							TaskNode taskNodeSQYS = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXSQYS_CCZXSQYS").eq(TaskNode::getProjectId, taskNodeVO.getProjectId()).eq(TaskNode::getCaseeId, taskNodeVO.getCaseeId()).eq(TaskNode::getTargetId, taskNodeVO.getTargetId()));
							EntityZX_STZX_CCZXSQYS_CCZXSQYS entityZX_stzx_cczxsqys_cczxsqys = JsonUtils.jsonToPojo(taskNodeSQYS.getFormData(), EntityZX_STZX_CCZXSQYS_CCZXSQYS.class);
							if (entityZX_stzx_cczxcf_cczxcf != null) {
								if ((mortgagee == 0 && entityZX_stzx_cczxcf_cczxcf.getSealUpCondition() == 0) ||//有抵押权、查封为首封
										(mortgagee == 0 && entityZX_stzx_cczxcf_cczxcf.getSealUpCondition() == 1 && entityZX_stzx_cczxsqys_cczxsqys != null && entityZX_stzx_cczxsqys_cczxsqys.getBusinessPleaseTransfer() == 0) ||//有抵押权、查封为轮候、已商情移送(结果成功)
										(mortgagee == 1 && entityZX_stzx_cczxcf_cczxcf.getSealUpCondition() == 0 && entityZX_stzx_cczxsqys_cczxsqys != null && entityZX_stzx_cczxsqys_cczxsqys.getBusinessPleaseTransfer() == 2)) {//无抵押权、首封、无抵押权人
									return taskNodeVO;
								}
							}
						}
					}
					return null;
				} else if (taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXZCCZYJSDQK_CCZXZCCZYJSDQK")) {//资产处置移交送达情况
					TaskNode taskNodeZCCZYJ = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ").eq(TaskNode::getProjectId, taskNodeVO.getProjectId()).eq(TaskNode::getCaseeId, taskNodeVO.getCaseeId()).eq(TaskNode::getTargetId, taskNodeVO.getTargetId()));
					if (taskNodeZCCZYJ.getStatus() != 0) {//资产处置移交环节已完成
						return taskNodeVO;
					}
				} else if (taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXBDCXKRH_CCZXBDCXKRH")) {//不动产现勘入户
					TaskNode taskNodeXk = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXXK_CCZXXK").eq(TaskNode::getProjectId, taskNodeVO.getProjectId()).eq(TaskNode::getCaseeId, taskNodeVO.getCaseeId()).eq(TaskNode::getTargetId, taskNodeVO.getTargetId()));
					EntityZX_STZX_CCZXXK_CCZXXK entityZX_stzx_cczxxk_cczxxk = JsonUtils.jsonToPojo(taskNodeXk.getFormData(), EntityZX_STZX_CCZXXK_CCZXXK.class);
					if (entityZX_stzx_cczxxk_cczxxk != null && entityZX_stzx_cczxxk_cczxxk.getWhetherHomeInspection() != null && entityZX_stzx_cczxxk_cczxxk.getWhetherHomeInspection() == 0) {//不动产现勘未入户
						return taskNodeVO;
					}
				} else if (taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXJGYJ_CCZXJGYJ")) {//价格依据
					TaskNode taskNodeXk = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXXK_CCZXXK").eq(TaskNode::getProjectId, taskNodeVO.getProjectId()).eq(TaskNode::getCaseeId, taskNodeVO.getCaseeId()).eq(TaskNode::getTargetId, taskNodeVO.getTargetId()));
					EntityZX_STZX_CCZXXK_CCZXXK entityZX_stzx_cczxxk_cczxxk = JsonUtils.jsonToPojo(taskNodeXk.getFormData(), EntityZX_STZX_CCZXXK_CCZXXK.class);

					TaskNode taskNodeXkRH = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXBDCXKRH_CCZXBDCXKRH").eq(TaskNode::getProjectId, taskNodeVO.getProjectId()).eq(TaskNode::getCaseeId, taskNodeVO.getCaseeId()).eq(TaskNode::getTargetId, taskNodeVO.getTargetId()));
					EntityZX_STZX_CCZXBDCXKRH_CCZXBDCXKRH entityZX_stzx_cczxbdcxkrh_cczxbdcxkrh = JsonUtils.jsonToPojo(taskNodeXkRH.getFormData(), EntityZX_STZX_CCZXBDCXKRH_CCZXBDCXKRH.class);

					//现勘已入户或者现勘和不动产现勘已完成或者动产现勘已完成
					if (entityZX_stzx_cczxxk_cczxxk != null && entityZX_stzx_cczxbdcxkrh_cczxbdcxkrh != null) {
						if (entityZX_stzx_cczxxk_cczxxk.getWhetherHomeInspection() == 1 || (entityZX_stzx_cczxxk_cczxxk != null && entityZX_stzx_cczxbdcxkrh_cczxbdcxkrh != null) || (entityZX_stzx_cczxxk_cczxxk != null && entityZX_stzx_cczxxk_cczxxk.getWhetherHomeInspection() == null)) {
							return taskNodeVO;
						}
					}
				} else if (taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXPMJGSDQK_CCZXPMJGSDQK")) {//拍卖结果送达情况
					TaskNode taskNodePMJG = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXPMJG_CCZXPMJG").eq(TaskNode::getProjectId, taskNodeVO.getProjectId()).eq(TaskNode::getCaseeId, taskNodeVO.getCaseeId()).eq(TaskNode::getTargetId, taskNodeVO.getTargetId()));
					EntityZX_STZX_CCZXPMJG_CCZXPMJG entityZX_stzx_cczxpmjg_cczxpmjg = JsonUtils.jsonToPojo(taskNodePMJG.getFormData(), EntityZX_STZX_CCZXPMJG_CCZXPMJG.class);
					//拍卖结果为成功
					if (entityZX_stzx_cczxpmjg_cczxpmjg != null && entityZX_stzx_cczxpmjg_cczxpmjg.getAuctionResults() == 0) {
						return taskNodeVO;
					}
				} else if (taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXCJCD_CCZXCJCD") || taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXZCDC_CCZXZCDC")) {//成交裁定、资产抵偿
					TaskNode taskNodeDK = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXDK_CCZXDK").eq(TaskNode::getProjectId, taskNodeVO.getProjectId()).eq(TaskNode::getCaseeId, taskNodeVO.getCaseeId()).eq(TaskNode::getTargetId, taskNodeVO.getTargetId()));
					if (taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXCJCD_CCZXCJCD")) {
						//完成到款环节
						if (taskNodeDK.getStatus() != 0) {
							return taskNodeVO;
						}
					} else {
						//没有完成到款环节
						if (taskNodeDK.getStatus() == 0) {
							return taskNodeVO;
						}
					}
				} else if (taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXDCCD_CCZXDCCD") || taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXDK_CCZXDK")) {//抵偿裁定、到款
					TaskNode taskNodeZCDC = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXZCDC_CCZXZCDC").eq(TaskNode::getProjectId, taskNodeVO.getProjectId()).eq(TaskNode::getCaseeId, taskNodeVO.getCaseeId()).eq(TaskNode::getTargetId, taskNodeVO.getTargetId()));
					if (taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXDCCD_CCZXDCCD")) {//抵偿裁定
						//完成资产抵偿环节
						if (taskNodeZCDC.getStatus() != 0) {
							return taskNodeVO;
						}
					} else {
						//没有完成资产抵偿环节
						if (taskNodeZCDC.getStatus() == 0) {
							return taskNodeVO;
						}
					}
				} else if (taskNodeVO.getNodeKey().equals("entityZX_STZX_CCZXDCCDSDQK_CCZXDCCDSDQK")) {//抵偿裁定送达情况
					TaskNode taskNodeDCCD = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXDCCD_CCZXDCCD").eq(TaskNode::getProjectId, taskNodeVO.getProjectId()).eq(TaskNode::getCaseeId, taskNodeVO.getCaseeId()).eq(TaskNode::getTargetId, taskNodeVO.getTargetId()));
					//抵偿裁定环节已完成
					if (taskNodeDCCD.getStatus() != 0) {
						return taskNodeVO;
					}
				} else {
					return taskNodeVO;
				}
			}
		}
		return null;
	}

	/**
	 * 将没有的子节点设置为null
	 *
	 * @param list
	 */
	public static void setChildrenAsNull(List<TaskNodeVO> list) {
		if (Objects.nonNull(list)) {
			for (int i = 0; i < list.size(); i++) {
				TaskNodeVO taskNodeVO = list.get(i);
				if ((taskNodeVO.getNodeAttributes() == 300 && taskNodeVO.getChildren().size() == 0) || (taskNodeVO.getNodeAttributes() == 300 && taskNodeVO.getChildren() == null)) {
					list.remove(i);
					i--;
				}
				if (taskNodeVO.getChildren().size() == 0) {
					taskNodeVO.setChildren(null);
				} else {
					setChildrenAsNull(taskNodeVO.getChildren());
				}
			}
		}
	}

	/**
	 * 修改其它父节点状态是否为跳过
	 *
	 * @param taskNodeService
	 * @param taskNode        任务信息
	 */
	public static void updateTaskNodeStatus(TaskNodeService taskNodeService, TaskNode taskNode) {
		//查询当前操作父节点信息
		TaskNode parentTaskNode = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getParentNodeId()).eq(TaskNode::getDelFlag, 0));
		//查询该标的节点属性为0的信息(所有父节点)
		List<TaskNode> TaskNodeList = taskNodeService.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getTargetId, taskNode.getTargetId()).eq(TaskNode::getNodeAttributes, 0).eq(TaskNode::getDelFlag, 0));
		for (TaskNode taskNodeInfo : TaskNodeList) {
			//如果当前节点是可跳过节点还是父节点并且节点顺序小于当前操作父节点顺序
			if (taskNodeInfo.getCanSkip() == 1 && taskNodeInfo.getNodeAttributes() == 0 && parentTaskNode.getNodeOrder() > taskNodeInfo.getNodeOrder()) {
				if (taskNodeInfo.getStatus() == 0) {//父节点状态为未提交
					taskNodeInfo.setStatus(301);//改成跳过状态
				}
			}
		}
		taskNodeService.updateBatchById(TaskNodeList);
	}
}
