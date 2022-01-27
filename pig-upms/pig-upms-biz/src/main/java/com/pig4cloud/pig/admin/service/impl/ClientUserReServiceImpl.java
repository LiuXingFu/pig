/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.ClientUserRe;
import com.pig4cloud.pig.admin.mapper.ClientUserReMapper;
import com.pig4cloud.pig.admin.service.ClientUserReService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 
 *
 * @author yuanduo
 * @date 2021-12-08 16:21:04
 */
@Service
public class ClientUserReServiceImpl extends ServiceImpl<ClientUserReMapper, ClientUserRe> implements ClientUserReService {

	/**
	 * 更新clientId
	 * @param username
	 * @param clientId
	 * @return 用户信息
	 */
	@Override
	@Transactional
	public Integer updateUserClientInfo(String username, String clientId) {
		ClientUserRe clientUserRe = this.getOne(new LambdaQueryWrapper<ClientUserRe>().eq(ClientUserRe::getUserName, username).eq(ClientUserRe::getClientId, clientId));
		if(Objects.nonNull(clientUserRe)){
			return 0;
		}
		clientUserRe = new ClientUserRe();
		clientUserRe.setClientId(clientId);
		clientUserRe.setUserName(username);
		this.save(clientUserRe);
		return 1;
	}
}
