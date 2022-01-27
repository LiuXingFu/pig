package com.pig4cloud.pig.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Api(value = "test", tags = "测试接口模块")
public class TestController {

	@Getter
	@Setter
	@ApiModel(value = "JSONTest")
	public static class JSONTest{
		public String name;
		public JSONObject data;
//		public void setData(String data){
//
//		}
	}
	/**
	 * 分页token 信息
	 * @param params 参数集
	 * @return token集合
	 */
	@PostMapping("/pub/jsonTest")
	public R jsonTest(JSONTest params ) {
		log.info("params");
		return R.ok();
	}
}
