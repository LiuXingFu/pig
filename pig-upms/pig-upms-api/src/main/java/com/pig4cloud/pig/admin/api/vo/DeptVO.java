package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.SysDept;
import lombok.Data;

@Data
public class DeptVO extends SysDept {

	/**
	 * 父级部门
	 */
	String parentName;

}
