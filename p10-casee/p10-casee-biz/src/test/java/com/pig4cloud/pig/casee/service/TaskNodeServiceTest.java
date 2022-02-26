package com.pig4cloud.pig.casee.service;

import com.pig4cloud.pig.admin.api.appserver.RequestAppService;
import com.pig4cloud.pig.admin.api.feign.RemoteAuthUtilsService;
import com.pig4cloud.pig.admin.api.feign.RemoteOutlesService;
import com.pig4cloud.pig.casee.dto.CaseeOrTargetTaskFlowDTO;
import com.pig4cloud.pig.casee.dto.TaskFlowDTO;
import com.pig4cloud.pig.casee.entity.PfzxTask;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.common.core.util.R;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskNodeServiceTest<T> {
	@Autowired
	private TaskNodeService taskNodeService;
	@Autowired
	private CaseePerformanceService caseePerformanceService;
	@Autowired
	private RemoteOutlesService remoteOutlesService;

	@Autowired
	RequestAppService requestAppService;

	@Autowired
	RemoteAuthUtilsService remoteAuthUtilsService;

	@Test
	public void test1() {
		List<Integer> a=new ArrayList<>();
		List<Integer> b=new ArrayList<>();
		b.add(1);
		b.add(2);
		b.add(3);
		a.addAll(b);
		System.out.println(a);


//		LiQuiSSES LiQuiSSES = new LiQuiSSES();
//
//		String jsonObject = JsonUtils.objectToJsonObject(LiQuiSSES);
////
//		System.out.println(jsonObject);

//		LiQuiSSES liQuiSSES = JsonUtils.jsonToPojo("{\"finalProcess\":0,\"formId\":\"\",\"liQui_SSES\":{\"finalProcess\":0,\"formId\":\"\",\"liQui_SSES_SSESCPJG\":{\"finalProcess\":0,\"formId\":\"\",\"liQui_SSES_SSESCPJG_SSESCPJG\":{\"effectiveDate\":null,\"effectivePerformancePeriod\":0,\"finalProcess\":0,\"formId\":\"\",\"instalmentFulfillmentRecordList\":[],\"litigationCosts\":0,\"mediationList\":[],\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0,\"refereeAmount\":0,\"refereeMediationResult\":0,\"refereeMediationTime\":null,\"refereeType\":0,\"remark\":\"\",\"revokedContent\":\"\",\"verdictList\":[],\"writList\":[]},\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0},\"liQui_SSES_SSESCPWSZZSDQK\":{\"finalProcess\":0,\"formId\":\"\",\"liQui_SSES_SSESCPWSZZSDQK_SSESCPWSZZSDQK\":{\"finalDeliveryRemark\":\"\",\"finalProcess\":0,\"formId\":\"\",\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0,\"receiptRecordList\":[]},\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0},\"liQui_SSES_SSESJACA\":{\"finalProcess\":0,\"formId\":\"\",\"liQui_SSES_SSESJACA_SSESJACA\":{\"caseClosedTime\":null,\"caseClosedType\":0,\"finalProcess\":0,\"formId\":\"\",\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0,\"remark\":\"\"},\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0},\"liQui_SSES_SSESJACASDQK\":{\"finalProcess\":0,\"formId\":\"\",\"liQui_SSES_SSESJACASDQK_SSESJACASDQK\":{\"deliveryDocumentNameList\":[],\"deliveryMethod\":0,\"deliveryResult\":0,\"deliveryTime\":null,\"executedNameList\":[],\"finalProcess\":0,\"formId\":\"\",\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0,\"remark\":\"\"},\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0},\"liQui_SSES_SSESLASDQK\":{\"finalProcess\":0,\"formId\":\"\",\"liQui_SSES_SSESLASDQK_SSESLASDQK\":{\"deliveryDocumentNameList\":[],\"deliveryMethod\":0,\"deliveryResult\":0,\"deliveryTime\":null,\"executedNameList\":[],\"finalProcess\":0,\"formId\":\"\",\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0,\"remark\":\"\"},\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0},\"liQui_SSES_SSESTSXX\":{\"finalProcess\":0,\"formId\":\"\",\"liQui_SSES_SSESTSXX_SSESTSXX\":{\"assistantJudge\":\"\",\"clerk\":\"\",\"courtSessionTime\":null,\"finalProcess\":0,\"formId\":\"\",\"nodeName\":\"\",\"nodeOrder\":0,\"otherPanelMembersList\":[],\"presidingJudge\":\"\",\"process\":0,\"remark\":\"\",\"tribunal\":\"\",\"turnUpTime\":null},\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0},\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0},\"nodeName\":\"\",\"nodeOrder\":0,\"process\":0}", LiQuiSSES.class);
//
//		System.out.println(liQuiSSES.toString());


//		JSONObject jsonObject = JSONObject.fromObject(liQui_sq);
//		System.out.println(jsonObject);


//		LiQui_LX liQui_lx=new LiQui_LX();
//
//		System.out.println(liQui_lxJsonObject);

//		LiQui liQui=new LiQui();
//		JSONObject jsonObject= JSONObject.fromObject(liQui);
//		JSONObject liQui_sq = jsonObject.getJSONObject("liQui_SQ");
//		liQui_sq.put("formId",1);
//		liQui_sq.put("nodeName","测试");
//		System.out.println(jsonObject.toString());
//		TaskNode taskNode=new TaskNode();
//		taskNode.setUpdateTime(LocalDateTime.now());
//		System.out.println(JsonUtils.objectToJson(taskNode));
//		List<Integer> list=new ArrayList<>();
//		list.add(86);
//		CaseeOrTargetTaskFlowDTO objectDTO=new CaseeOrTargetTaskFlowDTO();
//		objectDTO.setTaskKey("caseeOrTargetTaskFlow");
//		objectDTO.setActReProcdefId("1155019");
//		objectDTO.setCaseeOrTargetAuditProposer(86);
//		objectDTO.setCaseeOrTargetAuditorList(list);
//		objectDTO.setNodeId("CASEE_OPEN_183");
//		System.out.println("liQui_SQ_SQCS_SQCS".substring(0, StringUtils.ordinalIndexOf("liQui_SQ_SQCS_SQCS", "_", 2)));
//		ObjectTransitionEntityUtils objectTransitionEntityUtils=new ObjectTransitionEntityUtils();
//
//		try {
//			CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO=objectTransitionEntityUtils.readValueMap(objectDTO, CaseeOrTargetTaskFlowDTO.class);
//			System.out.println(caseeOrTargetTaskFlowDTO);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}


	}

	//1.流程定义的部署
	//重点！！！   一个流程定义可以对应多个流程实例。(一次部署可以多次定义流程实例)
	@Test
	public void testDeploy() {
//		taskNodeService.addDeployment("processes/caseeOrTargetTaskFlow.bpmn","案件标的审批流程");
//		taskNodeService.addDeployment("processes/taskFlow.bpmn","审批任务流程");

		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到repositoryService实例
		RepositoryService repositoryService = processEngine.getRepositoryService();

		//3，进行部署

		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("processes/task.bpmn") //添加bpmn资源
				.name("审批流程")
				.deploy();

		//4，输出部署的一些信息
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());

		Deployment deployment1 = repositoryService.createDeploymentQuery().deploymentName("审批流程").singleResult();

		System.out.println(deployment);

	}

	/**
	 * 推送消息测试
	 */
	@Test
	public void test9(){
//		for (int i = 0; i < 2; i++) {
//			R r = requestAppService.pushAppMessage("eabb390c45d49dae0a255b5dadf16c2a", "雪中悍刀行", "《雪中悍刀行》是由宋晓飞执导，张若昀、李庚希、胡军领衔主演，高伟光特别出演，张天爱特邀主演，刘端端、邱心志、田小洁、王天辰、李纯、丁笑滢、董颜、廖慧佳、孙雅丽、孟子义等联合主演的玄幻励志剧。");
//			System.out.println(r);
//			try {
//				Thread.currentThread().sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}

		List<String> list = new ArrayList<>();
		list.add("eabb390c45d49dae0a255b5dadf16c2a");
		list.add("eabb390c45d49dae0a255b5dadf16c2a");
		list.add("eabb390c45d49dae0a255b5dadf16c2a");

		R r = requestAppService.pushAppMessage(list, "雪中悍刀行", "《雪中悍刀行》是由宋晓飞执导，张若昀、李庚希、胡军领衔主演，高伟光特别出演，张天爱特邀主演，刘端端、邱心志、田小洁、王天辰、李纯、丁笑滢、董颜、廖慧佳、孙雅丽、孟子义等联合主演的玄幻励志剧。");

		System.out.println(r);

	}

	//2.启动一个实例,动态设置设置办理人信息：前提是已经完成了流程定义的部署工作
	//重点！！！   一个流程定义可以对应多个流程实例。(一次部署可以多次定义流程实例)
	@Test
	public void testBeginFlow() {//设置动态执行人负责人
		//设置assignee,map键对应配置中的变量名
		// 设置UEL-Value表达式中的值
		Map<String, Object> variables = new LinkedHashMap<>();
//		CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO = new CaseeOrTargetTaskFlowDTO();
////		caseeOrTargetTaskFlowDTO.setCaseeOrTargetAuditProposer(86);
//		List list=new ArrayList();
//		list.add(520);
//		list.add(521);
//		caseeOrTargetTaskFlowDTO.setCaseeOrTargetAuditorList(list);
//		caseeOrTargetTaskFlowDTO.setTaskKey("auditor");
//		variables.put("caseeOrTargetTaskFlowDTO", caseeOrTargetTaskFlowDTO);
		TaskFlowDTO taskFlowDTO = new TaskFlowDTO();
		taskFlowDTO.setTransactionId(118);
		taskFlowDTO.setTaskKey("taskFlow");
		variables.put("taskFlowDTO", taskFlowDTO);


		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到RuntimeService实例
		RuntimeService runtimeService = processEngine.getRuntimeService();
		//3，创建流程实例，流程定义的key需要知道流程唯一id：holiday
//		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(caseeOrTargetTaskFlowDTO.getTaskKey(), "1190_0_0_2", variables);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(taskFlowDTO.getTaskKey(), "1190_0_0_2", variables);
		//4，输出实例相关信息
		System.out.println("实例部署id：" + processInstance.getDeploymentId());
		System.out.println("流程实例id：" + processInstance.getId());
		System.out.println("活动id：" + processInstance.getActivityId());
		System.out.println("绑定业务id:" + processInstance.getBusinessKey());
	}


	@Test
	public void redify() throws Exception {
		//得到ProcessEngine
		ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
		//得到taskService
		TaskService taskService = defaultProcessEngine.getTaskService();

		//查询当前用户是否存在任务
		Map<String, Object> variables = new LinkedHashMap<>();

		List list=new ArrayList();
//		list.add(1314);
//		list.add(555);

		TaskFlowDTO taskFlowDTO = new TaskFlowDTO();
		taskFlowDTO.setTaskKey("taskFlow");
//		taskFlowDTO.setNeedCommission(0);
		taskFlowDTO.setNeedCommissionedReview(0);
//		taskFlowDTO.setTransactionId(86);
		taskFlowDTO.setAuditorIdList(list);
		variables.put("taskFlowDTO", taskFlowDTO);
		Task task = taskService.createTaskQuery().processDefinitionKey(taskFlowDTO.getTaskKey())
				.processInstanceId("1995001").taskName("正在处理").singleResult();


//		CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO = new CaseeOrTargetTaskFlowDTO();
//		caseeOrTargetTaskFlowDTO.setTaskKey("auditor");
//		caseeOrTargetTaskFlowDTO.setStatus(2);
//		caseeOrTargetTaskFlowDTO.setCaseeOrTargetAuditorList(list);
//		variables.put("caseeOrTargetTaskFlowDTO", caseeOrTargetTaskFlowDTO);
//		Task task = taskService.createTaskQuery().processDefinitionKey("caseeOrTargetTaskFlow")
//				.processInstanceId("1210001").taskAssignee("114").singleResult();
		//如果有任务，结束
		if (task != null) {
			taskService.complete(task.getId(), variables);
//			taskService.complete(task.getId());
			System.out.println("任务执行完毕");
		}
	}

	//查询组任务
	@Test
	public void finds() {
		ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = defaultProcessEngine.getTaskService();
		String key = "commonalityTaskFlow";//流程定义的key
		String candidate_users = "张新发";//指定某一个候选人
		//查询
		List<Task> list = taskService.createTaskQuery()
				.processDefinitionKey(key)
				.taskCandidateUser(candidate_users)//设置候选用户
				.list();
		//输出
		for (Task task : list) {
			System.out.println(task.getProcessInstanceId());
			System.out.println(task.getId());
			System.out.println(task.getName());
			System.out.println(task.getAssignee());
		}
	}

	//查询组任务，设置候选人
	@Test
	public void find() {
		ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = defaultProcessEngine.getTaskService();
		String key = "commonalityTaskFlow";//流程定义的key
		String candidate_users = "王老吉";//指定某一个候选人
		//查询
		Task task = taskService.createTaskQuery()
				.processDefinitionKey(key)
				.taskCandidateUser(candidate_users)
				.singleResult();
		if (task != null) {
			//第一个参数：任务id，第二个参数：候选人
			taskService.claim(task.getId(), candidate_users);
			System.out.println("任务拾取完毕");
		}
	}

	//查询当前用户的任务列表
	@Test
	public void query() {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到TaskService对象
		TaskService taskService = processEngine.getTaskService();
		//3，根据流程定义的key，负责人assignee来实现当前用户的任务列表查询
		String name="审核人";
		List<Task> list = taskService.createTaskQuery()
				.taskAssignee("87")
				.taskNameLike("%"+name)
				.list();
		//4，任务列表的展示
		for (Task task : list) {
			ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			TaskNode taskNode = taskNodeService.getById(processInstance.getBusinessKey());
			System.out.println(taskNode);
//			System.out.println("流程实例id：" + task.getProcessInstanceId());
//			System.out.println("任务id：" + task.getId());
//			System.out.println("任务负责人：" + task.getAssignee());
//			System.out.println("任务名称：" + task.getName());
//			System.out.println("任务id：" + processInstance.getBusinessKey());

		}
	}


	/**
	 * 获取流程变量数据
	 */
	@Test
	public void getVariableValues() {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

		TaskService taskService = processEngine.getTaskService(); // 任务Service
		String taskId = "187506";
		PfzxTask pfzxTask = (PfzxTask) taskService.getVariable(taskId, "data");
		System.out.println("任务对象：" + pfzxTask.getId() + "," + pfzxTask.getName() + "," + pfzxTask.getApproveType());
	}


	//3.处理任务
	@Test
	public void chuli() {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到TaskService对象
		TaskService taskService = processEngine.getTaskService();
		//3，处理任务，结合当前用户任务列表的查询操作，得到任务ID：47505
		taskService.complete("202506");
	}

	//流程定义查询
	@Test
	public void defined() {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到RepositoryService对象
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//3，得到ProcessDefinitionQuery对象，可以认为它就是一个查询器
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		//4，设置条件，并查询出当前的所有流程定义，查询条件：流程定义的key=holiday

		List<ProcessDefinition> holiday = processDefinitionQuery.processDefinitionKey("commonalityTaskFlow")
				.orderByProcessDefinitionVersion()  //设置排序方式，根据流程定义的版本号进行排序
				.desc()     //降序排列
				.list();

		//5，输出流程定义信息
		for (ProcessDefinition processDefinition : holiday) {
			System.out.println("流程定义ID：" + processDefinition.getId());
			System.out.println("流程定义名称：" + processDefinition.getName());
			System.out.println("流程定义的Key：" + processDefinition.getKey());
			System.out.println("流程定义的版本号：" + processDefinition.getVersion());
			System.out.println("流程部署的ID：" + processDefinition.getDeploymentId());
		}
	}

	@Test
	public void bang() {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到RuntimeService实例
		RuntimeService runtimeService = processEngine.getRuntimeService();
		//3，创建流程实例，流程定义的key需要知道流程唯一id：holiday
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday", "1001");
		System.out.println(processInstance.getBusinessKey());
	}


	//删除已经部署成功的流程定义。
	@Test
	public void del() {
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
		repositoryService.deleteDeployment("335001", true);
	}

	//保存资源文件到本地
	@Test
	public void save() throws IOException {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到RepositoryService对象
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//3，得到ProcessDefinitionQuery对象，可以认为它就是一个查询器
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		//4，设置查询条件
		processDefinitionQuery.processDefinitionKey("holiday");//参数是流程定义的key
		//5，执行查询操作，查询出想要的流程定义
		ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
		//6，通过流程定义信息，得到部署ID
		String deploymentId = processDefinition.getDeploymentId();

		//7，通过repositoryService的方法，实现读取图片信息及bpmn文件信息（输入流）
		//getResourceAsStream()方法的参数说明：第一个参数是部署id，第二个参数代表资源名称
		//processDefinition.getDiagramResourceName() 获取png图片资源的名称
		//processDefinition.getResourceName() 获取bpmn文件的名称
		InputStream pngIs = repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());
		InputStream bpmnIs = repositoryService.getResourceAsStream(deploymentId, processDefinition.getResourceName());

		//8，构建出OutputStream流
		OutputStream pngOs = new FileOutputStream("D:\\工作\\" + processDefinition.getDiagramResourceName());
		OutputStream bpmnOs = new FileOutputStream("D:\\工作\\" + processDefinition.getResourceName());

		//9，输入流、输出流的转换（commons-io.jar中的方法）
		IOUtils.copy(pngIs, pngOs);
		IOUtils.copy(bpmnIs, bpmnOs);

		//10，关闭流
		pngOs.close();
		bpmnOs.close();
		pngIs.close();
		bpmnIs.close();
	}


	//查询历史数据
	@Test
	public void select() {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到HistoryService对象
		HistoryService historyService = processEngine.getHistoryService();
		//3，得到查询器
		HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
		//4，执行查询
		instanceQuery.processInstanceId("822501"); //设置流程实例的id
		instanceQuery.activityName("审核人");
		instanceQuery.taskAssignee("86");
		List<HistoricActivityInstance> list = instanceQuery.orderByHistoricActivityInstanceStartTime().asc().list();//排序
		//5，遍历查询结果
		for (HistoricActivityInstance instance : list) {
			System.out.println(instance.getActivityId());
			System.out.println(instance.getActivityName());
			System.out.println(instance.getProcessDefinitionId());
			System.out.println(instance.getProcessInstanceId());
			System.out.println("===========================");
		}
	}

	//流程定义全部挂起
	// 需求：公司制度发生了变化，原本没有批完的流程怎么办？
	//	某些情况可能由于流程变更需要当前运行的流程暂停而不是直接删除，流程暂停后将不会继续执行。
	//  操作流程定义为挂起状态，该流程定义下边所有的流程实例全部暂停，并不允许启动新的流程实例。
	@Test
	public void guaqi() {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到RepositoryService对象
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//3，查询流程定义的对象
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				//可以根据流程定义的id查询指定流程定义，不过id要到Activiti数据库表中去看，			act_re_procdef表
				//  .processDefinitionId()
				.processDefinitionKey("holiday") //也可以根据流程定义的key查询
				.singleResult();
		//4，得到当前流程定义的实例是否都为暂停状态
		boolean suspended = processDefinition.isSuspended();
		//5，如果都为暂停状态，则全部激活
		if (suspended) {
			repositoryService.activateProcessDefinitionById(processDefinition.getId(), true, null);
			System.out.println("流程定义：" + processDefinition.getId() + "被激活");
		} else {
			repositoryService.suspendProcessDefinitionById(processDefinition.getId(), true, null);
			System.out.println("流程定义：" + processDefinition.getId() + "被挂起");
		}
	}

	//Activiti单个流程实例的挂起与激活
	//单个流程实例的挂起
	@Test
	public void single() {
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到RuntimeService对象
		RuntimeService runtimeService = processEngine.getRuntimeService();
		//3，查询流程实例对象
		ProcessInstance holiday = runtimeService.createProcessInstanceQuery().processInstanceId("52501").singleResult();
		//4，查看当前流程定义的实例是否为暂停状态
		boolean suspended = holiday.isSuspended();
		//判断
		if (suspended) {
			runtimeService.activateProcessInstanceById(holiday.getId());
			System.out.println("流程实例：" + holiday.getId() + "被激活");
		} else {
			runtimeService.suspendProcessInstanceById(holiday.getId());
			System.out.println("流程实例：" + holiday.getId() + "被挂起");
		}
	}

	/**
	 * 退回到上一节点
	 *
	 * @param task 当前任务
	 */
	public void backProcess(Task task, Map<String, Object> variables) throws Exception {
		String processInstanceId = task.getProcessInstanceId();
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		HistoryService historyService = processEngine.getHistoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();

		// 取得所有历史任务按时间降序排序
		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByTaskCreateTime()
				.desc()
				.list();
		if (ObjectUtils.isEmpty(htiList) || htiList.size() < 2) {
			return;
		}
		// list里的第二条代表上一个任务
		HistoricTaskInstance lastTask = htiList.get(1);
		// list里第二条代表当前任务
		HistoricTaskInstance curTask = htiList.get(0);
		// 当前节点的executionId
		String curExecutionId = curTask.getExecutionId();
		// 上个节点的taskId
		String lastTaskId = lastTask.getId();
		// 上个节点的executionId
		String lastExecutionId = lastTask.getExecutionId();
		if (null == lastTaskId) {
			throw new Exception("LAST TASK IS NULL");
		}
		String processDefinitionId = lastTask.getProcessDefinitionId();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		String lastActivityId = null;
		List<HistoricActivityInstance> haiFinishedList = historyService.createHistoricActivityInstanceQuery()
				.executionId(lastExecutionId).finished().list();
		for (HistoricActivityInstance hai : haiFinishedList) {
			if (lastTaskId.equals(hai.getTaskId())) {
				// 得到ActivityId，只有HistoricActivityInstance对象里才有此方法
				lastActivityId = hai.getActivityId();
				break;
			}
		}
		// 得到上个节点的信息
		FlowNode lastFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(lastActivityId);
		// 取得当前节点的信息
		Execution execution = runtimeService.createExecutionQuery().executionId(curExecutionId).singleResult();
		String curActivityId = execution.getActivityId();
		FlowNode curFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(curActivityId);
		//记录当前节点的原活动方向
		List<SequenceFlow> oriSequenceFlows = new ArrayList<>();
		oriSequenceFlows.addAll(curFlowNode.getOutgoingFlows());
		//清理活动方向
		curFlowNode.getOutgoingFlows().clear();
		//建立新方向
		List<SequenceFlow> newSequenceFlowList = new ArrayList<>();
		SequenceFlow newSequenceFlow = new SequenceFlow();
		newSequenceFlow.setId("123");
		newSequenceFlow.setSourceFlowElement(curFlowNode);
		newSequenceFlow.setTargetFlowElement(lastFlowNode);
		newSequenceFlowList.add(newSequenceFlow);
		curFlowNode.setOutgoingFlows(newSequenceFlowList);
		// 完成任务
		taskService.complete(task.getId(), variables);
		//恢复原方向
		curFlowNode.setOutgoingFlows(oriSequenceFlows);
		Task nextTask = taskService
				.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		// 设置执行人
		if (nextTask != null) {
			taskService.setAssignee(nextTask.getId(), lastTask.getAssignee());
		}
	}
}

