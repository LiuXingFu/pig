/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pig4cloud.pig.admin.api.dto.UserInfo;
import com.pig4cloud.pig.admin.api.entity.Institution;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.api.feign.RemoteAuthUtilsService;
import com.pig4cloud.pig.admin.api.feign.RemoteUserService;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户详细信息
 *
 * @author lengleng
 */
@Slf4j
@Service
	@RequiredArgsConstructor
	public class CustomerUserDetailsServiceImpl implements UserDetailsService {

	private final RemoteUserService remoteUserService;

	private final RemoteAuthUtilsService remoteAuthUtilsService;

	private final CacheManager cacheManager;

	/**
	 * 手机号登录
	 * @param phone 手机号
	 * @return
	 */
	@SneakyThrows
	public UserDetails loadUserByPhone(String phone, String clientId) {
//		Cache cachePhone = cacheManager.getCache(CacheConstants.USER_PHONE_DETAILS);
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
//		try{
//			if (cachePhone != null && cachePhone.get(phone) != null) {
//				return (PigUser) cachePhone.get(phone).get();
//			}
//		}catch (Exception e){
//			//数据升级了，需要重新登录
//			log.info("数据升级了，需要重新登录");
//		}

		R<UserInfo> result = remoteUserService.infoByPhone(phone, SecurityConstants.FROM_IN);
		if(result.getData()==null){
			throw new UsernameNotFoundException("用户不存在");
		}
//		if(result.getData().getInstitution()==null
//				|| result.getData().getRoles()==null
//				|| result.getData().getPermissions()==null ){
//			throw new UsernameNotFoundException("用户没有后台登录权限");
//		}
		UserDetails userDetails = getUserDetails(result);
//		if (cachePhone != null) {
//			cachePhone.put(phone, userDetails);
//		}
		if (cache != null) {
			cache.put(userDetails.getUsername(), userDetails);
		}
		remoteUserService.updateUserClientInfo(userDetails.getUsername(), clientId, SecurityConstants.FROM_IN);
		return userDetails;
	}


	/**
	 * 用户密码登录
	 * @param username 用户名
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		try{
			if (cache != null && cache.get(username) != null) {
				return (PigUser) cache.get(username).get();
			}
		}catch (Exception e){
			//数据升级了，需要重新登录
			log.info("数据升级了，需要重新登录");
		}




		R<UserInfo> result = remoteUserService.info(username, SecurityConstants.FROM_IN);
		if(result.getData()==null){
			throw new UsernameNotFoundException("用户不存在");
		}
		if(result.getData().getInstitution()==null
				|| result.getData().getRoles()==null
				|| result.getData().getPermissions()==null ){
			throw new UsernameNotFoundException("用户没有后台登录权限");
		}
		UserDetails userDetails = getUserDetails(result);
		if (cache != null) {
			cache.put(username, userDetails);
		}
		return userDetails;
	}

	/**
	 * 构建userdetails
	 * @param result 用户信息
	 * @return
	 */
	private UserDetails getUserDetails(R<UserInfo> result) {
		if (result == null || result.getData() == null) {
			throw new UsernameNotFoundException("用户不存在");
		}

		UserInfo info = result.getData();
		Set<String> dbAuthsSet = new HashSet<>();
		if (ArrayUtil.isNotEmpty(info.getRoles())) {
			// 获取角色
			Arrays.stream(info.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
			// 获取资源
			dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));

		}
		Collection<? extends GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(dbAuthsSet.toArray(new String[0]));
		SysUser user = info.getSysUser();
		Institution institution = info.getInstitution();
		// 构造security用户
		return new PigUser(user.getUserId(), user.getDeptId(),
				institution!=null?institution.getInsId():null,
				null,
				user.getUsername(),
				SecurityConstants.BCRYPT + user.getPassword(),
				StrUtil.equals(user.getLockFlag(), CommonConstants.STATUS_NORMAL), true, true, true, authorities);
	}

	/**
	 * 获取用户信息
	 * @param uniId 用户id
	 * @param uniIdToken token
	 * @param clientId
	 * @return
	 */
	public UserDetails loadUserByUniID(String uniId, String uniIdToken, String clientId) {
		//去unicloud 根据 uni_id_token， uni_id 拉取用户数据

		R useData = remoteAuthUtilsService.getUseData(uniId, uniIdToken, SecurityConstants.FROM);

		if(useData.getCode() != 0){
			//如果用户不存在 ，登录失败
			throw new UsernameNotFoundException("用户不存在");
		}

		//拉取到了用数据，根据weixin openid 查询我们的用户数据库 查询用户

		//我们的用户存在，登录成功

		//我我们用不存在，创建用户，登录成功。

		return null;
	}
}
