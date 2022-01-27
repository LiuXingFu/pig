package com.pig4cloud.pig.casee.entity.project.beillegalprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 行为违法
 */
@Data
public class BeIllegal_XWWF extends CommonalityData implements Serializable {

	/**
	 * 行为违法送达情况
	 */
	BeIllegal_XWWF_XWWFSDQK beIllegal_XWWF_XWWFSDQK = new BeIllegal_XWWF_XWWFSDQK();

	/**
	 * 行为违法限制撤销
	 */
	BeIllegal_XWWF_XWWFXZCX beIllegal_XWWF_XWWFXZCX = new BeIllegal_XWWF_XWWFXZCX();

	/**
	 * 行为违法限制撤销送达情况
	 */
	BeIllegal_XWWF_XWWFXZCXSDQK beIllegal_XWWF_XWWFXZCXSDQK = new BeIllegal_XWWF_XWWFXZCXSDQK();

	/**
	 * 实施行为违法
	 */
	BeIllegal_XWWF_SSXWWF beIllegal_XWWF_SSXWWF = new BeIllegal_XWWF_SSXWWF();

	/**
	 * 实施行为违法送达情况
	 */
	BeIllegal_XWWF_SSXWWFSDQK beIllegal_XWWF_SSXWWFSDQK = new BeIllegal_XWWF_SSXWWFSDQK();

}
