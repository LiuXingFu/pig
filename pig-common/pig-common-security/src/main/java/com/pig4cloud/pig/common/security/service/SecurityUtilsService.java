package com.pig4cloud.pig.common.security.service;


import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityUtilsService {

	private final CacheManager cacheManager;


	/**
	 * 获取缓存用户信息带选择的网点ID
	 */
	public PigUser getCacheUser() {
		Authentication authentication = SecurityUtils.getAuthentication();
		if (authentication == null) {
			return null;
		}
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		PigUser pigUser = cache.get(SecurityUtils.getUser().getUsername(), PigUser.class);
		return pigUser;
	}
}
