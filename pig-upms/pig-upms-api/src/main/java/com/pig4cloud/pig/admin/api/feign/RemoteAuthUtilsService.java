package com.pig4cloud.pig.admin.api.feign;

import com.pig4cloud.pig.admin.api.feign.factory.RemoteAuthUtilsServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(contextId = "remoteAuthUtilsService", value = ServiceNameConstants.UMPS_SERVICE,
		fallbackFactory = RemoteAuthUtilsServiceFallbackFactory.class)
public interface RemoteAuthUtilsService {

	/**
	 * 根据 uni_id_token， uni_id 拉取用户数据
	 *
	 * @return
	 */
	@PostMapping("/authutils/pub/getUseData")
	R getUseData(@RequestParam("uniId") String uniId, @RequestParam("uniIdToken") String uniIdToken, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 推送消息
	 * @param clientids 设备id
	 * @param title 标题
	 * @param content 内容
	 * @return
	 */
	@PostMapping("/authutils/pub/pushAppMessage")
	R pushAppMessage(@RequestParam("clientids") List<String> clientids, @RequestParam("title")  String title, @RequestParam("content")  String content, @RequestHeader(SecurityConstants.FROM) String from);

}
