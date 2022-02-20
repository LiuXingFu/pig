package com.pig4cloud.pig.casee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.casee.dto.AssetsOrProjectPageDTO;
import com.pig4cloud.pig.casee.dto.BankLoanDTO;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.AssetsBankLoanRe;
import com.pig4cloud.pig.casee.service.AssetsBankLoanReService;
import com.pig4cloud.pig.casee.service.AssetsCapitalService;
import com.pig4cloud.pig.casee.service.AssetsService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 财产表
 *
 * @author Mjh
 * @date 2022-02-10 09:39:52
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/assetsCapital" )
@Api(value = "assetsCapital", tags = "资金财产管理")
public class AssetsCapitalController {

	private final AssetsCapitalService assetsCapitalService;



}
