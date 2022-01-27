package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 拍卖辅助
 */
public class PaiFu_PMFZ implements Serializable {
	/**
	 * 公告前辅助(一拍)
	 */
	private PaiFu_PMFZ_GGQFZYP paiFu_PMFZ_GGQFZYP=new PaiFu_PMFZ_GGQFZYP();
	/**
	 * 关联标的(一拍)
	 */
	private PaiFu_PMFZ_GLBDYP paiFu_PMFZ_GLBDYP=new PaiFu_PMFZ_GLBDYP();

	/**
	 * 预约看样(一拍)
	 */
	private PaiFu_PMFZ_YYKYYP paiFu_PMFZ_YYKYYP=new PaiFu_PMFZ_YYKYYP();

	/**
	 * 引领看样(一拍)
	 */
	private PaiFu_PMFZ_YLKYYP paiFu_PMFZ_YLKYYP=new PaiFu_PMFZ_YLKYYP();

	/**
	 * 拍卖结果(一拍)
	 */
	private PaiFu_PMFZ_PMJGYP paiFu_PMFZ_PMJGYP=new PaiFu_PMFZ_PMJGYP();

	/**
	 * 公告前辅助(二拍)
	 */
	private PaiFu_PMFZ_GGQFZEP paiFu_PMFZ_GGQFZEP=new PaiFu_PMFZ_GGQFZEP();
	/**
	 * 关联标的(二拍)
	 */
	private PaiFu_PMFZ_GLBDEP paiFu_PMFZ_GLBDEP=new PaiFu_PMFZ_GLBDEP();

	/**
	 * 预约看样(二拍)
	 */
	private PaiFu_PMFZ_YYKYEP paiFu_PMFZ_YYKYEP=new PaiFu_PMFZ_YYKYEP();

	/**
	 * 引领看样(二拍)
	 */
	private PaiFu_PMFZ_YLKYEP paiFu_PMFZ_YLKYEP=new PaiFu_PMFZ_YLKYEP();

	/**
	 * 拍卖结果(二拍)
	 */
	private PaiFu_PMFZ_PMJGEP paiFu_PMFZ_PMJGEP=new PaiFu_PMFZ_PMJGEP();

	/**
	 * 公告前辅助(变卖)
	 */
	private PaiFu_PMFZ_GGQFZBM paiFu_PMFZ_GGQFZBM=new PaiFu_PMFZ_GGQFZBM();
	/**
	 * 关联标的(变卖)
	 */
	private PaiFu_PMFZ_GLBDBM paiFu_PMFZ_GLBDBM=new PaiFu_PMFZ_GLBDBM();

	/**
	 * 预约看样(变卖)
	 */
	private PaiFu_PMFZ_YYKYBM paiFu_PMFZ_YYKYBM=new PaiFu_PMFZ_YYKYBM();

	/**
	 * 引领看样(变卖)
	 */
	private PaiFu_PMFZ_YLKYBM paiFu_PMFZ_YLKYBM=new PaiFu_PMFZ_YLKYBM();

	/**
	 * 拍卖结果(变卖)
	 */
	private PaiFu_PMFZ_PMJGBM paiFu_PMFZ_PMJGBM=new PaiFu_PMFZ_PMJGBM();
}
