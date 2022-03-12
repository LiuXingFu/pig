package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体执行财产
 */
@Data
public class EntityZX_STZX extends CommonalityData implements Serializable {

	/**
	 * 财产执行查封
	 */
	EntityZX_STZX_CCZXCF entityZX_STZX_CCZXCF = new EntityZX_STZX_CCZXCF();

	/**
	 * 财产执行查封送达情况
	 */
	EntityZX_STZX_STZXCFSDQK entityZX_STZX_STZXCFSDQK=new EntityZX_STZX_STZXCFSDQK();

	/**
	 * 财产执行商请移送
	 */
	EntityZX_STZX_CCZXSQYS entityZX_STZX_CCZXSQYS = new EntityZX_STZX_CCZXSQYS();

	/**
	 * 财产执行资产处置移交
	 */
	EntityZX_STZX_CCZXZCCZYJ entityZX_STZX_CCZXZCCZYJ = new EntityZX_STZX_CCZXZCCZYJ();

	/**
	 * 财产执行资产处置移交送达情况
	 */
	EntityZX_STZX_CCZXZCCZYJSDQK entityZX_STZX_CCZXZCCZYJSDQK = new EntityZX_STZX_CCZXZCCZYJSDQK();

	/**
	 *	财产执行现勘
	 */
	EntityZX_STZX_CCZXXK entityZX_STZX_CCZXXK = new EntityZX_STZX_CCZXXK();

	/**
	 * 财产执行不动产现勘入户
	 */
	EntityZX_STZX_CCZXBDCXKRH entityZX_STZX_CCZXBDCXKRH = new EntityZX_STZX_CCZXBDCXKRH();

	/**
	 * 财产执行价格依据
	 */
	EntityZX_STZX_CCZXJGYJ entityZX_STZX_CCZXJGYJ = new EntityZX_STZX_CCZXJGYJ();

	/**
	 * 财产执行拍卖公告
	 */
	EntityZX_STZX_CCZXPMGG entityZX_STZX_CCZXPMGG = new EntityZX_STZX_CCZXPMGG();

	/**
	 * 财产执行拍卖结果
	 */
	EntityZX_STZX_CCZXPMJG entityZX_STZX_CCZXPMJG = new EntityZX_STZX_CCZXPMJG();

	/**
	 * 财产执行拍卖结果送达情况
	 */
	EntityZX_STZX_CCZXPMJGSDQK entityZX_STZX_CCZXPMJGSDQK = new EntityZX_STZX_CCZXPMJGSDQK();

	/**
	 * 财产执行到款
	 */
	EntityZX_STZX_CCZXDK entityZX_STZX_CCZXDK = new EntityZX_STZX_CCZXDK();

	/**
	 * 财产执行资产抵债
	 */
	EntityZX_STZX_CCZXZCDC entityZX_STZX_CCZXZCDC = new EntityZX_STZX_CCZXZCDC();

	/**
	 *	财产执行成交裁定
	 */
	EntityZX_STZX_CCZXCJCD entityZX_STZX_CCZXCJCD = new EntityZX_STZX_CCZXCJCD();

	/**
	 * 财产执行抵偿裁定
	 */
	EntityZX_STZX_CCZXDCCD entityZX_STZX_CCZXDCCD = new EntityZX_STZX_CCZXDCCD();

	/**
	 * 财产执行抵偿裁定送达情况
	 */
	EntityZX_STZX_CCZXDCCDSDQK entityZX_STZX_CCZXDCCDSDQK = new EntityZX_STZX_CCZXDCCDSDQK();

	/**
	 * 财产执行腾退成功
	 */
	EntityZX_STZX_CCZXTTCG entityZX_STZX_CCZXTTCG = new EntityZX_STZX_CCZXTTCG();

}
