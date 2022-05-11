package com.pig4cloud.pig.casee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePageDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsReTargetPageDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.vo.AssetsPaifuVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePageVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePaifuDetailVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePaifuDetailsVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsReTargetPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AssetsRePaifuMapper extends BaseMapper<AssetsRe> {

	IPage<AssetsRePageVO> queryAssetsRePageByProjectId(Page page, @Param("query") AssetsRePageDTO assetsRePageDTO);

	AssetsRePaifuDetailVO selectByAssetsReId(@Param("assetsReId") Integer assetsReId);

	AssetsRePaifuDetailVO queryAeetsDetail(@Param("assetsReId") Integer assetsReId);

	IPage<AssetsReTargetPageVO> queryTargetPage(Page page, @Param("query") AssetsReTargetPageDTO assetsReTargetPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	List<AssetsRePaifuDetailVO> selectByProjectId(@Param("projectId") Integer projectId,@Param("assetsName") String assetsName);

	List<AssetsRePaifuDetailVO> selectPostAnAuctionList(@Param("projectId") Integer projectId,@Param("caseeId") Integer caseeId,@Param("assetsId") Integer assetsId);

	AssetsRePaifuDetailsVO queryAssetsReProjectCasee(@Param("assetsReId") Integer assetsReId);
}

