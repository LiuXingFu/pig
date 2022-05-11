package com.pig4cloud.pig.casee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.DelAssetsTransferDTO;
import com.pig4cloud.pig.casee.dto.paifu.*;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.vo.AssetsPaifuVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePageVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePaifuDetailVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePaifuDetailsVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsReTargetPageVO;

import java.util.List;

public interface AssetsRePaifuService extends IService<AssetsRe> {

	/**
	 * 根据项目id分页查询标的物列表
	 * @param page
	 * @param assetsRePageDTO
	 * @return
	 */
	IPage<AssetsRePageVO> queryAssetsRePageByProjectId(Page page, AssetsRePageDTO assetsRePageDTO);

	/**
	 * 保存财产及项目案件关联财产
	 * @param assetsRePaifuSaveDTO
	 * @return
	 */
	Integer saveAssetsRe(AssetsRePaifuSaveDTO assetsRePaifuSaveDTO);

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

	/**
	 * 分页查询标的物列表
	 * @param page
	 * @param assetsReTargetPageDTO
	 * @return
	 */
	IPage<AssetsReTargetPageVO> queryTargetPage(Page page, AssetsReTargetPageDTO assetsReTargetPageDTO);

	/**
	 * 修改项目案件财产关联状态
	 * @return
	 */
	Integer modifyAssetsReStatus(AssetsReModifyStatusDTO assetsReModifyStatusDTO);

	/**
	 * 根据项目id查询财产关联信息
	 * @param projectId
	 * @param assetsName
	 * @return
	 */
	List<AssetsRePaifuDetailVO> queryByProjectId(Integer projectId,String assetsName);

	/**
	 * 查询可拍卖财产集合
	 * @param projectId
	 * @param caseeId
	 * @param assetsId
	 * @return
	 */
	List<AssetsRePaifuDetailVO> queryPostAnAuctionList(Integer projectId,Integer caseeId,Integer assetsId);

	/**
	 * 查询财产关联详情和项目案件财产详情
	 * @param assetsReId
	 * @return
	 */
	AssetsRePaifuDetailsVO queryAssetsReProjectCasee(Integer assetsReId);


}
