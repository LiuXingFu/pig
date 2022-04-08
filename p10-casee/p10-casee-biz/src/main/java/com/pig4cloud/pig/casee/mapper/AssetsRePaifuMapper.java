package com.pig4cloud.pig.casee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.TargetAssetsReAssetsCaseePageDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.vo.TargetAssetsReAssetsCaseeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AssetsRePaifuMapper extends BaseMapper<AssetsRe> {

	IPage<TargetAssetsReAssetsCaseeVO> queryTargetPage(Page page, @Param("query") TargetAssetsReAssetsCaseePageDTO targetAssetsReAssetsCaseePageDTO);

}
