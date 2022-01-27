package com.pig4cloud.pig.auth.granter;

import com.pig4cloud.pig.common.security.service.CustomerUserDetailsServiceImpl;
import com.pig4cloud.pig.common.security.service.PigUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class AppPasswordCustomTokenGranter extends ResourceOwnerPasswordTokenGranter {
	private CustomerUserDetailsServiceImpl userDetailsService;
	public AppPasswordCustomTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, CustomerUserDetailsServiceImpl userDetailsService) {
		super(authenticationManager, tokenServices, clientDetailsService, requestFactory, "password_app");
		this.userDetailsService = userDetailsService;
	}

}
