package com.pig4cloud.pig.casee.dto.paifu.excel;

import com.pig4cloud.pig.casee.entity.paifuentity.excel.ImportPaifu;
import lombok.Data;

import java.util.List;

@Data
public class ImportPaifuDTO {

	/**
	 * 在办导入集合 2021、2022tab页数据
	 */
	private List<ImportPaifu> inProgressList;

	/**
	 * 已结案导入集合 已结案tab页数据
	 */
	private List<ImportPaifu> closedList;

}
