package com.pig4cloud.pig.admin.controller;

import com.pig4cloud.pig.admin.api.appserver.RequestAppService;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/authutils")
@Api(value = "authutils", tags = "认证管理")
public class AuthUtilsController {

	@Autowired
	RequestAppService requestAppService;

	/**
	 * 发送短信验证码
	 *
	 * @param phone 发送手机号码
	 * @return
	 */
	@ApiOperation(value = "发送短信验证码", notes = "发送短信验证码")
	@PostMapping("/pub/smsVerificationCode")
	public R smsVerificationCode(String phone){
		R result = requestAppService.sendMsmVerifyCode(phone);
		return result.getCode() == CommonConstants.SUCCESS ? R.ok() : result;
	}

	/**
	 * 推送消息
	 * @param clientids 设备id
	 * @param title 标题
	 * @param content 内容
	 * @return
	 */
	@ApiOperation(value = "推送消息", notes = "推送消息")
	@PostMapping("/pub/pushAppMessage")
	public R pushAppMessage(List<String> clientids, String title, String content){
		R result = requestAppService.pushAppMessage(clientids, title, content);
		return result.getCode() == CommonConstants.SUCCESS ? R.ok() : result;
	}

	/**
	 * 根据 uni_id_token， uni_id 拉取用户数据
	 *
	 * @return
	 */
	@ApiOperation(value = "根据 uni_id_token， uni_id 拉取用户数据", notes = "根据 uni_id_token， uni_id 拉取用户数据")
	@PostMapping("/pub/getUseData")
	public R getUseData(String uniId, String uniIdToken){
		R result = requestAppService.getUseData(uniId, uniIdToken);
		return result;
	}
}
