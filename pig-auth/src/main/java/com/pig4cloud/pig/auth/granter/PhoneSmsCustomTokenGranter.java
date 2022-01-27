package com.pig4cloud.pig.auth.granter;

import com.pig4cloud.pig.common.security.service.CustomerUserDetailsServiceImpl;
import com.pig4cloud.pig.common.security.service.PigUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

public class PhoneSmsCustomTokenGranter extends AbstractCustomTokenGranter {
	private CustomerUserDetailsServiceImpl userDetailsService;
	public PhoneSmsCustomTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, CustomerUserDetailsServiceImpl userDetailsService) {
		super(tokenServices, clientDetailsService, requestFactory, "phone_sms_code");
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected UserDetails getUserDetails(Map<String, String> parameters) {
		UserDetails userDetails = userDetailsService.loadUserByPhone(parameters.get("username"), parameters.get("clientid"));

		return userDetails;
	}
}
