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

package com.pig4cloud.pig.auth.config;

import com.pig4cloud.pig.auth.granter.AppPasswordCustomTokenGranter;
import com.pig4cloud.pig.auth.granter.PhoneSmsCustomTokenGranter;
import com.pig4cloud.pig.auth.granter.WxTokenAndUniIdCustomTokenGranter;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.security.component.PigWebResponseExceptionTranslator;
import com.pig4cloud.pig.common.security.service.CustomerUserDetailsServiceImpl;
import com.pig4cloud.pig.common.security.service.PigClientDetailsService;
import com.pig4cloud.pig.common.security.service.PigUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.*;

/**
 * @author lengleng
 * @date 2019/2/1 认证服务器配置
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private final DataSource dataSource;

	private final CustomerUserDetailsServiceImpl customerUserDetailsService;

	private final AuthenticationManager authenticationManager;

	private final RedisConnectionFactory redisConnectionFactory;


	@Override
	@SneakyThrows
	public void configure(ClientDetailsServiceConfigurer clients) {
		PigClientDetailsService clientDetailsService = new PigClientDetailsService(dataSource);
		clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
		clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
		clients.withClientDetails(clientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
	}


	public List<TokenGranter> getTokenGranters(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory){
		return new ArrayList<>(Arrays.asList(
				new WxTokenAndUniIdCustomTokenGranter(tokenServices, clientDetailsService, requestFactory, customerUserDetailsService),
				new PhoneSmsCustomTokenGranter(tokenServices, clientDetailsService, requestFactory, customerUserDetailsService), //验证码
				new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory), //系统自带的
				new AppPasswordCustomTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory, customerUserDetailsService)  // 继承系统自带的
		));
	}

	//用户校验
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		List<TokenGranter> tokenGranters = getTokenGranters(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory());
		endpoints.tokenGranter(new CompositeTokenGranter(tokenGranters));

		endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST).tokenStore(tokenStore())
				.tokenEnhancer(tokenEnhancer()).userDetailsService(customerUserDetailsService)
				.authenticationManager(authenticationManager).reuseRefreshTokens(false)
				.pathMapping("/oauth/confirm_access", "/token/confirm_access")
				.exceptionTranslator(new PigWebResponseExceptionTranslator());
	}


	//token 存储
	@Bean
	public TokenStore tokenStore() {
		RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		tokenStore.setPrefix(CacheConstants.PROJECT_OAUTH_ACCESS);
		return tokenStore;
	}


	//生成token 规则
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			final Map<String, Object> additionalInfo = new HashMap<>(4);
			PigUser pigUser = (PigUser) authentication.getUserAuthentication().getPrincipal();

			additionalInfo.put(SecurityConstants.DETAILS_LICENSE, SecurityConstants.PROJECT_LICENSE);
			additionalInfo.put(SecurityConstants.DETAILS_USER_ID, pigUser.getId());
			additionalInfo.put(SecurityConstants.DETAILS_USERNAME, pigUser.getUsername());
			additionalInfo.put(SecurityConstants.DETAILS_DEPT_ID, pigUser.getDeptId());
			additionalInfo.put(SecurityConstants.DETAILS_INS_ID, pigUser.getInsId());
			additionalInfo.put(SecurityConstants.DETAILS_OUTLES_ID, pigUser.getOutlesId());

			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			Date dataExpiration = new Date(System.currentTimeMillis()+60 * 60 * 24 * 30 * 1000L);
			((DefaultOAuth2AccessToken) accessToken).setExpiration(dataExpiration);
			return accessToken;
		};
	}

}
