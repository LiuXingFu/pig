package com.pig4cloud.pig.casee.nodehandler;

import com.pig4cloud.pig.casee.dto.TaskFlowDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.nodehandler.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class NodeTaskHandlerRegister {

//	@Autowired
//	PAIFU_JGRZ_SFYJ_YJJG_NODEHandler paifu_jgrz_sfyj_yjjg_nodeHandler;
//
//	@Autowired
//	PAIFU_JGRZ_DXXJ_XJJG_NODEHandler paifu_jgrz_dxxj_xjjg_nodeHandler;
//
//	@Autowired
//	PAIFU_JGRZ_WLXJ_XTLR_NODEHandler paifu_jgrz_wlxj_xtlr_nodeHandler;
//
//	@Autowired
//	PAIFU_JGRZ_PGDJ_XTLR_NODEHandler paifu_jgrz_pgdj_xtlr_nodeHandler;
//
//	@Autowired
//	PAIFU_PMFZ_PMJGYP_PMJG_NODEHandler paifu_pmfz_pmjgyp_pmjg_nodeHandler;
//
//	@Autowired
//	LIQUI_LX_ZQCS_ZQCS_NODEHandler liqui_lx_zqcs_zqcs_nodeHandler;

	@Autowired
	PAIFUGENERALLY_NODEHandler paiFuGenerallyNodeHandler;

	@Autowired
	LIQUIGENERALLY_NODEHandler liQuiGenerallyNodeHandler;

//	@Autowired
//	lIQUI_CASEE_REPAYMENTDETAILS_NODEHandler liqui_casee_repaymentdetails_nodeHandler;

	@Autowired
	LIQUI_SQ_SQBQJG_SQBQJG_NODEHandler liqui_sq_sqbqjg_sqbqjg_nodeHandler;

	@Autowired
	LIQUI_SSYS_SSYSCPJG_SSYSCPJG_NODEHandler liqui_ssys_ssyscpjg_ssyscpjg_nodeHandler;

	@Autowired
	LIQUI_SSBQ_SSBQBQJG_SSBQBQJG_NODEHandler liqui_ssbq_ssbqbqjg_ssbqbqjg_nodeHandler;

	@Autowired
	LIQUI_SSES_SSESCPJG_SSESCPJG_NODEHandler liqui_sses_ssescpjg_ssescpjg_nodeHandler;

	@Autowired
	LIQUI_SSQT_SSQTCPJG_SSQTCPJG_NODEHandler liqui_ssqt_ssqtcpjg_ssqtcpjg_nodeHandler;

	@Autowired
	LIQUI_LX_LXSCZXLA_LXSCZXLA_NODEHandler liqui_lx_lxsczxla_lxsczxla_nodeHandler;

	@Autowired
	BEILLEGAL_XWWF_XWWFXZCX_XWWFXZCX_NODEHandler beillegal_xwwf_xwwfxzcx_xwwfxzcx_nodeHandler;

	@Autowired
	LIQUI_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK_NODEHandler liqui_ssys_ssyscpwszzsdqk_ssyscpwszzsdqk_nodeHandler;

	@Autowired
	LIQUI_SSES_SSESCPWSZZSDQK_SSESCPWSZZSDQK_NODEHandler liqui_sses_ssescpwszzsdqk_ssescpwszzsdqk_nodeHandler;

	@Autowired
	LIQUI_SSQT_SSQTCPWSZZSDQK_SSQTCPWSZZSDQK_NODEHandler liqui_ssqt_ssqtcpwszzsdqk_ssqtcpwszzsdqk_nodeHandler;

	@Autowired
	ENTITYZX_STZX_CCZXPMJG_CCZXPMJG_NODEHandler entityzx_stzx_cczxpmjg_cczxpmjg_nodeHandler;

	@Autowired
	limit_XWXZ_XWXZXZCX_XWXZXZCX_NODEHandler limit_xwxz_xwxzxzcx_xwxzxzcx_nodeHandler;

	@Autowired
	LiQui_SSQT_SSQTCPJGSX_SSQTCPJGSX_NODEHandler liQui_ssqt_ssqtcpjgsx_ssqtcpjgsx_nodeHandler;

	@Autowired
	FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK_NODEHandler fundingZX_zjzx_zjzxzjhk_zjzxzjhk_nodeHandler;

	@Autowired
	EntityZX_STZX_CCZXZCDC_CCZXZCDC_NODEHandler entityZX_stzx_cczxzcdc_cczxzcdc_nodeHandler;

	@Autowired
	EntityZX_STZX_CCZXDK_CCZXDK_NODEHandler entityZX_stzx_cczxdk_cczxdk_nodeHandler;

	@Autowired
	EntityZX_STZX_CCZXJGYJ_CCZXJGYJ_NODEHandler entityZX_stzx_cczxjgyj_cczxjgyj_nodeHandler;

	@Autowired
	EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ_NODEHandler entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ_NODEHandler;

	@Autowired
	PaiFu_STCC_PMGG_PMGG_NODEHandler paiFu_stcc_pmgg_pmgg_nodeHandler;

	@Autowired
	PaiFu_STCC_PMJG_PMJG_NODEHandler paiFu_stcc_pmjg_pmjg_nodeHandler;

	@Autowired
	PaiFu_STCC_JSZX_JSZX_NODEHandler paiFu_stcc_jszx_jszx_nodeHandler;

	@Autowired
	PaiFu_STCC_BMKY_BMKY_NODEHandler paiFu_stcc_bmky_bmky_nodeHandler;

	@Autowired
	PaiFu_STCC_KYZBGZ_KYZBGZ_NODEHandler paiFu_stcc_kyzbgz_kyzbgz_nodeHandler;

	@Autowired
	PaiFu_STCC_YLKY_YLKY_NODEHandler paiFu_stcc_ylky_ylky_nodeHandler;

	@Autowired
	PaiFu_STCC_PMJGSDQK_PMJGSDQK_NODEHandler paiFu_stcc_pmjgsdqk_pmjgsdqk_nodeHandler;

	@Autowired
	PaiFu_STCC_DK_DK_NODEHandler paiFu_stcc_dk_dk_nodeHandler;

	@Autowired
	PaiFu_STCC_ZCDC_ZCDC_NODEHandler paiFu_stcc_zcdc_zcdc_nodeHandler;

	// 任务节点提交节点map
	private Map<String, TaskNodeHandler> submitHandlerMap = new HashMap<>();

	// 任务节点审核节点map
	private Map<String, TaskNodeHandler> auditHandlerMap = new HashMap<>();

	// 任务节点补录节点map
	private Map<String, TaskNodeHandler> makeUpHandlerMap = new HashMap<>();

	//初始方法
	@PostConstruct
	public void init() {
		// 拍辅任务节点提交map实现类
//		submitHandlerMap.put("paiFu_JGRZ_SFYJ_YJJG", paifu_jgrz_sfyj_yjjg_nodeHandler);
//		submitHandlerMap.put("paiFu_JGRZ_DXXJ_XJJG", paifu_jgrz_dxxj_xjjg_nodeHandler);
//		submitHandlerMap.put("paiFu_JGRZ_WLXJ_XTLR", paifu_jgrz_wlxj_xtlr_nodeHandler);
//		submitHandlerMap.put("paiFu_JGRZ_PGDJ_XTLR", paifu_jgrz_pgdj_xtlr_nodeHandler);
//		submitHandlerMap.put("paiFu_PMFZ_PMJGYP_PMJG", paifu_pmfz_pmjgyp_pmjg_nodeHandler);
//		submitHandlerMap.put("liQui_LX_ZQCS_ZQCS", liqui_lx_zqcs_zqcs_nodeHandler);

		//清收任务阶段提交map实现类
		submitHandlerMap.put("liQui_SQ_SQBQJG_SQBQJG", liqui_sq_sqbqjg_sqbqjg_nodeHandler);
		submitHandlerMap.put("liQui_SSYS_SSYSCPJG_SSYSCPJG", liqui_ssys_ssyscpjg_ssyscpjg_nodeHandler);
		submitHandlerMap.put("liQui_SSBQ_SSBQBQJG_SSBQBQJG", liqui_ssbq_ssbqbqjg_ssbqbqjg_nodeHandler);
		submitHandlerMap.put("liQui_SSES_SSESCPJG_SSESCPJG", liqui_sses_ssescpjg_ssescpjg_nodeHandler);
		submitHandlerMap.put("liQui_SSQT_SSQTCPJG_SSQTCPJG", liqui_ssqt_ssqtcpjg_ssqtcpjg_nodeHandler);
		submitHandlerMap.put("liQui_LX_LXSCZXLA_LXSCZXLA", liqui_lx_lxsczxla_lxsczxla_nodeHandler);
		submitHandlerMap.put("liQui_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK", liqui_ssys_ssyscpwszzsdqk_ssyscpwszzsdqk_nodeHandler);
		submitHandlerMap.put("liQui_SSES_SSESCPWSZZSDQK_SSESCPWSZZSDQK", liqui_sses_ssescpwszzsdqk_ssescpwszzsdqk_nodeHandler);
		submitHandlerMap.put("liQui_SSQT_SSQTCPWSZZSDQK_SSQTCPWSZZSDQK", liqui_ssqt_ssqtcpwszzsdqk_ssqtcpwszzsdqk_nodeHandler);
		submitHandlerMap.put("limit_XWXZ_XWXZXZCX_XWXZXZCX", limit_xwxz_xwxzxzcx_xwxzxzcx_nodeHandler);
		submitHandlerMap.put("liQui_SSQT_SSQTCPJGSX_SSQTCPJGSX", liQui_ssqt_ssqtcpjgsx_ssqtcpjgsx_nodeHandler);

		//财产程序任务提交map实体类
		submitHandlerMap.put("entityZX_STZX_CCZXPMJG_CCZXPMJG", entityzx_stzx_cczxpmjg_cczxpmjg_nodeHandler);
		submitHandlerMap.put("fundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK", fundingZX_zjzx_zjzxzjhk_zjzxzjhk_nodeHandler);
		submitHandlerMap.put("entityZX_STZX_CCZXZCDC_CCZXZCDC", entityZX_stzx_cczxzcdc_cczxzcdc_nodeHandler);
		submitHandlerMap.put("entityZX_STZX_CCZXDK_CCZXDK", entityZX_stzx_cczxdk_cczxdk_nodeHandler);
		submitHandlerMap.put("entityZX_STZX_CCZXJGYJ_CCZXJGYJ", entityZX_stzx_cczxjgyj_cczxjgyj_nodeHandler);
		submitHandlerMap.put("entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ", entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ_NODEHandler);

		//拍辅任务阶段提交map实现类
		submitHandlerMap.put("paiFu_STCC_PMGG_PMGG", paiFu_stcc_pmgg_pmgg_nodeHandler);
		submitHandlerMap.put("paiFu_STCC_PMJG_PMJG", paiFu_stcc_pmjg_pmjg_nodeHandler);
		submitHandlerMap.put("paiFu_STCC_JSZX_JSZX", paiFu_stcc_jszx_jszx_nodeHandler);
		submitHandlerMap.put("paiFu_STCC_BMKY_BMKY", paiFu_stcc_bmky_bmky_nodeHandler);
		submitHandlerMap.put("paiFu_STCC_KYZBGZ_KYZBGZ", paiFu_stcc_kyzbgz_kyzbgz_nodeHandler);
		submitHandlerMap.put("paiFu_STCC_YLKY_YLKY", paiFu_stcc_ylky_ylky_nodeHandler);
		submitHandlerMap.put("paiFu_STCC_PMJGSDQK_PMJGSDQK", paiFu_stcc_pmjgsdqk_pmjgsdqk_nodeHandler);
		submitHandlerMap.put("paiFu_STCC_DK_DK", paiFu_stcc_dk_dk_nodeHandler);
		submitHandlerMap.put("paiFu_STCC_ZCDC_ZCDC", paiFu_stcc_zcdc_zcdc_nodeHandler);


//		// 任务节点审核map实现类
//		auditHandlerMap.put("paiFu_JGRZ_SFYJ_YJJG", paifu_jgrz_sfyj_yjjg_nodeHandler);
//		auditHandlerMap.put("paiFu_JGRZ_DXXJ_XJJG", paifu_jgrz_dxxj_xjjg_nodeHandler);
//		auditHandlerMap.put("paiFu_JGRZ_WLXJ_XTLR", paifu_jgrz_wlxj_xtlr_nodeHandler);
//		auditHandlerMap.put("paiFu_JGRZ_PGDJ_XTLR", paifu_jgrz_pgdj_xtlr_nodeHandler);
//		auditHandlerMap.put("liQui_CASEE_REPAYMENTDETAILS", liqui_casee_repaymentdetails_nodeHandler);
//
//		// 任务补录
//		makeUpHandlerMap.put("paiFu_JGRZ_SFYJ_YJJG", paifu_jgrz_sfyj_yjjg_nodeHandler);
//		makeUpHandlerMap.put("paiFu_JGRZ_DXXJ_XJJG", paifu_jgrz_dxxj_xjjg_nodeHandler);
//		makeUpHandlerMap.put("paiFu_JGRZ_WLXJ_XTLR", paifu_jgrz_wlxj_xtlr_nodeHandler);
//		makeUpHandlerMap.put("paiFu_JGRZ_PGDJ_XTLR", paifu_jgrz_pgdj_xtlr_nodeHandler);
//		makeUpHandlerMap.put("paiFu_PMFZ_PMJGYP_PMJG", paifu_pmfz_pmjgyp_pmjg_nodeHandler);
	}

	/**
	 * 处理提交任务
	 *
	 * @param taskNode
	 */
	public void onTaskNodeSubmit(TaskNode taskNode) {

		TaskNodeHandler taskNodeHandler = null;

		if (taskNode.getNodeKey().contains("entityZX_STZX_CCZXPMJG_CCZXPMJG")) {
			taskNodeHandler = submitHandlerMap.get("entityZX_STZX_CCZXPMJG_CCZXPMJG");
		} else {
			taskNodeHandler = submitHandlerMap.get(taskNode.getNodeKey());
		}

		if (taskNodeHandler == null) {

			if (taskNode.getInsType().equals(1100)) {
				taskNodeHandler = paiFuGenerallyNodeHandler;
			} else {
				taskNodeHandler = liQuiGenerallyNodeHandler;
			}
		}

		taskNodeHandler.handlerTaskSubmit(taskNode);
	}

	/**
	 * 处理审核任务
	 *
	 * @param taskFlowDTO
	 */
	public void onTaskNodeAudit(TaskFlowDTO taskFlowDTO) {

		TaskNodeHandler taskNodeHandler = null;

		if (taskFlowDTO.getNodeKey().contains("entityZX_STZX_CCZXPMJG_CCZXPMJG")) {
			taskNodeHandler = submitHandlerMap.get("entityZX_STZX_CCZXPMJG_CCZXPMJG");
		} else {
			taskNodeHandler = submitHandlerMap.get(taskFlowDTO.getNodeKey());
		}

		if (taskNodeHandler == null) {

			if (taskFlowDTO.getInsType().equals(1100)) {
				taskNodeHandler = paiFuGenerallyNodeHandler;
			} else {
				taskNodeHandler = liQuiGenerallyNodeHandler;
			}

		}
		taskNodeHandler.handlerTaskAudit(taskFlowDTO);
	}

	/**
	 * 处理补录任务
	 *
	 * @param taskNode
	 */
	public void onTaskNodeMakeUp(TaskNode taskNode) {

		TaskNodeHandler taskNodeHandler = null;

		if (taskNode.getNodeKey().contains("entityZX_STZX_CCZXPMJG_CCZXPMJG")) {
			taskNodeHandler = submitHandlerMap.get("entityZX_STZX_CCZXPMJG_CCZXPMJG");
		} else {
			taskNodeHandler = submitHandlerMap.get(taskNode.getNodeKey());
		}

		if (taskNodeHandler == null) {

			if (taskNode.getInsType().equals(1100)) {
				taskNodeHandler = paiFuGenerallyNodeHandler;
			} else {
				taskNodeHandler = liQuiGenerallyNodeHandler;
			}

		}

		taskNodeHandler.handlerTaskMakeUp(taskNode);

	}
}
