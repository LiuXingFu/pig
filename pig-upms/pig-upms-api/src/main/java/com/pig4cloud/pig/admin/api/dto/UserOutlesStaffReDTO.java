package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.UserOutlesStaffRe;
import lombok.Data;

import java.util.List;

@Data
public class UserOutlesStaffReDTO extends UserOutlesStaffRe {
	private List<UserOutlesStaffRe> staffList;
}
