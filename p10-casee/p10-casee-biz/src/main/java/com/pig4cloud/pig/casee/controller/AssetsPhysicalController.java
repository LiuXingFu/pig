package com.pig4cloud.pig.casee.controller;

import com.pig4cloud.pig.casee.service.AssetsCapitalService;
import com.pig4cloud.pig.casee.service.AssetsPhysicalService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 财产表
 *
 * @author Mjh
 * @date 2022-02-10 09:39:52
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/assetsPhysical" )
@Api(value = "assetsPhysical", tags = "实体财产管理")
public class AssetsPhysicalController {

	private final AssetsPhysicalService assetsPhysicalService;



}
