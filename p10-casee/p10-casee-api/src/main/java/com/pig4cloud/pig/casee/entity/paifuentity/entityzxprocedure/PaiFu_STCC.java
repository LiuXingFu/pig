package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

@Data
public class PaiFu_STCC extends CommonalityData implements Serializable {

	/**
	 * 现勘
	 */
	PaiFu_STCC_XK paiFu_STCC_XK=new PaiFu_STCC_XK();

	/**
	 * 不动产现勘入户
	 */
	PaiFu_STCC_BDCXKRH paiFu_STCC_BDCXKRH=new PaiFu_STCC_BDCXKRH();

	/**
	 * 价格依据
	 */
	PaiFu_STCC_JGYJ paiFu_STCC_JGYJ=new PaiFu_STCC_JGYJ();

	/**
	 * 拍卖公告
	 */
	PaiFu_STCC_PMGG paiFu_STCC_PMGG=new PaiFu_STCC_PMGG();

	/**
	 * 接受咨询
	 */
	PaiFu_STCC_JSZX paiFu_STCC_JSZX=new PaiFu_STCC_JSZX();

	/**
	 * 报名看样
	 */
	PaiFu_STCC_BMKY paiFu_STCC_BMKY=new PaiFu_STCC_BMKY();

	/**
	 * 看样准备工作
	 */
	PaiFu_STCC_KYZBGZ paiFu_STCC_KYZBGZ=new PaiFu_STCC_KYZBGZ();

	/**
	 * 引领看样
	 */
	PaiFu_STCC_YLKY paiFu_STCC_YLKY=new PaiFu_STCC_YLKY();

	/**
	 * 拍卖结果
	 */
	PaiFu_STCC_PMJG paiFu_STCC_PMJG=new PaiFu_STCC_PMJG();

	/**
	 * 拍卖结果送达情况
	 */
	PaiFu_STCC_PMJGSDQK paiFu_STCC_PMJGSDQK=new PaiFu_STCC_PMJGSDQK();

	/**
	 * 到款
	 */
	PaiFu_STCC_DK paiFu_STCC_DK=new PaiFu_STCC_DK();

	/**
	 * 资产抵偿
	 */
	PaiFu_STCC_ZCDC paiFu_STCC_ZCDC=new PaiFu_STCC_ZCDC();

	/**
	 * 成交裁定
	 */
	PaiFu_STCC_CJCD paiFu_STCC_CJCD=new PaiFu_STCC_CJCD();

	/**
	 * 抵偿裁定
	 */
	PaiFu_STCC_DCCD paiFu_STCC_DCCD=new PaiFu_STCC_DCCD();

	/**
	 * 抵偿裁定送达情况
	 */
	PaiFu_STCC_DCCDSDQK paiFu_STCC_DCCDSDQK=new PaiFu_STCC_DCCDSDQK();

	/**
	 * 腾退成功
	 */
	PaiFu_STCC_TTCG paiFu_STCC_TTCG=new PaiFu_STCC_TTCG();

}
