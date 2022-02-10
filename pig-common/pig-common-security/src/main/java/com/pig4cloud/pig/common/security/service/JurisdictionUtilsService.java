package com.pig4cloud.pig.common.security.service;


import com.pig4cloud.pig.admin.api.entity.SysRole;
import com.pig4cloud.pig.admin.api.feign.RemoteSysRoleService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class JurisdictionUtilsService {

	private final SecurityUtilsService securityUtilsService;

	private final RemoteSysRoleService remoteSysRoleService;


	/**
	 * 获取机构id
	 */
	public Integer queryByInsId(String roleCode) {
		Integer insId = 0;
		PigUser pigUser = securityUtilsService.getCacheUser();
		// 运营平台账号可查所有数据
		R<SysRole> sysRole = remoteSysRoleService.queryByUserIdList(null, pigUser.getInsId(), null, roleCode, SecurityConstants.FROM);
		if(Objects.isNull(sysRole.getData())){
			insId = pigUser.getInsId();
		}
		return insId;
	}

	/**
	 * 获取网点id
	 */
	public Integer queryByOutlesId(String roleCode) {
		Integer outlesId = 0;
		PigUser pigUser = securityUtilsService.getCacheUser();
		// 运营平台账号可查所有数据
		R<SysRole> sysRole = remoteSysRoleService.queryByUserIdList(null, pigUser.getInsId(), pigUser.getOutlesId(), roleCode, SecurityConstants.FROM);
		if(Objects.isNull(sysRole.getData())){
			outlesId = pigUser.getOutlesId();
		}
		return outlesId;
	}
}
