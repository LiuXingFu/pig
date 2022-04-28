/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.casee.service.impl;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.dto.TaskNodeTemplateDTO;
import com.pig4cloud.pig.admin.api.entity.InsOutlesUser;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.admin.api.feign.RemoteInsOutlesUserService;
import com.pig4cloud.pig.admin.api.feign.RemoteMessageRecordService;
import com.pig4cloud.pig.admin.api.feign.RemoteNodeTemplateService;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.AssetsReLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.AssetsReCaseeDetail;
import com.pig4cloud.pig.casee.entity.liquientity.BehaviorLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.BehaviorLiquiDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.CaseeLiquiDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.*;
import com.pig4cloud.pig.casee.entity.paifu.PaiFu_JGRZ_DXXJ_XJJG;
import com.pig4cloud.pig.casee.entity.paifu.PaiFu_JGRZ_PGDJ_XTLR;
import com.pig4cloud.pig.casee.entity.paifu.PaiFu_JGRZ_SFYJ_YJJG;
import com.pig4cloud.pig.casee.entity.paifu.PaiFu_JGRZ_WLXJ_XTLR;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXPMGG_CCZXPMGG;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.ReceiptRecord;
import com.pig4cloud.pig.casee.mapper.TargetMapper;
import com.pig4cloud.pig.casee.mapper.TaskNodeMapper;
import com.pig4cloud.pig.casee.nodehandler.NodeTaskHandlerRegister;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.utils.FindNodeTemplateChildrenUtils;
import com.pig4cloud.pig.casee.vo.AgentMatterVO;
import com.pig4cloud.pig.casee.vo.TaskNodeVO;
import com.pig4cloud.pig.casee.vo.paifu.NodeMessageRecordVO;
import com.pig4cloud.pig.common.core.constant.CaseeOrTargetTaskFlowConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.*;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.transaction.annotation.Transactional;
import net.sf.json.JSONObject;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程节点表
 *
 * @author xiaojun
 * @date 2021-09-07 17:01:38
 */
@Service
public class TaskNodeServiceImpl extends ServiceImpl<TaskNodeMapper, TaskNode> implements TaskNodeService {
	@Autowired
	private RemoteNodeTemplateService remoteNodeTemplateService;
	@Autowired
	private CaseePerformanceService caseePerformanceService;
	@Autowired
	private RemoteInsOutlesUserService remoteInsOutlesUserService;

	@Autowired
	public NodeTaskHandlerRegister nodeHandlerRegister;
	@Autowired
	private SecurityUtilsService securityUtilsService;
	@Autowired
	private TargetService targetService;
	@Autowired
	private TargetMapper targetMapper;
	@Autowired
	private TaskRecordService taskRecordService;
	@Autowired
	private AssetsService assetsService;
	@Autowired
	private AssetsReLiquiService assetsReCaseeService;
	@Autowired
	private BehaviorService BehaviorLiquiService;
	@Autowired
	private CaseeLiquiService caseeLiquiService;
	@Autowired
	private RemoteMessageRecordService remoteMessageRecordService;
	@Autowired
	CaseeHandlingRecordsService caseeHandlingRecordsService;

	private int sum = 0;


	@Override
	public Deployment addDeployment(String bpmn, String name) {

		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到repositoryService实例
		RepositoryService repositoryService = processEngine.getRepositoryService();

		//3，进行部署
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource(bpmn) //添加bpmn资源
				.name(name)
				.deploy();

		//返回部署对象
		return deployment;
	}

	@Override
	@Transactional
	public ProcessInstance beginFlow(Object objectDTO, String taskFlowName) {
		//设置assignee,map键对应配置中的变量名
		// 设置UEL-Value表达式中的值
		Map<String, Object> variables = new LinkedHashMap<>();
		variables.put(taskFlowName, objectDTO);
		try {
			ObjectTransitionEntityUtils objectTransitionEntityUtils = new ObjectTransitionEntityUtils();

			//1，创建processEngine对象
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			//2，得到RuntimeService实例
			RuntimeService runtimeService = processEngine.getRuntimeService();
			//3，创建流程实例，绑定业务主键id(节点任务id)流程定义的key需要知道流程唯一id：holiday
			//第一个参数：流程定义的key
			//第二个参数：绑定业务主键id
			//第三个参数：流程变量所对应的值
			ProcessInstance processInstance = null;
			if (taskFlowName.equals(CaseeOrTargetTaskFlowConstants.TASK_OBJECT)) {//办理任务流对象名称
				TaskFlowDTO taskFlowDTO = objectTransitionEntityUtils.readValueMap(objectDTO, TaskFlowDTO.class);
				processInstance = runtimeService.startProcessInstanceByKey(taskFlowDTO.getTaskKey(), taskFlowDTO.getNodeId(), variables);
			} else if (taskFlowName.equals(CaseeOrTargetTaskFlowConstants.CASEEORTARGET_OBJECT)) {//案件或标的任务流对象名称
				CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO = objectTransitionEntityUtils.readValueMap(objectDTO, CaseeOrTargetTaskFlowDTO.class);
				processInstance = runtimeService.startProcessInstanceByKey(caseeOrTargetTaskFlowDTO.getTaskKey(), caseeOrTargetTaskFlowDTO.getNodeId(), variables);
			}
			//4，返回流程实例相关信息CaseeOrTargetTaskFlow
			return processInstance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public Task executionTask(Object objectDTO, String taskName, String taskFlowName) {
		//得到ProcessEngine
		ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
		//得到taskService
		TaskService taskService = defaultProcessEngine.getTaskService();

		Map<String, Object> variables = new LinkedHashMap<>();
		ObjectTransitionEntityUtils objectTransitionEntityUtils = new ObjectTransitionEntityUtils();

		//任务流唯一key
		String taskFlowKey = null;
		//任务流实例id
		String actReProcdefId = null;

		List list = new ArrayList<>();
		try {
			if (taskFlowName.equals(CaseeOrTargetTaskFlowConstants.TASK_OBJECT)) {//处理审核流程节点任务
				TaskFlowDTO taskFlowDTO = objectTransitionEntityUtils.readValueMap(objectDTO, TaskFlowDTO.class);
				taskFlowKey = taskFlowDTO.getTaskKey();
				actReProcdefId = taskFlowDTO.getActReProcdefId();
				if (null != taskFlowDTO.getAuditorIdList() && taskFlowDTO.getAuditorIdList().size() > 0) {
					list.addAll(taskFlowDTO.getAuditorIdList());
				}
				variables.put(taskFlowName, taskFlowDTO);
			} else if (taskFlowName.equals(CaseeOrTargetTaskFlowConstants.CASEEORTARGET_OBJECT)) {//处理案件或标的任务流（暂缓、和解）
				CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO = objectTransitionEntityUtils.readValueMap(objectDTO, CaseeOrTargetTaskFlowDTO.class);
				taskFlowKey = caseeOrTargetTaskFlowDTO.getTaskKey();
				actReProcdefId = caseeOrTargetTaskFlowDTO.getActReProcdefId();
				if (null != caseeOrTargetTaskFlowDTO.getCaseeOrTargetAuditorList() && caseeOrTargetTaskFlowDTO.getCaseeOrTargetAuditorList().size() > 0) {
					list.addAll(caseeOrTargetTaskFlowDTO.getCaseeOrTargetAuditorList());
				}
				variables.put(taskFlowName, caseeOrTargetTaskFlowDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		//通过流程定义key和流程实例id查询当前办理人任务信息
		Task task = null;
		if (null != list && list.size() > 0) {//审核人存在多个，完成任务时要根据当前审核人去查询任务
			task = taskService.createTaskQuery().processDefinitionKey(taskFlowKey)
					.processInstanceId(actReProcdefId).taskAssignee(list.get(0).toString()).taskName(taskName).singleResult();
		} else {//根据任务流任务名称以及流程实例查询任务
			task = taskService.createTaskQuery().processDefinitionKey(taskFlowKey)
					.processInstanceId(actReProcdefId).taskName(taskName).singleResult();
		}

		//如果有任务，结束
		if (task != null) {
			taskService.complete(task.getId(), variables);
		}
		return task;
	}

	@Override
	public List<Task> queryTaskList(TaskFlowDTO taskFlowDTO) {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到TaskService对象
		TaskService taskService = processEngine.getTaskService();
		//3，根据流程定义的key，负责人assignee来实现当前任务办理人的任务列表查询
		List<Task> list = taskService.createTaskQuery()
				.processDefinitionKey(taskFlowDTO.getTaskKey())//流程定义key
				.taskAssignee(taskFlowDTO.getTaskAssigneeId().toString())
				.list();

		//4，任务列表的展示
		for (Task task : list) {
			System.out.println("流程实例id：" + task.getProcessInstanceId());
			System.out.println("任务id：" + task.getId());
			System.out.println("任务负责人：" + task.getAssignee());
			System.out.println("任务名称：" + task.getName());
		}

		return list;
	}

	@Override
	public void delDeployment(String actDeploymentId) {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到RepositoryService对象
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//3，执行删除流程定义，参数代表流程部署的id
		//注意事项：
		//
		//1，当我们正在执行的这一套流程没有完全审批结束的时候，如果此时要删除流程定义信息就会失败。
		//
		//2，如果要强制删除，可以使用repositoryService.deleteDeployment("1",true)；参数true代表级联删除，
		// 此时就会先删除没有完成的流程结点，最后就可以删除流程定义信息，false值代表不级联删除，不写默认为false
		repositoryService.deleteDeployment(actDeploymentId, true);
	}

	@Override
	public List<HistoricActivityInstance> queryHistoryData(String actReProcdefId) {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到HistoryService对象
		HistoryService historyService = processEngine.getHistoryService();
		//3，得到查询器
		HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
		//4，执行查询
		instanceQuery.processInstanceId(actReProcdefId); //设置流程实例的id

		List<HistoricActivityInstance> list = instanceQuery.orderByHistoricActivityInstanceStartTime().asc().list();//排序
		//5，遍历查询结果
		for (HistoricActivityInstance instance : list) {
			System.out.println(instance.getActivityId());
			System.out.println(instance.getActivityName());
			System.out.println(instance.getProcessDefinitionId());
			System.out.println(instance.getProcessInstanceId());
			System.out.println("===========================");
		}
		return list;
	}

	@Override
	@Transactional
	public TaskNodeTemplate queryNodeTemplateAddTaskNode(TaskNodeTemplateDTO taskNodeTemplateDTO, JSONObject jsonObject) {
		//查询模板数据
		R<TaskNodeTemplate> result = remoteNodeTemplateService.getById(taskNodeTemplateDTO.getTemplateId(), SecurityConstants.FROM);

		if (result == null || result.getData() == null) {
			return null;
		}
		sum = 0;
		genNodeFromTemplate(result.getData(), taskNodeTemplateDTO, jsonObject);
		return result.getData();
	}

	@Override
	@Transactional
	public List<TaskNodeVO> queryNodeTemplateByCaseeId(Integer projectId,Integer caseeId, Integer procedureNature, Integer id) {
		if (procedureNature.equals(20100)) {//资金财产
			procedureNature = 4041;
		} else if (procedureNature.equals(20200)) {//实体财产
			procedureNature = 4040;
		}
		if (procedureNature.equals(200)) {//行为违法
			procedureNature = 5051;
		} else if (procedureNature.equals(100)) {//行为限制
			procedureNature = 5050;
		}
		//1.根据案件id和程序性质查询所有任务节点数据
		List<TaskNodeVO> list = this.targetService.getTarget(projectId,caseeId, procedureNature, id);
		//2.任务节点对象集合
		List<TaskNodeVO> voList = new ArrayList<>();
		//3.将节点对象集合转换树形结构工具类
		FindNodeTemplateChildrenUtils.getGrandParentChildren(list, voList, null,procedureNature);
		//4.将没有子节点的数据设置为null
		FindNodeTemplateChildrenUtils.setChildrenAsNull(voList);
		return voList;
	}

	@Override
	@Transactional
	public Integer queryTaskProgress(String targetId) {
		// 1.根据标的id查询所有进度
		List<TaskNode> taskNodes = this.baseMapper.selectList(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getTargetId, targetId).eq(TaskNode::getDelFlag, 0));
		// 2.判断任务集合是否为空
		if (Objects.nonNull(taskNodes) && taskNodes.size() > 0) {
			// 3.查询主任务进度
			Map<String, Object> taskMap = getTaskProgress(taskNodes);
			// 4.返回任务进度
			for (TaskNode taskNode : taskNodes) {
				if (Objects.nonNull(taskNode.getParentNodeId())) {
					if (taskNode.getParentNodeId().equals(taskMap.get("id")) && taskNode.getNodeOrder().equals(taskMap.get("taskProgress"))) {
						return taskNode.getProcess();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 根据条件判断节点状态
	 *
	 * @param taskNodeId
	 * @return 0 正常
	 * 1 补录
	 * 2 节点前有不可跳过节点 无法填写。请确保从【公告前辅助】开始的必填步骤全部完成。
	 * 3 待审核
	 * 4 当前进度节点有未填写节点，节点名称为议价补录、议价结果
	 * 5 任务不存在，模板创建异常
	 * 6 委托中
	 */
	@Override
	@Transactional
	public TaskReminder judgmentTaskStatus(String taskNodeId, Integer canContinueExecution) {

		// 1.根据id查询任务
		TaskNode taskNode = this.getById(taskNodeId);

		TaskReminder taskReminder = new TaskReminder();

		// 2.判断任务是否存在 存在 继续执行 不存在 返回提示信息
		if (Objects.isNull(taskNode)) {
			taskReminder.setHint(5);
			taskReminder.setHintInformation("任务不存在，模板创建异常");
			return taskReminder;
		}

//		判断当前节点前是否有标记状态父节点
		TaskNode threeLevelParentTaskNode = queryParentTaskNode(taskNode, 3);

//		3.判断继续执行状态 0-无执行状态 1-继续执行
		if (canContinueExecution == 0) {
//			0-无执行状态

			List<String> nodeIds = this.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeAttributes, threeLevelParentTaskNode.getNodeAttributes())
					.lt(TaskNode::getSort, threeLevelParentTaskNode.getSort()).eq(TaskNode::getStatus, 501)).stream().map(TaskNode::getNodeId)
					.collect(Collectors.toList());

			List<TaskNode> nodeList = new ArrayList<>();

			if (nodeIds.size() > 0) {
				nodeList = this.list(new LambdaQueryWrapper<TaskNode>().in(TaskNode::getParentNodeId, nodeIds).eq(TaskNode::getStatus, 0));
			}

			List<TaskNode> nodes = this.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getParentNodeId, threeLevelParentTaskNode.getNodeId())
					.eq(TaskNode::getStatus, 0)
					.lt(TaskNode::getNodeOrder, taskNode.getNodeOrder()));

			nodeList.addAll(nodes);

			if (nodeList.size() > 0) {
				taskReminder.setHint(4);
				taskReminder.setHintInformation("当前进度节点前有未填写的子节点，节点名称为");
				for (TaskNode childNode : nodeList) {
					taskReminder.setHintInformation(taskReminder.getHintInformation() + childNode.getNodeName() + "、");
				}
				taskReminder.setHintInformation(taskReminder.getHintInformation().substring(0, taskReminder.getHintInformation().length() - 1));
				taskReminder.setHintInformation(taskReminder.getHintInformation() + "！是否继续执行？");
			} else {
				queryIsStatus(threeLevelParentTaskNode, taskNode, taskReminder);
			}

		} else {
//			1-继续执行
			queryIsStatus(threeLevelParentTaskNode, taskNode, taskReminder);
		}

		return taskReminder;
	}

	/**
	 * 判断节点状态
	 *
	 * @param parentTaskNode 第三层父节点信息
	 * @param taskNode       节点信息
	 * @param taskReminder   任务提示
	 * @return
	 */
	public TaskReminder queryIsStatus(TaskNode parentTaskNode, TaskNode taskNode, TaskReminder taskReminder) {
		// 查询当前任务进度
		Integer progress = queryTaskProgress(taskNode);
		// 返回步骤是否正常
		taskReminder = isStepNormal(parentTaskNode, taskReminder);
		// 正常继续执行 否则返回提示信息
		if (Objects.nonNull(taskReminder.getHint())) return taskReminder;
		// 判断节点状态
		taskReminder = determineNodeStatus(taskNode, progress, taskReminder);
		return taskReminder;
	}

	/**
	 * 是否步骤正常
	 *
	 * @param parentTaskNode
	 * @param taskReminder
	 * @return
	 */
	public TaskReminder isStepNormal(TaskNode parentTaskNode, TaskReminder taskReminder) {
		// 查询当前节点父节点前是否有不可跳过节点集合
		List<TaskNode> nodeList = this.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getCanSkip, 0)
				.eq(TaskNode::getNodeAttributes, 300)
				.eq(TaskNode::getCanSkip, 0)
				.eq(TaskNode::getTargetId, parentTaskNode.getTargetId())
				.ne(TaskNode::getStatus, 502)
				.ne(TaskNode::getStatus, 600)
				.lt(TaskNode::getSort, parentTaskNode.getSort())
				.eq(TaskNode::getDelFlag, 0)
				.orderByAsc(TaskNode::getSort));
		// 跳过节点集合不为空返回提示信息
		if (Objects.nonNull(nodeList) && nodeList.size() > 0) {
			TaskNode threeLevelParentTaskNode = queryParentTaskNode(nodeList.get(0), 3);
			taskReminder.setHint(2);
			taskReminder.setHintInformation("节点前有不可跳过节点 无法填写。请确保从【" + threeLevelParentTaskNode.getNodeName() + "-" + nodeList.get(0).getNodeName() + "】开始的步骤下的子任务全部完成。");
		}
		return taskReminder;
	}

	/**
	 * 判断节点状态
	 *
	 * @param taskNode
	 * @param progress
	 * @param taskReminder
	 * @return
	 */
	public TaskReminder determineNodeStatus(TaskNode taskNode, Integer progress, TaskReminder taskReminder) {

		// 判断节点状态如果为待审核中返回提示信息
		if (taskNode.getStatus().equals(101)) {
			taskReminder.setHint(3);
			taskReminder.setHintInformation(taskNode.getNodeName() + "节点审核中！");
			return taskReminder;
		}

		if (taskNode.getStatus().equals(600)) {
			taskReminder.setHint(6);
			taskReminder.setHintInformation(taskNode.getNodeName() + "节点委托中！");
			return taskReminder;
		}

		// 判断当前节点顺序是否小于进度 小于返回提示信息
		if (taskNode.getNodeOrder() < progress) {
			taskReminder.setHint(1);
			return taskReminder;
		}

		taskReminder.setHint(0);
		return taskReminder;
	}

	/**
	 * 获取当前任务进度
	 *
	 * @param taskNode
	 * @return
	 */
	public Integer queryTaskProgress(TaskNode taskNode) {
		// 获取当前点击任务的主任务 获取方式 标的名称+截取nodeKey第一个_字符查询
//		String mainTaskKey = nodeKey.substring(0, nodeKey.indexOf("_"));
//		TaskNode mainTask = this.getOne(new LambdaQueryWrapper<TaskNode>()
//				.eq(TaskNode::getTargetId, targetId)
//				.eq(TaskNode::getNodeKey, mainTaskKey)
//				.eq(TaskNode::getDelFlag, 0));

		TaskNode oneLevelParentTaskNode = queryParentTaskNode(taskNode, 1);

//		// 根据主任务id与当前子节点进度获取主任务的子节点信息 如：价格认证
//		TaskNode oneChild = this.getOne(new LambdaQueryWrapper<TaskNode>()
//				.eq(TaskNode::getParentNodeId, mainTask.getNodeId())
//				.eq(TaskNode::getNodeOrder, mainTask.getProcess())
//				.eq(TaskNode::getDelFlag, 0));

		return oneLevelParentTaskNode.getProcess();
	}

	/**
	 * @param taskFlowDTO
	 * @return
	 */
	@Override
	@Transactional
	public String saveNodeFormData(TaskFlowDTO taskFlowDTO) {
		if (taskFlowDTO != null) {  // 需要修改平行节点概念
			//提交办理任务生成任务流并保存任务数据
			taskFlowDTO = makeUpEntrustOrSubmit(taskFlowDTO);

			// 处理特殊节点与一般节点
			nodeHandlerRegister.onTaskNodeSubmit(taskFlowDTO);

			//添加任务记录数据
//			taskRecordService.addTaskRecord(taskFlowDTO, CaseeOrTargetTaskFlowConstants.TASK_OBJECT);

			//添加案件任务办理记录
			CaseeHandlingRecords caseeHandlingRecords=new CaseeHandlingRecords();
			BeanUtils.copyProperties(taskFlowDTO,caseeHandlingRecords);
			caseeHandlingRecords.setCreateTime(LocalDateTime.now());
			caseeHandlingRecords.setInsId(securityUtilsService.getCacheUser().getInsId());
			caseeHandlingRecords.setOutlesId(securityUtilsService.getCacheUser().getOutlesId());
			if (taskFlowDTO.getAssetsId()!=null){
				caseeHandlingRecords.setSourceId(taskFlowDTO.getAssetsId());
				caseeHandlingRecords.setSourceType(0);
			}
			caseeHandlingRecordsService.save(caseeHandlingRecords);
		}
		return taskFlowDTO.getNodeId();
	}

	@Override
	@Transactional
	public TaskNode commissionTransactionTask(TaskFlowDTO taskFlowDTO) {
		//设置委托类型为办理任务
		taskFlowDTO.setTrusteeType(0);

		//如果拒绝委托办理任务则把这条任务重新委托回去
		if (taskFlowDTO.getTrusteeStatus() != null && taskFlowDTO.getTrusteeStatus() == 1) {
			//拒绝委托任务时修改任务信息
			this.refuseEntrustUpdateTask(taskFlowDTO);
		} else {
			//添加任务记录数据
			taskRecordService.addTaskRecord(taskFlowDTO, CaseeOrTargetTaskFlowConstants.TASK_OBJECT);
		}

		//提交委托办理任务生成任务流并保存任务数据
		return makeUpEntrustOrSubmit(taskFlowDTO);
	}

	@Override
	@Transactional
	public TaskNode commissionAuditTask(TaskFlowDTO taskFlowDTO) {
		//设置委托类型为审核任务
		taskFlowDTO.setTrusteeType(1);
		PigUser cacheUser = securityUtilsService.getCacheUser();

		//如果拒绝委托审核任务则把这条审核任务重新委托回去
		if (taskFlowDTO.getTrusteeStatus() != null && taskFlowDTO.getTrusteeStatus() == 1) {
			//拒绝委托任务时修改任务信息
			this.refuseEntrustUpdateTask(taskFlowDTO);
		} else {
			//添加任务记录
			taskRecordService.addTaskRecord(taskFlowDTO, CaseeOrTargetTaskFlowConstants.TASK_OBJECT);
		}

		//设置任务流唯一key
		taskFlowDTO.setTaskKey(CaseeOrTargetTaskFlowConstants.TASK_KEY);
		//当前审核人id
		taskFlowDTO.getAuditorIdList().add(cacheUser.getId());
		//1.完成审核人流程
		this.executionTask(taskFlowDTO, "审核人", CaseeOrTargetTaskFlowConstants.TASK_OBJECT);

		//设置审核人id为委托人id
		taskFlowDTO.getAuditorIdList().add(taskFlowDTO.getCommissionUserId());

		//完成正在处理流程
		this.executionTask(taskFlowDTO, "正在处理", CaseeOrTargetTaskFlowConstants.TASK_OBJECT);

		//查询任务记录状态为未审核的
		List<TaskRecord> taskRecordList = taskRecordService.list(new LambdaQueryWrapper<TaskRecord>().eq(TaskRecord::getNodeId, taskFlowDTO.getNodeId()).eq(TaskRecord::getStatus, 101).eq(TaskRecord::getCanEntrust, 1));
		if (taskRecordList.size() > 0) {
			for (TaskRecord taskRecord : taskRecordList) {
				taskRecord.setAuditorId(cacheUser.getId());
				taskRecord.setAuditorOutlesId(cacheUser.getOutlesId());
				taskRecord.setAuditorInsId(cacheUser.getInsId());
				taskRecord.setStatus(taskFlowDTO.getStatus());
				taskRecord.setTrusteeType(taskFlowDTO.getTrusteeType());
			}
			taskRecordService.updateBatchById(taskRecordList);
		}
		return taskFlowDTO;
	}

	@Override
	@Transactional
	public TaskFlowDTO refuseEntrustUpdateTask(TaskFlowDTO taskFlowDTO) {
		PigUser cacheUser = securityUtilsService.getCacheUser();
		TaskRecord taskRecord = taskRecordService.getOne(new LambdaQueryWrapper<TaskRecord>().eq(TaskRecord::getNodeId, taskFlowDTO.getNodeId()).eq(TaskRecord::getStatus, 600).eq(TaskRecord::getTrusteeId, cacheUser.getId()));
		if (taskRecord != null) {
			//设置任务流委托信息
			taskFlowDTO.setCommissionInsId(taskRecord.getSubmitInsId());
			taskFlowDTO.setCommissionOutlesId(taskRecord.getSubmitOutlesId());
			taskFlowDTO.setCommissionUserId(taskRecord.getSubmitId());
			taskRecord.setHandlerId(cacheUser.getId());
			taskRecord.setTrusteeStatus(1);
			taskRecord.setTrusteeType(taskFlowDTO.getTrusteeType());
			taskRecordService.updateById(taskRecord);

			//如果委托类型为办理任务，修改审核任务记录数据状态为拒绝委托
			if (taskFlowDTO.getTrusteeType() == 0) {
				TaskRecord record = taskRecordService.getOne(new LambdaQueryWrapper<TaskRecord>().eq(TaskRecord::getNodeId, taskFlowDTO.getNodeId()).eq(TaskRecord::getStatus, 600).eq(TaskRecord::getCanEntrust, 0));
				if (record != null) {
					record.setTrusteeStatus(1);
					taskRecordService.updateById(record);
				}
			}
		}
		return taskFlowDTO;
	}

	@Override
	public TaskNode getByNodeId(String nodeId) {
		return this.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, nodeId).eq(TaskNode::getDelFlag, 0));
	}

	@Override
	@Transactional
	public boolean auditNode(TaskFlowDTO taskFlowDTO) {
		//设置任务流唯一key
		taskFlowDTO.setTaskKey(CaseeOrTargetTaskFlowConstants.TASK_KEY);
		//当前审核人id
		taskFlowDTO.getAuditorIdList().add(securityUtilsService.getCacheUser().getId());
		//1.完成审核人流程
		this.executionTask(taskFlowDTO, "审核人", CaseeOrTargetTaskFlowConstants.TASK_OBJECT);

		//设置审核任务不委托
		taskFlowDTO.setNeedCommissionedReview(0);

		//2.完成正在处理流程
		this.executionTask(taskFlowDTO, "正在处理", CaseeOrTargetTaskFlowConstants.TASK_OBJECT);

		if (Objects.nonNull(taskFlowDTO)) {
			//3.处理特殊节点与一般节点
			nodeHandlerRegister.onTaskNodeAudit(taskFlowDTO);

			taskRecordService.addTaskRecord(taskFlowDTO, CaseeOrTargetTaskFlowConstants.TASK_OBJECT);

			//4.更改审核状态与相关信息
			boolean updateById = this.updateById(taskFlowDTO);

			return updateById;
		}
		return false;
	}

	@Override
	@Transactional
	public CaseeOrTargetTaskFlowDTO auditCaseeOrTargetTask(CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO) {
		//设置任务流唯一key
		caseeOrTargetTaskFlowDTO.setTaskKey(CaseeOrTargetTaskFlowConstants.CASEEORTARGET_KEY);
		//当前审核人id
		caseeOrTargetTaskFlowDTO.getCaseeOrTargetAuditorList().add(securityUtilsService.getCacheUser().getId());
		//1.完成审核人流程
		this.executionTask(caseeOrTargetTaskFlowDTO, "案件或标的审核人", CaseeOrTargetTaskFlowConstants.CASEEORTARGET_OBJECT);

		//1.更改审核状态与相关信息
		this.updateById(caseeOrTargetTaskFlowDTO);

		//添加任务记录
		taskRecordService.addTaskRecord(caseeOrTargetTaskFlowDTO, CaseeOrTargetTaskFlowConstants.CASEEORTARGET_OBJECT);

		return caseeOrTargetTaskFlowDTO;
	}

	@Override
	public TaskNodeVO revoke(TaskNode taskNode) {
		TaskNode taskNodePmgg = this.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, taskNode.getProjectId()).eq(TaskNode::getCaseeId, taskNode.getCaseeId()).eq(TaskNode::getTargetId, taskNode.getTargetId()).eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXPMGG_CCZXPMGG").orderByDesc(TaskNode::getCreateTime).last("limit 1"));
		TaskNodeVO taskNodeVO=new TaskNodeVO();
		if (taskNodePmgg!=null){
			if (taskNodePmgg.getFormData()==null){
				return taskNodeVO;
			}

			EntityZX_STZX_CCZXPMGG_CCZXPMGG entityZX_stzx_cczxpmgg_cczxpmgg = JsonUtils.jsonToPojo(taskNodePmgg.getFormData(), EntityZX_STZX_CCZXPMGG_CCZXPMGG.class);

			if (entityZX_stzx_cczxpmgg_cczxpmgg.getRevoke()==1){
				return taskNodeVO;
			}
			Date announcementReleaseTime = entityZX_stzx_cczxpmgg_cczxpmgg.getAnnouncementReleaseTime();
			Date auctionStartDate = entityZX_stzx_cczxpmgg_cczxpmgg.getAuctionEndDate();
			Date nowTime = new Date();
			boolean effectiveDate = DataUtils.isEffectiveDate(nowTime, announcementReleaseTime, auctionStartDate);
			taskNodeVO.setRevokeSign(effectiveDate);
			taskNodeVO.setNodeId(taskNodePmgg.getNodeId());
			return taskNodeVO;
		}
		return taskNodeVO;
	}

	@Override
	public IPage<AgentMatterVO> auditList(Page page, TaskFlowDTO taskFlowDTO) {
		return this.baseMapper.auditList(page, SecurityUtils.getUser().getId(), taskFlowDTO);
	}

	@Override
	public Boolean queryNodeIdIsAuditor(String nodeId) {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到TaskService对象
		TaskService taskService = processEngine.getTaskService();
		//3，根据流程定义的key，负责人assignee来和任务名称查询当前用户的审核列表
		List<Task> list = taskService.createTaskQuery()
				.taskAssignee(SecurityUtils.getUser().getId().toString())
				.taskNameLike("%审核人")
				.list();

		for (Task task : list) {
			String assignee = task.getAssignee();
			//如果当前任务流审核人是当前登录用户则返回true
			if (assignee.equals(SecurityUtils.getUser().getId().toString())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public AgentMatterVO taskDetails(String nodeId) {
		return this.baseMapper.taskDetails(nodeId);
	}

	@Override
	public void setOneThreeParentNode(TaskNode threeLevelParentTaskNode, TaskNode taskNode) {
//			3.判断当前节点顺序(议价结果----顺序为3)等于第三层父节点子任务最终进度（双方议价----子任务最终进度3）则第二层节点（价格认证等）当前子节点进度=顺序+1
		if (threeLevelParentTaskNode.getFinalProcess() == taskNode.getNodeOrder()) {
			this.updateLevelTaskNodeList(taskNode, threeLevelParentTaskNode, taskNode.getAddProcess());
		} else {
			this.updateOneLevelTaskNodeList(taskNode, threeLevelParentTaskNode);
		}
//		//4.修改第一层父节点信息
//		this.updateOneLevelTaskNodeList(taskNode);
	}

	@Override
	public void updateBusinessData(AuditTargetDTO auditTargetDTO) {
		targetService.updateBusinessData(auditTargetDTO);
	}

	/**
	 * 补录流程节点任务信息
	 *
	 * @param taskFlowDTO
	 * @return
	 */
	@Override
	@Transactional
	public String makeUpNode(TaskFlowDTO taskFlowDTO) {
		if (Objects.nonNull(taskFlowDTO)) {
			//提交补录办理任务生成任务流并保存任务数据
			taskFlowDTO = makeUpEntrustOrSubmit(taskFlowDTO);
			//处理特殊节点与一般节点
			nodeHandlerRegister.onTaskNodeMakeUp(taskFlowDTO);

			PigUser cacheUser = securityUtilsService.getCacheUser();
			TaskRecord taskRecord = new TaskRecord();
			BeanCopyUtil.copyBean(taskFlowDTO, taskRecord);
			taskRecord.setCreateBy(null);
			taskRecord.setCreateTime(null);
			taskRecord.setUpdateTime(null);
			taskRecord.setUpdateBy(null);
			taskRecord.setSubmitOutlesId(cacheUser.getOutlesId());
			taskRecord.setSubmitInsId(cacheUser.getInsId());
			taskRecord.setSubmitId(cacheUser.getId());
			//添加任务记录数据
			taskRecordService.save(taskRecord);
		}
		return taskFlowDTO.getNodeId();
	}

	@Override
	@Transactional
	public TaskFlowDTO makeUpEntrustOrSubmit(TaskFlowDTO taskFlowDTO) {
		//当前登录用户信息
		PigUser user = SecurityUtils.getUser();

		//设置办理人id
		taskFlowDTO.setTransactionId(user.getId());
		//设置任务流唯一key
		taskFlowDTO.setTaskKey(CaseeOrTargetTaskFlowConstants.TASK_KEY);

		//如果当前任务没有流程实例id则创建流程实例
		if (taskFlowDTO.getActReProcdefId() == null) {
			// 1.创建任务流程实例
			ProcessInstance processInstance = this.beginFlow(taskFlowDTO, CaseeOrTargetTaskFlowConstants.TASK_OBJECT);
			//设置流程实例id
			taskFlowDTO.setActReProcdefId(processInstance.getId());
		}

		//2.完成办理人流程
		this.executionTask(taskFlowDTO, "办理人", CaseeOrTargetTaskFlowConstants.TASK_OBJECT);

		//设置办理任务是否委托
		taskFlowDTO.setNeedCommission(taskFlowDTO.getNeedCommission());

		//如果不委托办理任务则设置审核人id
		if (taskFlowDTO.getNeedCommission() == 0) {
			//查询当前网点网点负责人
			R<List<InsOutlesUser>> outlesPrincipal = remoteInsOutlesUserService.getOutlesPrincipal(1, taskFlowDTO.getOutlesId(), SecurityConstants.FROM);
			for (InsOutlesUser insOutlesUser : outlesPrincipal.getData()) {
				//设置审核人id默认为网点负责人id
				taskFlowDTO.getAuditorIdList().add(insOutlesUser.getUserId());
			}
		} else if (taskFlowDTO.getNeedCommission() == 1) {//委托
			//设置办理人id为委托人id
			taskFlowDTO.setTransactionId(taskFlowDTO.getCommissionUserId());
		}

		//3.完成正在处理流程
		this.executionTask(taskFlowDTO, "正在处理", CaseeOrTargetTaskFlowConstants.TASK_OBJECT);

		// 4.保存任务数据
		this.updateById(taskFlowDTO);

		return taskFlowDTO;
	}

	/**
	 * 根据当前节点信息与层级查询层级父节点信息
	 *
	 * @param taskNode 节点信息
	 * @param level    层级
	 * @return
	 */
	@Override
	public TaskNode queryParentTaskNode(TaskNode taskNode, Integer level) {

		TaskNode threeLevelParentTaskNode = null;

		TaskNode twoLevelParentTaskNode = null;

		TaskNode oneLevelParentTaskNode;

		if (level <= 3) {
			//查询第三级父节点信息(例：双方议价)
			threeLevelParentTaskNode = this.getById(taskNode.getParentNodeId());
			if (level == 3) {
				return threeLevelParentTaskNode;
			}
		}
		if (level <= 2) {
			//查询第二级父节点信息(例：价格认证)
			twoLevelParentTaskNode = this.getById(threeLevelParentTaskNode.getParentNodeId());
			if (level == 2) {
				return twoLevelParentTaskNode;
			}

		}
		if (level == 1) {
			//查询第一级父节点信息(例：主任务)
			oneLevelParentTaskNode = this.getById(twoLevelParentTaskNode.getParentNodeId());
			return oneLevelParentTaskNode;
		}
		return null;
	}

	/**
	 * 判断跳过节点
	 *
	 * @param taskNode
	 * @return
	 */
	public void setSkipNode(TaskNode taskNode, Integer process) {
//		TaskNode threeLevelParentTaskNode = this.queryParentTaskNode(taskNode, 3);

//		1.根据第三层父节点（双方议价）的当前子节点进度判断如果第三层下子节点顺序小于（<3）当前子节点进度并且状态为未提交则将状态改为跳过
		List<TaskNode> list = this.list(new LambdaQueryWrapper<TaskNode>()
				.eq(TaskNode::getTargetId, taskNode.getTargetId())
				.eq(TaskNode::getNodeAttributes, 400)
				.lt(TaskNode::getNodeOrder, process)
				.eq(TaskNode::getStatus, 0));

/*//		2.判断第三层节点顺序小于第三层父节点(双方议价)顺序且未提交的子节点设置为跳过   ------无需判断第三层父节点
		// 查询顺序小于threeLevelParentTaskNode顺序的节点集合
		List<TaskNode> threeLevelTaskNodeList = this.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeAttributes, 300).lt(TaskNode::getNodeOrder, process));

//		3.将第三层节点的未提交子节点查出存入list集合中
		for (int i = 0; i < threeLevelTaskNodeList.size(); i++) {
			TaskNode threeLevelTaskNode = threeLevelTaskNodeList.get(i);
			List<TaskNode> nodeList = this.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getParentNodeId, threeLevelTaskNode.getNodeId()).eq(TaskNode::getStatus, 0));
			list.addAll(nodeList);
		}*/

		// 将list集合数据全部设置为跳过
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setStatus(301);
		}

		this.updateBatchById(list);
	}

	/**
	 * 修改第二层节点集合
	 *
	 * @param taskNode
	 * @param threeLevelParentTaskNode
	 * @param addProcess
	 */
	@Override
	public void updateLevelTaskNodeList(TaskNode taskNode, TaskNode threeLevelParentTaskNode, Integer addProcess) {
//		//根据第二层父节点父id查询第二层的所有节点集合
//		List<TaskNode> twoLevelTaskNodeList = this.list(new LambdaQueryWrapper<TaskNode>()
//				.eq(TaskNode::getNodeAttributes, 200)
//				.eq(TaskNode::getDelFlag, 0));
//		//给第二层节点集合设置当前子节点进度
//		for (int i = 0; i < twoLevelTaskNodeList.size(); i++) {
//			twoLevelTaskNodeList.get(i).setProcess(process);
//		}

		//查询第二层节点信息
		TaskNode twoLevelParentTaskNode = this.queryParentTaskNode(taskNode, 2);

		//查询第一层节点信息
		TaskNode oneLevelParentTaskNode = this.queryParentTaskNode(taskNode, 1);

		//将第三层顺序复制给第二层父节点
		twoLevelParentTaskNode.setProcess(threeLevelParentTaskNode.getNodeOrder());

		//将第任务节点顺序+第三层父节点增加进度存入第一层父节点
		oneLevelParentTaskNode.setProcess((taskNode.getNodeOrder() + addProcess));

		//将第三层节点设置为可跳过
		threeLevelParentTaskNode.setStatus(502);

		//更新第三层节点状态
		this.updateById(threeLevelParentTaskNode);

		//更新第二层节点
		this.updateById(twoLevelParentTaskNode);

		updateTwoTargetProcessJson(taskNode, twoLevelParentTaskNode);

		//更新第一层节点
		this.updateById(oneLevelParentTaskNode);

		//第一层节点key
		String oneLevelKey = "$";
		//更新标的json第一层节点进度数据
		this.updateTargetProcessJson(oneLevelParentTaskNode.getProcess() - 1, oneLevelParentTaskNode.getFinalProcess(), oneLevelKey, taskNode.getTargetId());

//      1.根据第三层父节点（双方议价）的当前子节点进度判断如果第三层下子节点顺序小于（<3）当前子节点进度并且状态为未提交则将状态改为跳过
//      2.判断第三层子节点顺序小于第三层父节点顺序且未提交的子节点设置为跳过
		this.setSkipNode(taskNode, oneLevelParentTaskNode.getProcess());
	}

	/**
	 * 更新第一层父节点信息
	 *
	 * @param taskNode
	 */
	@Override
	public void updateOneLevelTaskNodeList(TaskNode taskNode, TaskNode threeLevelParentTaskNode) {
		//查询第一层节点信息
		TaskNode oneLevelParentTaskNode = this.queryParentTaskNode(taskNode, 1);

		//将任务节点顺序+任务节点增加进度存入第一层父节点
		oneLevelParentTaskNode.setProcess((taskNode.getNodeOrder() + taskNode.getAddProcess()));

		threeLevelParentTaskNode.setStatus(501);

		this.updateById(threeLevelParentTaskNode);

		//更新第一层节点
		this.updateById(oneLevelParentTaskNode);

		//第一层节点key
		String oneLevelKey = "$";
		//更新标的json第一层节点进度数据
		this.updateTargetProcessJson(oneLevelParentTaskNode.getProcess() - 1, oneLevelParentTaskNode.getFinalProcess(), oneLevelKey, taskNode.getTargetId());

//      1.根据第三层父节点（双方议价）的当前子节点进度判断如果第三层下子节点顺序小于（<3）当前子节点进度并且状态为未提交则将状态改为跳过
//      2.判断第三层子节点顺序小于第三层父节点顺序且未提交的子节点设置为跳过
		this.setSkipNode(taskNode, oneLevelParentTaskNode.getProcess());
	}

//	/**
//	 * 修改第一层节点信息
//	 * @param taskNode
//	 */
//	@Override
//	public void updateOneLevelTaskNodeList(TaskNode taskNode) {
//		// 重新查询第二层父节点信息
//		TaskNode twoLevelParentTaskNode = this.queryParentTaskNode(taskNode, 2);
//
//		if (twoLevelParentTaskNode.getProcess() > twoLevelParentTaskNode.getFinalProcess()) {
////			4.根据二层节点的当前子节点进度查询第三层父节点的顺序得到第三层节点信息
//			TaskNode threeLevelTaskNode = this.getOne(new LambdaQueryWrapper<TaskNode>()
//					.eq(TaskNode::getNodeOrder, twoLevelParentTaskNode.getProcess())
//					.eq(TaskNode::getNodeAttributes, 300));
//
////			根据第三层节点的父节点id查询第二层节点
//			TaskNode twoLevelTaskNode = this.getOne(new LambdaQueryWrapper<TaskNode>()
//					.eq(TaskNode::getNodeId, threeLevelTaskNode.getParentNodeId()));
//
////			查询一层父节点信息
//			TaskNode oneLevelParentTaskNode =  this.getOne(new LambdaQueryWrapper<TaskNode>()
//					.eq(TaskNode::getNodeId, twoLevelTaskNode.getParentNodeId()));
//
////			5.更新
//			oneLevelParentTaskNode.setProcess(twoLevelTaskNode.getNodeOrder());
//		}
//	}

	/**
	 * 设置跳过节点与修改第三层父节点信息
	 *
	 * @param taskNode
	 * @return
	 */
	@Override
	public TaskNode setTaskNode(TaskNode taskNode) {
		// 1.查询第三层父节点信息
		TaskNode threeLevelParentTaskNode = this.queryParentTaskNode(taskNode, 3);

		// 2.将当前任务节点(议价结果----顺序为3)顺序复制到第三层父节点（双方议价----当前子节点进度为3）的当前子节点进度
		threeLevelParentTaskNode.setProcess(taskNode.getNodeOrder());

		this.updateById(threeLevelParentTaskNode);

		return threeLevelParentTaskNode;
	}

	@Override
	public String getKey(TaskNode taskNode) {
		//第五层父节点key
		String fiveParentNodeKey = "";
		//第四层父节点key
		String FourParentNodeKey = "";
		//第三层父节点key
		String threeParentNodeKey = "";
		//第二层父节点key
		String twoParentNodeKey = "";

		String[] keys = taskNode.getNodeKey().split("_");

		Integer keyCount = keys.length - 1;

		String key = "";

		//判断任务节点层级，根据层级拼接key
		if (keyCount == 5) {
			fiveParentNodeKey = taskNode.getNodeKey().substring(0, taskNode.getNodeKey().lastIndexOf("_"));
			FourParentNodeKey = fiveParentNodeKey.substring(0, fiveParentNodeKey.lastIndexOf("_"));
			threeParentNodeKey = FourParentNodeKey.substring(0, FourParentNodeKey.lastIndexOf("_"));
			twoParentNodeKey = threeParentNodeKey.substring(0, threeParentNodeKey.lastIndexOf("_"));
			key = "$." + twoParentNodeKey + "." + threeParentNodeKey + "." + FourParentNodeKey + "." + fiveParentNodeKey + "." + taskNode.getNodeKey();
		} else if (keyCount == 4) {
			FourParentNodeKey = taskNode.getNodeKey().substring(0, taskNode.getNodeKey().lastIndexOf("_"));
			threeParentNodeKey = FourParentNodeKey.substring(0, FourParentNodeKey.lastIndexOf("_"));
			twoParentNodeKey = threeParentNodeKey.substring(0, threeParentNodeKey.lastIndexOf("_"));
			key = "$." + twoParentNodeKey + "." + threeParentNodeKey + "." + FourParentNodeKey + "." + taskNode.getNodeKey();
		} else if (keyCount == 3) {
			threeParentNodeKey = taskNode.getNodeKey().substring(0, taskNode.getNodeKey().lastIndexOf("_"));
			twoParentNodeKey = threeParentNodeKey.substring(0, threeParentNodeKey.lastIndexOf("_"));
			key = "$." + twoParentNodeKey + "." + threeParentNodeKey + "." + taskNode.getNodeKey();
		} else if (keyCount == 2) {
			twoParentNodeKey = taskNode.getNodeKey().substring(0, taskNode.getNodeKey().lastIndexOf("_"));
			key = "$." + twoParentNodeKey + "." + taskNode.getNodeKey();
		} else {
			key = "$." + taskNode.getNodeKey();
		}
		return key;
	}

	/**
	 * 根据标的id查询任务集合，然后将标的json更新
	 *
	 * @param targetId
	 */
	@Override
	@Transactional
	public void setTaskNodeListByTargetId(Integer targetId, List<String> keys) {
		// 根据key类别查询状态不是未提交与跳过的任务对象集合
		List<TaskNode> nodeList = this.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getTargetId, targetId)
				.in(TaskNode::getNodeKey, keys).and(i -> i.ne(TaskNode::getStatus, 0).ne(TaskNode::getStatus, 301)));

		//循环任务对象集合将任务信息更新标的json
		for (int i = 0; i < nodeList.size(); i++) {

			//根据任务信息获取相应key
			String nodeKey = this.getKey(nodeList.get(i));

			AuditTargetDTO targetDTO = new AuditTargetDTO();

			targetDTO.setKey(nodeKey);
			targetDTO.setFormData(nodeList.get(i).getFormData());
			targetDTO.setTargetId(nodeList.get(i).getTargetId());

//			3.根据节点key修改标的表单json数据
			this.updateBusinessData(targetDTO);

		}
	}

	/**
	 * 根据任务信息更新标的json
	 *
	 * @param taskFlowDTO
	 * @param auditTargetDTO
	 */
	@Override
	@Transactional
	public void setTaskNodeByTargetId(TaskFlowDTO taskFlowDTO, AuditTargetDTO auditTargetDTO) {
		//1.根据节点id查询案件绩效考核表
		CaseePerformance caseePerformance = this.caseePerformanceService.getOne(new LambdaQueryWrapper<CaseePerformance>()
				.eq(CaseePerformance::getNodeId, taskFlowDTO.getNodeId())
				.eq(CaseePerformance::getDelFlag, 0));
		if (Objects.nonNull(caseePerformance)) {
			//2.修改计费状态
			caseePerformance.setPerfAmount(taskFlowDTO.getPerformanceAmount());
			caseePerformance.setPerfStatus(taskFlowDTO.getPerfStatus());
			caseePerformance.setUserId(taskFlowDTO.getUpdateBy());
			caseePerformance.setRemark(taskFlowDTO.getDescribed());
			this.caseePerformanceService.updateById(caseePerformance);
		}
//		3.节点key修改标的表单json数据
		this.updateBusinessData(auditTargetDTO);
//		//更改审核状态与相关信息
//		this.updateById(taskFlowDTO);
	}

	/**
	 * 查询议价成功的任务集合
	 *
	 * @param nodeList
	 * @return
	 */
	@Override
	public List<TaskNode> setTaskNodes(List<TaskNode> nodeList) {
		List<TaskNode> updateNodeList = new ArrayList<>();

		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).getNodeKey().equals("paiFu_JGRZ_SFYJ_YJJG")) {
				PaiFu_JGRZ_SFYJ_YJJG paiFu_jgrz_sfyj_yjjg = JsonUtils.jsonToPojo(nodeList.get(i).getFormData(), PaiFu_JGRZ_SFYJ_YJJG.class);
				if (paiFu_jgrz_sfyj_yjjg.getBargainInquirySuccess() == 1) {
					paiFu_jgrz_sfyj_yjjg.setBargainInquirySuccess(0);
					String formData = JsonUtils.objectToJson(paiFu_jgrz_sfyj_yjjg);
					nodeList.get(i).setFormData(formData);
					updateNodeList.add(nodeList.get(i));
				}
			} else if (nodeList.get(i).getNodeKey().equals("paiFu_JGRZ_DXXJ_XJJG")) {
				PaiFu_JGRZ_DXXJ_XJJG paiFu_jgrz_dxxj_xjjg = JsonUtils.jsonToPojo(nodeList.get(i).getFormData(), PaiFu_JGRZ_DXXJ_XJJG.class);
				if (paiFu_jgrz_dxxj_xjjg.getBargainInquirySuccess() == 1) {
					paiFu_jgrz_dxxj_xjjg.setBargainInquirySuccess(0);
					String formData = JsonUtils.objectToJson(paiFu_jgrz_dxxj_xjjg);
					nodeList.get(i).setFormData(formData);
					updateNodeList.add(nodeList.get(i));
				}
			} else if (nodeList.get(i).getNodeKey().equals("paiFu_JGRZ_WLXJ_XTLR")) {
				PaiFu_JGRZ_WLXJ_XTLR paiFu_jgrz_wlxj_xtlr = JsonUtils.jsonToPojo(nodeList.get(i).getFormData(), PaiFu_JGRZ_WLXJ_XTLR.class);
				if (paiFu_jgrz_wlxj_xtlr.getBargainInquirySuccess() == 1) {
					paiFu_jgrz_wlxj_xtlr.setBargainInquirySuccess(0);
					String formData = JsonUtils.objectToJson(paiFu_jgrz_wlxj_xtlr);
					nodeList.get(i).setFormData(formData);
					updateNodeList.add(nodeList.get(i));
				}
			} else if (nodeList.get(i).getNodeKey() == "paiFu_JGRZ_PGDJ_XTLR") {
				PaiFu_JGRZ_PGDJ_XTLR paiFu_jgrz_pgdj_xtlr = JsonUtils.jsonToPojo(nodeList.get(i).getFormData(), PaiFu_JGRZ_PGDJ_XTLR.class);
				if (paiFu_jgrz_pgdj_xtlr.getBargainInquirySuccess() == 1) {
					paiFu_jgrz_pgdj_xtlr.setBargainInquirySuccess(0);
					String formData = JsonUtils.objectToJson(paiFu_jgrz_pgdj_xtlr);
					nodeList.get(i).setFormData(formData);
					updateNodeList.add(nodeList.get(i));
				}
			}
		}
		return updateNodeList;
	}

	@Override
	public <T> void YJ_HandlerTaskMakeUp(Class<T> clazz, TaskNode taskNode, List<String> keys) {

		Integer bargainInquirySuccess = getTaskNodeVariable(clazz, taskNode);

		// 判断是否议价成功
		if (bargainInquirySuccess == 1) {
			// 成功----》

			this.setNodeTaskMakeUpProcess(taskNode);

			// 根据key类别查询状态不是未提交与跳过的任务对象集合
			List<TaskNode> nodeList = this.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getTargetId, taskNode.getTargetId())
					.in(TaskNode::getNodeKey, keys).and(i -> i.ne(TaskNode::getStatus, 0).ne(TaskNode::getStatus, 301)));

			// 循环任务对象集合将相关议价结果设置为议价失败
			// 1.解析json
			// 2.设置议价失败
			// 3.将议价失败转成String存入任务对象formData列中

			List<TaskNode> updateNodeList = this.setTaskNodes(nodeList);

			if (Objects.nonNull(updateNodeList)) {
				//更新任务对象列表
				this.updateBatchById(updateNodeList);
			}
		} else {
			this.setNodeTaskMakeUpProcess(taskNode);
		}
	}

	@Override
	public <T> Integer getTaskNodeVariable(Class<T> clazz, TaskNode taskNode) {
		T pub = JsonUtils.jsonToPojo(taskNode.getFormData(), clazz);

		Integer variable = null;

		for (Field field : pub.getClass().getDeclaredFields()) {//通过反射遍历当前传过来的对象进行赋值和拿值
			field.setAccessible(true);
			try {
				if (field.getName().equals("bargainInquirySuccess")) {
					String type = field.get(pub) == null ? null : String.valueOf(field.get(pub));
					if (type != null) {
						variable = Integer.parseInt(type);
					}
				} else if (field.getName().equals("auctionResultsType")) {
					String type = field.get(pub) == null ? null : String.valueOf(field.get(pub));
					if (type != null) {
						variable = Integer.parseInt(type);
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return variable;
	}

	@Override
	public <T> void YJ_HandlerTaskSubmit(Class<T> clazz, TaskNode taskNode) {

		Integer bargainInquirySuccess = this.getTaskNodeVariable(clazz, taskNode);
		// 设置跳过节点与修改第三层父节点信息
		TaskNode threeLevelParentTaskNode = this.setTaskNode(taskNode);

//		if (taskNode.getStatus() == 301) {
//			this.setOneThreeParentNode(threeLevelParentTaskNode, taskNode);
//		} else {
//			1.当前节点为议价结果时将节点JSON（表单填充数据）转换成相应实体类
//			2.根据不同条件判断节点成功与失败
		if (bargainInquirySuccess == 1) {
//				成功----》
//				1.判断当前节点顺序(议价结果----顺序为3)等于第三层父节点子任务最终进度（双方议价----子任务最终进度3）则第二层节点（价格认证等）增加任务节点（议价结果）的增加进度+顺序 5
			this.updateLevelTaskNodeList(taskNode, threeLevelParentTaskNode, threeLevelParentTaskNode.getAddProcess());

			//4.修改第一层父节点信息
//				taskNodeService.updateOneLevelTaskNodeList(taskNode);
		} else {
			//失败----》
			this.setOneThreeParentNode(threeLevelParentTaskNode, taskNode);
		}
//		}
	}

	@Override
	public <T> void YJ_HandlerTaskAudit(Class<T> clazz, TaskFlowDTO taskFlowDTO, List<String> keys) {

		Integer bargainInquirySuccess = this.getTaskNodeVariable(clazz, taskFlowDTO);

		AuditTargetDTO auditTargetDTO = this.getAuditTargetDTO(taskFlowDTO);

		//		1.判断是否审核成功 判断是否计费
		if (taskFlowDTO.getStatus().equals(403)) {
//			计费------》
			this.setTaskNodeByTargetId(taskFlowDTO, auditTargetDTO);
			// 解析任务JSON
			// 判断是否议价成功
			if (bargainInquirySuccess == 1) {

				// 成功----》
				this.setTaskNodeListByTargetId(taskFlowDTO.getTargetId(), keys);

			}
		} else {
//			不计费----》
//			1.节点key修改标的表单json数据
//			taskNodeService.updateContent(auditTargetDTO);
		}

	}

	@Override
	public <T> void PMJG_HandlerTaskSubmit(Class<T> clazz, TaskNode taskNode) {

		Integer bargainInquirySuccess = this.getTaskNodeVariable(clazz, taskNode);
		// 设置跳过节点与修改第三层父节点信息
		TaskNode threeLevelParentTaskNode = this.setTaskNode(taskNode);

//		if (taskNode.getStatus() == 301) {
//			this.setOneThreeParentNode(threeLevelParentTaskNode, taskNode);
//		} else {
//			1.根据拍卖结果判断
		if (bargainInquirySuccess == 0) {
//				成交----》
//				1.判断当前节点顺序(议价结果----顺序为3)等于第三层父节点子任务最终进度（双方议价----子任务最终进度3）则第二层节点（价格认证等）增加任务节点（议价结果）的增加进度+顺序 5
			this.updateLevelTaskNodeList(taskNode, threeLevelParentTaskNode, threeLevelParentTaskNode.getAddProcess());

			//4.修改第一层父节点信息
//				taskNodeService.updateOneLevelTaskNodeList(taskNode);
		} else if (bargainInquirySuccess == 1) {
			//流拍----》
			this.setOneThreeParentNode(threeLevelParentTaskNode, taskNode);
		} else {
			// 撤回

		}
//		}

	}

	@Override
	public <T> void PMJG_HandlerTaskMakeUp(Class<T> clazz, TaskNode taskNode) {

		TaskNode oneLevelParentTaskNode = this.queryParentTaskNode(taskNode, 1);

		if (Objects.nonNull(oneLevelParentTaskNode)) {
			if (oneLevelParentTaskNode.getProcess() <= 43) {
				this.PMJG_HandlerTaskSubmit(clazz, taskNode);
				this.setNodeTaskMakeUpProcess(taskNode);
			}
		}

	}

	@Override
	public AuditTargetDTO getAuditTargetDTO(TaskNode taskNode) {

		//根据任务信息获取相应key
		String key = this.getKey(taskNode);

		//任务审核修改标的DTO
		AuditTargetDTO auditTargetDTO = new AuditTargetDTO();

		auditTargetDTO.setKey(key);
		auditTargetDTO.setFormData(taskNode.getFormData());

		auditTargetDTO.setTargetId(taskNode.getTargetId());
		return auditTargetDTO;
	}

	@Override
	public boolean jumpOver(TaskNode taskNode) {

		this.updateById(taskNode);

		// 设置跳过节点与修改第三层父节点信息
		TaskNode threeLevelParentTaskNode = this.setTaskNode(taskNode);

		this.setOneThreeParentNode(threeLevelParentTaskNode, taskNode);

		return true;
	}

	/**
	 * 根据条件查询任务节点集合
	 *
	 * @param taskNode
	 * @return
	 */
	@Override
	public List<TaskNode> queryTaskListByCondition(TaskNode taskNode) {

		// 条件对象
		LambdaQueryWrapper<TaskNode> queryWrapper = new LambdaQueryWrapper<>();

		// 判断任务变量是否为空
		// 判断任务id不为空
		if (Objects.nonNull(taskNode.getNodeId())) {
			queryWrapper.eq(TaskNode::getNodeId, taskNode.getNodeId());
		}

		// 判断任务key不为空
		if (Objects.nonNull(taskNode.getNodeKey())) {
			queryWrapper.eq(TaskNode::getNodeKey, taskNode.getNodeKey());
		}

		// 判断标的id不为空
		if (Objects.nonNull(taskNode.getTargetId())) {
			queryWrapper.eq(TaskNode::getTargetId, taskNode.getTargetId());
		}

		// 判断顺序不为空
		if (Objects.nonNull(taskNode.getNodeOrder())) {
			queryWrapper.eq(TaskNode::getNodeOrder, taskNode.getNodeOrder());
		}

		// 判断状态不为空
		if (Objects.nonNull(taskNode.getStatus())) {
			queryWrapper.eq(TaskNode::getStatus, taskNode.getStatus());
		}

		// 判断机构id不为空
		if (Objects.nonNull(taskNode.getInsId())) {
			queryWrapper.eq(TaskNode::getInsId, taskNode.getInsId());
		}

		// 判断网点id不为空
		if (Objects.nonNull(taskNode.getOutlesId())) {
			queryWrapper.eq(TaskNode::getOutlesId, taskNode.getOutlesId());
		}

		// 判断案件id不为空
		if (Objects.nonNull(taskNode.getCaseeId())) {
			queryWrapper.eq(TaskNode::getCaseeId, taskNode.getCaseeId());
		}

		// 判断表单id不为空
		if (Objects.nonNull(taskNode.getFormId())) {
			queryWrapper.eq(TaskNode::getFormId, taskNode.getFormId());
		}

		// 判断任务父id不为空
		if (Objects.nonNull(taskNode.getParentNodeId())) {
			queryWrapper.eq(TaskNode::getParentNodeId, taskNode.getParentNodeId());
		}

		// 判断任务名称不为空
		if (Objects.nonNull(taskNode.getNodeName())) {
			queryWrapper.like(TaskNode::getNodeName, taskNode.getNodeName());
		}

		// 判断子节点进度不为空
		if (Objects.nonNull(taskNode.getProcess())) {
			queryWrapper.eq(TaskNode::getProcess, taskNode.getProcess());
		}

		// 判断增加进度不为空
		if (Objects.nonNull(taskNode.getAddProcess())) {
			queryWrapper.eq(TaskNode::getAddProcess, taskNode.getAddProcess());
		}

		// 判断子节点最终进度不为空
		if (Objects.nonNull(taskNode.getFinalProcess())) {
			queryWrapper.eq(TaskNode::getFinalProcess, taskNode.getFinalProcess());
		}

		// 判断提交状态不为空
		if (Objects.nonNull(taskNode.getSubmissionStatus())) {
			queryWrapper.eq(TaskNode::getSubmissionStatus, taskNode.getSubmissionStatus());
		}

		// 判断任务属性不为空
		if (Objects.nonNull(taskNode.getNodeAttributes())) {
			queryWrapper.eq(TaskNode::getNodeAttributes, taskNode.getNodeAttributes());
		}

		// 判断任务是否跳过不为空
		if (Objects.nonNull(taskNode.getCanSkip())) {
			queryWrapper.eq(TaskNode::getCanSkip, taskNode.getCanSkip());
		}

		// 判断任务流部署id不为空
		if (Objects.nonNull(taskNode.getActDeploymentId())) {
			queryWrapper.eq(TaskNode::getActDeploymentId, taskNode.getActDeploymentId());
		}

		// 判断任务流部署id不为空
		if (Objects.nonNull(taskNode.getActReProcdefId())) {
			queryWrapper.eq(TaskNode::getActReProcdefId, taskNode.getActReProcdefId());
		}

		// 判断流程实例id不为空
		if (Objects.nonNull(taskNode.getActTaskId())) {
			queryWrapper.eq(TaskNode::getActTaskId, taskNode.getActTaskId());
		}

		// 判断是否需要审核不为空
		if (Objects.nonNull(taskNode.getNeedAudit())) {
			queryWrapper.eq(TaskNode::getNeedAudit, taskNode.getNeedAudit());
		}

		// 判断是否需要计费不为空
		if (Objects.nonNull(taskNode.getNeedCost())) {
			queryWrapper.eq(TaskNode::getNeedCost, taskNode.getNeedCost());
		}

		// 判断机构类型不为空
		if (Objects.nonNull(taskNode.getInsType())) {
			queryWrapper.eq(TaskNode::getInsType, taskNode.getInsType());
		}

		// 判断备注不为空
		if (Objects.nonNull(taskNode.getRemark())) {
			queryWrapper.like(TaskNode::getRemark, taskNode.getRemark());
		}

		// 判断节点计费不为空
		if (Objects.nonNull(taskNode.getCharge())) {
			queryWrapper.eq(TaskNode::getCharge, taskNode.getCharge());
		}

		// 判断节点分值不为空
		if (Objects.nonNull(taskNode.getScore())) {
			queryWrapper.eq(TaskNode::getScore, taskNode.getScore());
		}

		// 判断用于节点排序不为空
		if (Objects.nonNull(taskNode.getSort())) {
			queryWrapper.eq(TaskNode::getSort, taskNode.getSort());
		}

		queryWrapper.eq(TaskNode::getDelFlag, 0);

		List<TaskNode> nodeList = this.list(queryWrapper);

		return nodeList;
	}

	/**
	 * 根据案件id与节点key查询任务对象
	 *
	 * @param caseeId 案件id
	 * @param nodeKey 节点key
	 * @return
	 */
	@Override
	public TaskNode queryNodeTaskByCaseeIdAndNodeKey(Integer caseeId, String nodeKey) {
		return this.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getCaseeId, caseeId).eq(TaskNode::getNodeKey, nodeKey));
	}

	@Override
	public void updateTargetProcessJson(Integer process, Integer finalProcess, String nodeKey, Integer targetId) {
		AuditTargetDTO auditTargetDTO = new AuditTargetDTO();
		auditTargetDTO.setFinalProcess(finalProcess);
		auditTargetDTO.setProcess(process);
		auditTargetDTO.setKey(nodeKey);
		auditTargetDTO.setTargetId(targetId);
		auditTargetDTO.setFormData("{\"finalProcess\":" + finalProcess + ",\"process\":" + process + "}");

		if (nodeKey.equals("$")) {//判断是否是第一层节点
			com.alibaba.fastjson.JSONObject formData = com.alibaba.fastjson.JSONObject.parseObject(auditTargetDTO.getFormData());
			List<KeyValue> listParams = new ArrayList<KeyValue>();

			for (String o : formData.keySet()) {
				listParams.add(new KeyValue(auditTargetDTO.getKey() + "." + o, formData.get(o)));
			}
			//修改标的json数据进度
			targetMapper.updateBusinessData(auditTargetDTO, listParams);

		} else {
			this.updateBusinessData(auditTargetDTO);
		}
	}

	@Override
	public void setNodeTaskMakeUpProcess(TaskNode taskNode) {
		TaskNode threeLevelParentTaskNode = queryParentTaskNode(taskNode, 3);
		if (threeLevelParentTaskNode.getProcess() > taskNode.getNodeOrder()) {

			//查询第二层节点信息
			TaskNode twoLevelParentTaskNode = this.queryParentTaskNode(taskNode, 2);

			if (threeLevelParentTaskNode.getFinalProcess() == taskNode.getNodeOrder()) {

				if (twoLevelParentTaskNode.getProcess() > threeLevelParentTaskNode.getNodeOrder()) {
					//将第三层顺序复制给第二层父节点
					twoLevelParentTaskNode.setProcess(threeLevelParentTaskNode.getNodeOrder());

					//更新第二层节点
					this.updateById(twoLevelParentTaskNode);

					updateTwoTargetProcessJson(taskNode, twoLevelParentTaskNode);

				}
			}
		}
	}

	/**
	 * 任务数据提交 保存程序、财产和行为
	 *
	 * @param taskNode
	 */
	@Override
	public void setTaskDataSubmission(TaskNode taskNode) {
		if (taskNode.getNeedAudit() == 0) {
			Target target = this.targetService.getById(taskNode.getTargetId());
			//更新程序表json
			AuditTargetDTO auditTargetDTO = this.getAuditTargetDTO(taskNode);


			this.updateBusinessData(auditTargetDTO);
			//判断如果财产或行为将他们存入json字段中
			if (target.getGoalType().equals(Integer.valueOf("20001"))) {

				AssetsRe assetsRe = new AssetsRe();
				assetsRe.setAssetsId(target.getGoalId());
				assetsRe.setCaseeId(target.getCaseeId());
				assetsRe.setProjectId(taskNode.getProjectId());

				AssetsReLiqui assetsReLiqui = this.assetsReCaseeService.getAssetsReCasee(assetsRe);

				AssetsReCaseeDetail assetsReCaseeDetail = assetsReLiqui.getAssetsReCaseeDetail();
				if (Objects.isNull(assetsReCaseeDetail)) {
					assetsReCaseeDetail = new AssetsReCaseeDetail();
				}

				if (taskNode.getNodeKey().equals("fundingZX_ZJZX_ZJZXDJ_ZJZXDJ")) {
					//资金资产冻结实体
					AssetsFreeze assetsFreeze = JsonUtils.jsonToPojo(taskNode.getFormData(), AssetsFreeze.class);

					assetsReCaseeDetail.setAssetsFreeze(assetsFreeze);

					assetsReLiqui.setAssetsReCaseeDetail(assetsReCaseeDetail);

				} else if (taskNode.getNodeKey().equals("fundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK")) {
					//资金财产划扣
					AssetsSnap assetsSnap = JsonUtils.jsonToPojo(taskNode.getFormData(), AssetsSnap.class);

					assetsReCaseeDetail.setAssetsSnap(assetsSnap);

					assetsReLiqui.setAssetsReCaseeDetail(assetsReCaseeDetail);
				} else if (taskNode.getNodeKey().equals("entityZX_STZX_CCZXCF_CCZXCF")) {
					//实体财产查封
					AssetsSeizure assetsSeizure = JsonUtils.jsonToPojo(taskNode.getFormData(), AssetsSeizure.class);

					assetsReCaseeDetail.setAssetsSeizure(assetsSeizure);

					assetsReLiqui.setAssetsReCaseeDetail(assetsReCaseeDetail);

					//实体财产到款实体类
				} else if (taskNode.getNodeKey().equals("entityZX_STZX_CCZXDK_CCZXDK")) {
					AssetsPayment assetsPayment = JsonUtils.jsonToPojo(taskNode.getFormData(), AssetsPayment.class);

					assetsReCaseeDetail.setAssetsPayment(assetsPayment);

					assetsReLiqui.setAssetsReCaseeDetail(assetsReCaseeDetail);
					//资产抵偿
				}else if (taskNode.getNodeKey().equals("entityZX_STZX_CCZXZCDZ_CCZXZCDZ")) {
					AssetsAssetCompensate assetsAssetCompensate = JsonUtils.jsonToPojo(taskNode.getFormData(), AssetsAssetCompensate.class);

					assetsReCaseeDetail.setAssetsAssetCompensate(assetsAssetCompensate);

					assetsReLiqui.setAssetsReCaseeDetail(assetsReCaseeDetail);

					//实体财产成交裁定
				} else if (taskNode.getNodeKey().equals("entityZX_STZX_CCZXCJCD_CCZXCJCD")) {
					AssetsCompletionRuling assetsCompletionRuling = JsonUtils.jsonToPojo(taskNode.getFormData(), AssetsCompletionRuling.class);

					assetsReCaseeDetail.setAssetsCompletionRuling(assetsCompletionRuling);

					assetsReLiqui.setAssetsReCaseeDetail(assetsReCaseeDetail);

					//实体财产抵偿裁定
				} else if (taskNode.getNodeKey().equals("entityZX_STZX_CCZXDCCD_CCZXDCCD")) {
					AssetsCompensationAward assetsCompensationAward = JsonUtils.jsonToPojo(taskNode.getFormData(), AssetsCompensationAward.class);

					assetsReCaseeDetail.setAssetsCompensationAward(assetsCompensationAward);

					assetsReLiqui.setAssetsReCaseeDetail(assetsReCaseeDetail);
				}
				this.assetsReCaseeService.updateById(assetsReLiqui);
			} else if (target.getGoalType().equals(Integer.valueOf("30001"))) {

				Behavior behavior = new Behavior();
				behavior.setBehaviorId(target.getGoalId());
				behavior.setCaseeId(target.getCaseeId());

				BehaviorLiqui behaviorLiqui = this.BehaviorLiquiService.getBehaviorLiqui(behavior);

				BehaviorLiquiDetail behaviorLiquiDetail = behaviorLiqui.getBehaviorLiquiDetail();
				if (Objects.isNull(behaviorLiquiDetail)) {
					behaviorLiquiDetail = new BehaviorLiquiDetail();
				}

				if (taskNode.getNodeKey().equals("beIllegal_XWWF_XWWFSDQK_XWWFSDQK")) {
					//行为违法送达情况

					BehaviorIllegalServedSituation behaviorIllegalServedSituation = JsonUtils.jsonToPojo(taskNode.getFormData(), BehaviorIllegalServedSituation.class);

					behaviorLiquiDetail.setBehaviorIllegalServedSituation(behaviorIllegalServedSituation);

					behaviorLiqui.setBehaviorLiquiDetail(behaviorLiquiDetail);

				} else if (taskNode.getNodeKey().equals("beIllegal_XWWF_XWWFXZCX_XWWFXZCX")) {
					//行为违法限制撤销

					BehaviorIllegalRevoke behaviorIllegalRevoke = JsonUtils.jsonToPojo(taskNode.getFormData(), BehaviorIllegalRevoke.class);

					behaviorLiquiDetail.setBehaviorIllegalRevoke(behaviorIllegalRevoke);

					behaviorLiqui.setBehaviorLiquiDetail(behaviorLiquiDetail);

				} else if (taskNode.getNodeKey().equals("beIllegal_XWWF_XWWFXZCXSDQK_XWWFXZCXSDQK")) {
					//行为违法限制撤销送达情况

					BehaviorIllegalRevokeServedSituation behaviorIllegalRevokeServedSituation = JsonUtils.jsonToPojo(taskNode.getFormData(), BehaviorIllegalRevokeServedSituation.class);

					behaviorLiquiDetail.setBehaviorIllegalRevokeServedSituation(behaviorIllegalRevokeServedSituation);

					behaviorLiqui.setBehaviorLiquiDetail(behaviorLiquiDetail);

				} else if (taskNode.getNodeKey().equals("beIllegal_XWWF_SSXWWF_SSXWWF")) {
					//行为违法实施行为违法

					BehaviorIllegalCommittingAnIllegalAct behaviorIllegalCommittingAnIllegalAct = JsonUtils.jsonToPojo(taskNode.getFormData(), BehaviorIllegalCommittingAnIllegalAct.class);

					behaviorLiquiDetail.setBehaviorIllegalCommittingAnIllegalAct(behaviorIllegalCommittingAnIllegalAct);

					behaviorLiqui.setBehaviorLiquiDetail(behaviorLiquiDetail);

				} else if (taskNode.getNodeKey().equals("beIllegal_XWWF_SSXWWFSDQK_SSXWWFSDQK")) {
					//行为违法实施行为违法送达情况

					BehaviorIllegalCommittingAnIllegalActServedSituation behaviorIllegalCommittingAnIllegalActServedSituation = JsonUtils.jsonToPojo(taskNode.getFormData(), BehaviorIllegalCommittingAnIllegalActServedSituation.class);

					behaviorLiquiDetail.setBehaviorIllegalCommittingAnIllegalActServedSituation(behaviorIllegalCommittingAnIllegalActServedSituation);

					behaviorLiqui.setBehaviorLiquiDetail(behaviorLiquiDetail);

				} else if (taskNode.getNodeKey().equals("limit_XWXZ_XWXZSDQK_XWXZSDQK")) {

					BehaviorRestrictionsServedSituation behaviorRestrictionsServedSituation = JsonUtils.jsonToPojo(taskNode.getFormData(), BehaviorRestrictionsServedSituation.class);

					behaviorLiquiDetail.setBehaviorRestrictionsServedSituation(behaviorRestrictionsServedSituation);

					behaviorLiqui.setBehaviorLiquiDetail(behaviorLiquiDetail);

				} else if (taskNode.getNodeKey().equals("limit_XWXZ_XWXZXZCX_XWXZXZCX")) {

					BehaviorRestrictionsRevoke behaviorRestrictionsRevoke = JsonUtils.jsonToPojo(taskNode.getFormData(), BehaviorRestrictionsRevoke.class);

					behaviorLiquiDetail.setBehaviorRestrictionsRevoke(behaviorRestrictionsRevoke);

					behaviorLiqui.setBehaviorLiquiDetail(behaviorLiquiDetail);

				} else if (taskNode.getNodeKey().equals("limit_XWXZ_XWXZXZCXSDQK_XWXZXZCXSDQK")) {

					BehaviorRestrictionsRevokeServedSituation behaviorRestrictionsRevokeServedSituation = JsonUtils.jsonToPojo(taskNode.getFormData(), BehaviorRestrictionsRevokeServedSituation.class);

					behaviorLiquiDetail.setBehaviorRestrictionsRevokeServedSituation(behaviorRestrictionsRevokeServedSituation);

					behaviorLiqui.setBehaviorLiquiDetail(behaviorLiquiDetail);

				} else {
					new RuntimeException("没有对应的行为实体类型，行为详情录入异常！");
				}

				this.BehaviorLiquiService.updateById(behaviorLiqui);
			}
		}
	}

	/**
	 * 更新最终送达时间
	 *
	 * @param taskNode
	 * @param receiptRecordList
	 */
	public void updateFinalReceiptTimeOrEffectiveDate(TaskNode taskNode, List<ReceiptRecord> receiptRecordList, Date effectiveDate) {

		Date date = new Date();

		for (int i = 0; i < receiptRecordList.size(); i++) {
			if (i == 0) {
				date = receiptRecordList.get(i).getFinalReceiptTime();
			} else {
				if (receiptRecordList.get(i).getFinalReceiptTime().compareTo(date) > 0) {
					date = receiptRecordList.get(i).getFinalReceiptTime();
				}
			}
		}

		//查询案件与案件详情
		Casee queryCasee = new Casee();
		queryCasee.setCaseeId(taskNode.getCaseeId());
		CaseeLiqui caseeLiqui = caseeLiquiService.getCaseeLiqui(queryCasee);
		CaseeLiquiDetail caseeLiquiDetail = caseeLiqui.getCaseeLiquiDetail();

		//案件详情为空创建
		if (Objects.isNull(caseeLiquiDetail)) {
			caseeLiquiDetail = new CaseeLiquiDetail();
		}

		//最终送达时间
		caseeLiquiDetail.setFinalReceiptTime(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		//裁判结果生效日期
		if(Objects.nonNull(effectiveDate)) {
			caseeLiquiDetail.setEffectiveDate(effectiveDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}

		caseeLiqui.setCaseeLiquiDetail(caseeLiquiDetail);

		caseeLiquiService.updateById(caseeLiqui);
	}

	public void updateTwoTargetProcessJson(TaskNode taskNode, TaskNode twoLevelParentTaskNode) {
		TaskNode twoLeveTaskNode = new TaskNode();

		//获取第二层节点key
		twoLeveTaskNode.setNodeKey(twoLevelParentTaskNode.getNodeKey());

		String twoLevelKey = this.getKey(twoLeveTaskNode);

		//更新标的json第二层节点进度数据
		this.updateTargetProcessJson(twoLevelParentTaskNode.getProcess(), twoLevelParentTaskNode.getFinalProcess(), twoLevelKey, taskNode.getTargetId());
	}

	/**
	 * 查询主任务进度
	 *
	 * @param list
	 * @return
	 */
	private Map<String, Object> getTaskProgress(List<TaskNode> list) {
		Map<String, Object> taskMap = new HashMap<>();
		for (TaskNode taskNode : list) {
			if (taskNode.getNodeName().equals("主任务")) {
				taskMap.put("taskProgress", taskNode.getProcess());
				taskMap.put("id", taskNode.getNodeId());
			}
		}
		return taskMap;
	}

	/**
	 * 递归添加节点数据
	 * taskNode 				当前节点
	 * id		  				节点id
	 * parentNode				当前节点父节点信息
	 * resTaskNodes			保存节点集合
	 * caseePerformanceList	保存绩效信息集合
	 * taskNodeTemplateDTO		模板信息
	 * jsonObject				清收或拍辅json对象 存储表单id和节点名称
	 */
	public void genNode(TaskNodeDTO taskNode, String id, TaskNodeDTO parentNode, List<TaskNodeDTO> resTaskNodes, List<CaseePerformance> caseePerformanceList, TaskNodeTemplateDTO taskNodeTemplateDTO, JSONObject jsonObject) {
		taskNode.setNodeId(id);
		JSONObject json = jsonObject.getJSONObject(taskNode.getNodeKey());
		if (json.size() > 0) {
			if (taskNode.getFormId() != null) {
				json.put("formId", taskNode.getFormId());
			}
			json.put("nodeOrder", taskNode.getNodeOrder());
			json.put("nodeName", taskNode.getNodeName());
		}
		BeanCopyUtil.copyBean(taskNodeTemplateDTO, taskNode);

		if (taskNode.getNeedCost() == 1) {//节点如果计费的话添加到绩效集合中
			CaseePerformance caseePerformance = new CaseePerformance();
			BeanCopyUtil.copyBean(taskNodeTemplateDTO, caseePerformance);
			caseePerformance.setNodeId(id);
			caseePerformance.setPerfAmount(taskNode.getCharge());
			caseePerformance.setPerfScore(taskNode.getScore());
			caseePerformanceList.add(caseePerformance);
		}
		if (null != parentNode) {//添加父节点id
			taskNode.setParentNodeId(parentNode.getNodeId());
		}

		taskNode.setSort(sum++);
		resTaskNodes.add(taskNode);
		for (int i = 0; i < taskNode.getChildren().size(); i++) {//递归
			taskNode.getChildren().get(i).setActDeploymentId(taskNode.getActDeploymentId());
			if (json.size() > 0) {
				this.genNode(taskNode.getChildren().get(i), id + "_" + i, taskNode, resTaskNodes, caseePerformanceList, taskNodeTemplateDTO, json);
			} else {
				this.genNode(taskNode.getChildren().get(i), id + "_" + i, taskNode, resTaskNodes, caseePerformanceList, taskNodeTemplateDTO, jsonObject);
			}
		}
	}

	public void genNodeFromTemplate(TaskNodeTemplate taskNodeTemplate, TaskNodeTemplateDTO taskNodeTemplateDTO, JSONObject jsonObject) {
		//将模板内容转换成TaskNodeDTO集合
		List<TaskNodeDTO> taskNodes = JsonUtils.jsonToList(taskNodeTemplate.getTemplateContent(), TaskNodeDTO.class);
		//节点集合
		List resTaskNodes = new ArrayList<>();
		//案件绩效集合
		List<CaseePerformance> caseePerformanceList = new ArrayList<>();

		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到repositoryService实例
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//查询清收任务办理流程部署信息
		Deployment deployment = repositoryService.createDeploymentQuery().deploymentName("审批流程").singleResult();

		//循环遍历模板内容
		for (int i = 0; i < taskNodes.size(); i++) {
			//添加流程部署id
			taskNodes.get(i).setActDeploymentId(deployment.getId());
			this.genNode(taskNodes.get(i), taskNodeTemplateDTO.getTargetId().toString() + i, null, resTaskNodes, caseePerformanceList, taskNodeTemplateDTO, jsonObject);
		}
		caseePerformanceService.saveBatch(caseePerformanceList);
		this.saveBatch(resTaskNodes);
	}

}
