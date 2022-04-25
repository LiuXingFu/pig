package com.pig4cloud.pig.casee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.DelAssetsTransferDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePageDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePaifuModifyDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePaifuSaveDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.vo.AssetsPaifuVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePageVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePaifuDetailVO;

public interface AssetsRePaifuService extends IService<AssetsRe> {

	/**
	 * 根据项目id分页查询标的物列表
	 * @param page
	 * @param assetsRePageDTO
	 * @return
	 */
	IPage<AssetsRePageVO> queryAssetsRePageByProjectId(Page page, AssetsRePageDTO assetsRePageDTO);

	AssetsPaifuVO queryAssetsPaifuById(Integer assetsId, Integer projectId, Integer caseeId);

	/**
	 * 保存财产及项目案件关联财产
	 * @param assetsRePaifuSaveDTO
	 * @return
	 */
	Integer saveAssetsRe(AssetsRePaifuSaveDTO assetsRePaifuSaveDTO);

	int deleteAssetsTransfer(DelAssetsTransferDTO delAssetsTransferDTO);

	/**
	 * 根据id查询项目案件财产关联和财产详情
	 * @param assetsReId
	 * @return
	 */
	AssetsRePaifuDetailVO getAssetsReById(Integer assetsReId);

	/**
	 * 根据项目案件财产关联更新财产基本信息
	 * @param assetsRePaifuModifyDTO
	 * @return
	 */
	Integer modifyByAssetsReId(AssetsRePaifuModifyDTO assetsRePaifuModifyDTO);
}
