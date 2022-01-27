package com.pig4cloud.pig.admin.api.appserver;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
@RequiredArgsConstructor
public class RequestAppService {
	public static final String APPSERVER_BASE_URL = "https://671094bf-f0d3-4cdf-b8b3-afa35f91311b.bspapp.com/http/router/";
	private final static char[] RANDOM_CODE_VEC = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

	public static final String APPSECRET = "VAf7WuNVY2yjk2kaPai56VFLvHwxWDU5";

	private final RedisTemplate<String, Object> redisTemplate;


	/**
	 * 推送消息
	 *
	 * @param clientidList 设备id集合
	 * @param title     标题
	 * @param content   内容
	 * @return
	 */
	public R pushAppMessage(List<String> clientidList, String title, String content) {

		String clientids = "";

		for (String clientid : clientidList) {
			clientids += clientid + ",";
		}

		clientids = clientids.substring(0, clientids.length() - 1);

		Map<String, Object> params = new HashMap<>();
		params.put("clientids", clientids);
		params.put("appid", "p10admin");
		params.put("title", title);
		params.put("content", content);

		R result = reqAppServer("open/message/pub/pushAppMessage", params);

		return result;
	}

	/**
	 * 获取验证码
	 *
	 * @param length
	 * @return
	 */
	public String getRandomCode(int length) {
		StringBuilder strbuff = new StringBuilder();
		for (int i = 0; i < length; i++) {
			strbuff.append(RANDOM_CODE_VEC[(int) (Math.floor(Math.random() * RANDOM_CODE_VEC.length))]);
		}
		return strbuff.toString();
	}

	/**
	 * 发送验证短信
	 *
	 * @param phone
	 * @return
	 */
	public R sendMsmVerifyCode(String phone) {
		String smsCode = getRandomCode(6);

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.opsForValue().set(
				CacheConstants.DEFAULT_SMS_CODE_KEY + phone, smsCode,
				SecurityConstants.CODE_TIME * 5, TimeUnit.SECONDS
		);

//		log.info("smsCode : "+ smsCode);
//		Map<String, Object> data = new HashMap();
//		data.put("len", 6);

		Map<String, Object> params = new HashMap<>();
		params.put("appid", "p10admin");
		params.put("phone", phone);
		params.put("smsCode", smsCode);
		params.put("templateId", "13438");
		params.put("expMinute", '5');

		R result = reqAppServer("open/sms/pub/sendMsm", params);
		return result.getCode() == CommonConstants.SUCCESS ? R.ok(smsCode) : result;
	}

	public R miniProgramWeChatLogin() {
		Map<String, Object> params = new HashMap<>();
		params.put("appid", "p10admin");
		R result = reqAppServer("open/user/pub/verifyUserLogin", params);
		return result;
	}

	/**
	 * 根据 uni_id_token， uni_id 拉取用户数据
	 *
	 * @param uniId
	 * @param uniIdToken
	 * @return
	 */
	public R getUseData(String uniId, String uniIdToken) {
		Map<String, Object> params = new HashMap<>();
		params.put("appid", "p10admin");
		params.put("uniId", uniId);
		params.put("uniIdToken", uniIdToken);
		R result = reqAppServer("open/user/pub/getUseData", params);
		return result;
	}


	/**
	 * @param requestUrl
	 * @return
	 */
	public R doRequestHttpGet(String requestUrl) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建Get请求
		HttpGet httpGet = new HttpGet(requestUrl);

		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				return R.ok(EntityUtils.toString(responseEntity));
			} else {
				return R.failed("返回数据为空");
			}
		} catch (ParseException | IOException e) {
			return R.failed("连接失败" + e.getMessage());
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @param method
	 * @param params
	 * @return
	 */
	public R reqAppServer(String method, Map<String, Object> params) {
		String requestUrl = APPSERVER_BASE_URL + method + "?";
		params.put("time", System.currentTimeMillis());

		List<String> paramsKeyList = new ArrayList<>();
		for (String key : params.keySet()) {
			paramsKeyList.add(key);
		}
		paramsKeyList.sort(new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return a.compareTo(b);
			}
		});
		StringBuffer paramsStrBuff = new StringBuffer();
		for (int index = 0; index < paramsKeyList.size(); index++) {
			paramsStrBuff.append(paramsKeyList.get(index)).append("=").append(params.get(paramsKeyList.get(index)) + "");
			if (index != paramsKeyList.size() - 1) {
				paramsStrBuff.append("&");
			}
		}
		String paramsStr = paramsStrBuff.toString();

		String sign = DigestUtils.md5DigestAsHex((paramsStr + "&appsecret=" + APPSECRET).getBytes());
		paramsStr = paramsStr + "&sign=" + sign;

		requestUrl = requestUrl + paramsStr;


		R result = doRequestHttpGet(requestUrl);
		if (result.getCode() != CommonConstants.SUCCESS) {
			return result;
		}
		Map<String, Object> resultMap = ((Map) JSON.parse(result.getData().toString()));

		Object data = resultMap.get("data");
		int code = (int) resultMap.get("code");
		String msg = (String) resultMap.get("msg");
		if (code != CommonConstants.SUCCESS) {
			return R.failed(msg);
		}
		return R.ok(data);
	}
}
