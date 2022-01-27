package com.pig4cloud.pig.common.core.util;

import lombok.Data;

@Data
public class KeyValue {
	public KeyValue(){}
	public KeyValue(String key, Object value){
		this.key = key;
		this.value = value;
	}
	public String key;
	public Object value;
}
