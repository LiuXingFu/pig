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

package com.pig4cloud.pig.common.security.component;

import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.security.service.PigUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lengleng
 * @date 2019-03-07
 * <p>
 * 根据checktoken 的结果转化用户信息
 */
public class PigUserAuthenticationConverter implements UserAuthenticationConverter {

	private static final String N_A = "N/A";

	/**
	 * Extract information about the user to be used in an access token (i.e. for resource
	 * servers).
	 * @param authentication an authentication representing a user
	 * @return a map of key values representing the unique information about the user
	 */
	@Override
	public Map<String, ?> convertUserAuthentication(Authentication authentication) {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put(USERNAME, authentication.getName());
		if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
			response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
		}
		return response;
	}

	/**
	 * Inverse of {@link #convertUserAuthentication(Authentication)}. Extracts an
	 * Authentication from a map.
	 * @param map a map of user information
	 * @return an Authentication representing the user or null if there is none
	 */
	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		if (map.containsKey(USERNAME)) {
			Collection<? extends GrantedAuthority> authorities = getAuthorities(map);

			String username = (String) map.get(SecurityConstants.DETAILS_USERNAME);
			Integer id = (Integer) map.get(SecurityConstants.DETAILS_USER_ID);
			Integer deptId = (Integer) map.get(SecurityConstants.DETAILS_DEPT_ID);
			Integer insId = (Integer) map.get(SecurityConstants.DETAILS_INS_ID);
			Integer outlesId = (Integer) map.get(SecurityConstants.DETAILS_OUTLES_ID);
			PigUser user = new PigUser(id, deptId, insId, outlesId, username, N_A, true, true, true, true, authorities);
			return new UsernamePasswordAuthenticationToken(user, N_A, authorities);
		}
		return null;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
		Object authorities = map.get(AUTHORITIES);
		if (authorities instanceof String) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
		}
		if (authorities instanceof Collection) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList(
					StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
		}
		return AuthorityUtils.NO_AUTHORITIES;
	}

}
