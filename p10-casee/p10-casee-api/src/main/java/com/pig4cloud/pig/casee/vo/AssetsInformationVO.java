package com.pig4cloud.pig.casee.vo;


import com.pig4cloud.pig.casee.dto.AssetsDTO;
import com.pig4cloud.pig.casee.entity.MortgageAssetsRecords;
import lombok.Data;
import java.util.List;

/**
 * 	抵押物信息DTO
 */
@Data
public class AssetsInformationVO extends MortgageAssetsRecords {

	/**
	 * 	抵押物信息DTO
	 */
	List<AssetsDTO> assetsDTOList;
}
