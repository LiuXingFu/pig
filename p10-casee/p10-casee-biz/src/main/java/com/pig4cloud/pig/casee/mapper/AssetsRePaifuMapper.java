package com.pig4cloud.pig.casee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.paifu.AssetsRePageDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.vo.AssetsPaifuVO;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AssetsRePaifuMapper extends BaseMapper<AssetsRe> {

	IPage<AssetsRePageVO> queryAssetsRePageByProjectId(Page page, @Param("query") AssetsRePageDTO assetsRePageDTO);

	AssetsPaifuVO queryAssetsPaifuById(@Param("assetsId") Integer assetsId, @Param("projectId") Integer projectId, @Param("caseeId") Integer caseeId);
}

