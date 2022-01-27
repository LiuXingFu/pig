package com.pig4cloud.pig.auth.granter;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.pig4cloud.pig.admin.api.feign.RemoteUserService;
import com.pig4cloud.pig.common.security.service.CustomerUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class WxTokenAndUniIdCustomTokenGranter extends AbstractCustomTokenGranter {

	private CustomerUserDetailsServiceImpl userDetailsService;

	public WxTokenAndUniIdCustomTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, CustomerUserDetailsServiceImpl userDetailsService) {
		super(tokenServices, clientDetailsService, requestFactory, "weixin_uni_id");
		this.userDetailsService = userDetailsService;


	}

	@Override
	protected UserDetails getUserDetails(Map<String, String> parameters) {

		return userDetailsService.loadUserByUniID(parameters.get("uniId"), parameters.get("uniIdToken"), parameters.get("clientid"));

	}
}
