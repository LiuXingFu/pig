package com.pig4cloud.pig.admin.api.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aliyun")
@Component
@Setter
@Getter
public class AliyunConfigProperties {
	private String stsendpoint;

	private String ossendpoint;

	private String accessKeyId;

	private String accessKeySecret;

	private String roleArn;

	private String roleSessionName;
}
