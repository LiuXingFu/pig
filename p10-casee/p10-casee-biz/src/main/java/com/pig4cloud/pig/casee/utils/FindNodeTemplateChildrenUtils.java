package com.pig4cloud.pig.casee.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.casee.vo.TaskNodeVO;
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
	public static void getGrandParentChildren(List<TaskNodeVO> list, List<TaskNodeVO> voList, String parentId) {
		for (TaskNodeVO taskNodeVO : list) {
			if (StringUtils.isBlank(parentId)) {
				if (StringUtils.isBlank(taskNodeVO.getParentNodeId())) {
					voList.add(taskNodeVO);
				}
			}
		}
		getGrandChildren(list, voList);
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
	 * 将没有的子节点设置为null
	 *
	 * @param list
	 */
	public static void setChildrenAsNull(List<TaskNodeVO> list) {
		if (Objects.nonNull(list)) {
			for (int i = 0; i < list.size(); i++) {
				TaskNodeVO taskNodeVO = list.get(i);
				if (taskNodeVO.getChildren().size() == 0) {
					taskNodeVO.setChildren(null);
				} else {
					setChildrenAsNull(taskNodeVO.getChildren());
				}
			}
		}
	}

	/**
	 *
	 * 修改其它父节点状态是否为跳过
	 *
	 * @param taskNodeService
	 * @param taskNode	任务信息
	 */
	public static void updateTaskNodeStatus(TaskNodeService taskNodeService,TaskNode taskNode) {
		//查询当前操作父节点信息
		TaskNode parentTaskNode = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getParentNodeId()).eq(TaskNode::getDelFlag, 0));
		//查询该标的节点属性为0的信息(所有父节点)
		List<TaskNode> TaskNodeList = taskNodeService.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getTargetId, taskNode.getTargetId()).eq(TaskNode::getNodeAttributes,0).eq(TaskNode::getDelFlag, 0));
		for (TaskNode taskNodeInfo : TaskNodeList) {
			//如果当前节点是可跳过节点还是父节点并且节点顺序小于当前操作父节点顺序
			if (taskNodeInfo.getCanSkip()==1&&taskNodeInfo.getNodeAttributes()==0&&parentTaskNode.getNodeOrder()>taskNodeInfo.getNodeOrder()){
				if (taskNodeInfo.getStatus()==0){//父节点状态为未提交
					taskNodeInfo.setStatus(301);//改成跳过状态
				}
			}
		}
		taskNodeService.updateBatchById(TaskNodeList);
	}
}
