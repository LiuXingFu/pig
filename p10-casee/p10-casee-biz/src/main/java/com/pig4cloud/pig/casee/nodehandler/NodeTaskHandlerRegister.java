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

//	@Autowired
//	PAIFUGENERALLY_NODEHandler paiFuGenerallyNodeHandler;

	@Autowired
	LIQUIGENERALLY_NODEHandler liQuiGenerallyNodeHandler;

//	@Autowired
//	lIQUI_CASEE_REPAYMENTDETAILS_NODEHandler liqui_casee_repaymentdetails_nodeHandler;

	@Autowired
	LIQUI_SQ_SQBQJG_SQBQJG_NODEHandler liqui_sq_sqbqjg_sqbqjg_nodeHandler;

	@Autowired
	LIQUI_SQ_SQAJCA_SQAJCA_NODEHandler liqui_sq_sqajca_sqajca_nodeHandler;

	@Autowired
	LIQUI_SSYS_SSYSCPJG_SSYSCPJG_NODEHandler liqui_ssys_ssyscpjg_ssyscpjg_nodeHandler;

	@Autowired
	LIQUI_SSYS_SSYSJACA_SSYSJACA_NODEHandler liqui_ssys_ssysjaca_ssysjaca_nodeHandler;

	@Autowired
	LIQUI_SSBQ_SSBQBQJG_SSBQBQJG_NODEHandler liqui_ssbq_ssbqbqjg_ssbqbqjg_nodeHandler;

	@Autowired
	LIQUI_SSBQ_SSBQCA_SSBQCA_NODEHandler liqui_ssbq_ssbqca_ssbqca_nodeHandler;

	@Autowired
	LIQUI_SSES_SSESCPJG_SSESCPJG_NODEHandler liqui_sses_ssescpjg_ssescpjg_nodeHandler;

	@Autowired
	LIQUI_SSES_SSESJACA_SSESJACA_NODEHandler liqui_sses_ssesjaca_ssesjaca_nodeHandler;

	@Autowired
	LIQUI_SSQT_SSQTCPJG_SSQTCPJG_NODEHandler liqui_ssqt_ssqtcpjg_ssqtcpjg_nodeHandler;

	@Autowired
	LIQUI_SSQT_SSQTJACA_SSQTJACA_NODEHandler liqui_ssqt_ssqtjaca_ssqtjaca_nodeHandler;

	@Autowired
	LIQUI_LX_LXSCZXLA_LXSCZXLA_NODEHandler liqui_lx_lxsczxla_lxsczxla_nodeHandler;

	@Autowired
	LIQUI_ZXSZ_ZXSZZJ_ZXSZZJ_NODEHandler liqui_zxsz_zxszzj_zxszzj_nodeHandler;

	@Autowired
	LIQUI_ZXSZ_ZXSZSJZJ_ZXSZSJZJ_NODEHandler liqui_zxsz_zxszsjzj_zxszsjzj_nodeHandler;

	@Autowired
	LIQUI_ZXSZ_ZXSZJACA_ZXSZJACA_NODEHandler liqui_zxsz_zxszjaca_zxszjaca_nodeHandler;

	@Autowired
	LIQUI_ZXZH_ZXZHZJ_ZXZHZJ_NODEHandler liqui_zxzh_zxzhzj_zxzhzj_nodeHandler;

	@Autowired
	LIQUI_ZXZH_ZXZHSJZJ_ZXZHSJZJ_NODEHandler liqui_zxzh_zxzhsjzj_zxzhsjzj_nodeHandler;

	@Autowired
	LIQUI_ZXZH_ZXZHJACA_ZXZHJACA_NODEHandler liqui_zxzh_zxzhjaca_zxzhjaca_nodeHandler;

	@Autowired
	ENTITYZX_STZX_CCZXPMGG_CCZXPMGG_NODEHandler entityzx_stzx_cczxpmgg_cczxpmgg_nodeHandler;

	@Autowired
	BEILLEGAL_XWWF_XWWFXZCX_XWWFXZCX_NODEHandler beillegal_xwwf_xwwfxzcx_xwwfxzcx_nodeHandler;

	@Autowired
	LIQUI_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK_NODEHandler liqui_ssys_ssyscpwszzsdqk_ssyscpwszzsdqk_nodeHandler;

	// 任务节点提交节点map
	private Map<String, TaskNodeHandler> submitHandlerMap = new HashMap<>();

	// 任务节点审核节点map
	private Map<String, TaskNodeHandler> auditHandlerMap = new HashMap<>();

	// 任务节点补录节点map
	private Map<String, TaskNodeHandler> makeUpHandlerMap = new HashMap<>();

	//初始方法
	@PostConstruct
	public void init() {
//		// 拍辅任务节点提交map实现类
//		submitHandlerMap.put("paiFu_JGRZ_SFYJ_YJJG", paifu_jgrz_sfyj_yjjg_nodeHandler);
//		submitHandlerMap.put("paiFu_JGRZ_DXXJ_XJJG", paifu_jgrz_dxxj_xjjg_nodeHandler);
//		submitHandlerMap.put("paiFu_JGRZ_WLXJ_XTLR", paifu_jgrz_wlxj_xtlr_nodeHandler);
//		submitHandlerMap.put("paiFu_JGRZ_PGDJ_XTLR", paifu_jgrz_pgdj_xtlr_nodeHandler);
//		submitHandlerMap.put("paiFu_PMFZ_PMJGYP_PMJG", paifu_pmfz_pmjgyp_pmjg_nodeHandler);
//		submitHandlerMap.put("liQui_LX_ZQCS_ZQCS", liqui_lx_zqcs_zqcs_nodeHandler);

		//清收任务阶段提交map实现类
		submitHandlerMap.put("liQui_SQ_SQBQJG_SQBQJG", liqui_sq_sqbqjg_sqbqjg_nodeHandler);
		submitHandlerMap.put("liQui_SQ_SQAJCA_SQAJCA", liqui_sq_sqajca_sqajca_nodeHandler);

		submitHandlerMap.put("liQui_SSYS_SSYSCPJG_SSYSCPJG", liqui_ssys_ssyscpjg_ssyscpjg_nodeHandler);
		submitHandlerMap.put("liQui_SSYS_SSYSJACA_SSYSJACA", liqui_ssys_ssysjaca_ssysjaca_nodeHandler);
		submitHandlerMap.put("liQui_SSBQ_SSBQBQJG_SSBQBQJG", liqui_ssbq_ssbqbqjg_ssbqbqjg_nodeHandler);
		submitHandlerMap.put("liQui_SSBQ_SSBQCA_SSBQCA", liqui_ssbq_ssbqca_ssbqca_nodeHandler);
		submitHandlerMap.put("liQui_SSES_SSESCPJG_SSESCPJG", liqui_sses_ssescpjg_ssescpjg_nodeHandler);
		submitHandlerMap.put("liQui_SSES_SSESJACA_SSESJACA", liqui_sses_ssesjaca_ssesjaca_nodeHandler);
		submitHandlerMap.put("liQui_SSQT_SSQTCPJG_SSQTCPJG", liqui_ssqt_ssqtcpjg_ssqtcpjg_nodeHandler);
		submitHandlerMap.put("liQui_SSQT_SSQTJACA_SSQTJACA", liqui_ssqt_ssqtjaca_ssqtjaca_nodeHandler);
		submitHandlerMap.put("liQui_LX_LXSCZXLA_LXSCZXLA", liqui_lx_lxsczxla_lxsczxla_nodeHandler);
		submitHandlerMap.put("liQui_ZXSZ_ZXSZZJ_ZXSZZJ", liqui_zxsz_zxszzj_zxszzj_nodeHandler);
		submitHandlerMap.put("liQui_ZXSZ_ZXSZSJZJ_ZXSZSJZJ", liqui_zxsz_zxszsjzj_zxszsjzj_nodeHandler);
		submitHandlerMap.put("liQui_ZXSZ_ZXSZJACA_ZXSZJACA", liqui_zxsz_zxszjaca_zxszjaca_nodeHandler);
		submitHandlerMap.put("liQui_ZXZH_ZXZHZJ_ZXZHZJ", liqui_zxzh_zxzhzj_zxzhzj_nodeHandler);
		submitHandlerMap.put("liQui_ZXZH_ZXZHSJZJ_ZXZHSJZJ", liqui_zxzh_zxzhsjzj_zxzhsjzj_nodeHandler);
		submitHandlerMap.put("liQui_ZXZH_ZXZHJACA_ZXZHJACA", liqui_zxzh_zxzhjaca_zxzhjaca_nodeHandler);
		submitHandlerMap.put("liQui_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK", liqui_ssys_ssyscpwszzsdqk_ssyscpwszzsdqk_nodeHandler);

		//财产程序任务提交map实体类
		submitHandlerMap.put("entityZX_STZX_CCZXPMGG_CCZXPMGG", entityzx_stzx_cczxpmgg_cczxpmgg_nodeHandler);

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

		if (taskNode.getNodeKey().contains("entityZX_STZX_CCZXPMGG_CCZXPMGG")) {
			taskNodeHandler = submitHandlerMap.get("entityZX_STZX_CCZXPMGG_CCZXPMGG");
		} else {
			taskNodeHandler = submitHandlerMap.get(taskNode.getNodeKey());
		}

		if (taskNodeHandler == null) {

			taskNodeHandler = liQuiGenerallyNodeHandler;
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

		if (taskFlowDTO.getNodeKey().contains("entityZX_STZX_CCZXPMGG_CCZXPMGG")) {
			taskNodeHandler = submitHandlerMap.get("entityZX_STZX_CCZXPMGG_CCZXPMGG");
		} else {
			taskNodeHandler = submitHandlerMap.get(taskFlowDTO.getNodeKey());
		}

		if (taskNodeHandler == null) {

			taskNodeHandler = liQuiGenerallyNodeHandler;

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

		if (taskNode.getNodeKey().contains("entityZX_STZX_CCZXPMGG_CCZXPMGG")) {
			taskNodeHandler = submitHandlerMap.get("entityZX_STZX_CCZXPMGG_CCZXPMGG");
		} else {
			taskNodeHandler = submitHandlerMap.get(taskNode.getNodeKey());
		}

		if (taskNodeHandler == null) {

			taskNodeHandler = liQuiGenerallyNodeHandler;

		}

		taskNodeHandler.handlerTaskMakeUp(taskNode);

	}
}
