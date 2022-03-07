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

package com.pig4cloud.pig.admin.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.pig4cloud.pig.admin.api.dto.UpdataData;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.pig4cloud.pig.admin.api.config.AliyunConfigProperties;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 公共api
 *
 * @author xiaojun
 * @date 2021-09-03 17:14:47
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comm")
@Api(value = "comm", tags = "公共api")
public class CommController {


	private final AliyunConfigProperties aliyunConfigProperties;

	/**
	 * 上传文件
	 * 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param file 资源
	 * @return R(bucketName, filename)
	**/
	 @PostMapping("/pub/upload")
	public R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

		 String ossendpoint = aliyunConfigProperties.getOssendpoint();
		 String accessKeyId = aliyunConfigProperties.getAccessKeyId();
		 String accessKeySecret = aliyunConfigProperties.getAccessKeySecret();

		 OSS ossClient = new OSSClientBuilder().build(ossendpoint, accessKeyId, accessKeySecret);
		 PutObjectResult result = null;
		 try {
		 	String fileName = "upload/"+System.currentTimeMillis()+"-"+file.getOriginalFilename();
		 	ossClient.putObject(CommonConstants.BUCKET_NAME, fileName, file.getInputStream());
		 	String url = "https://"+CommonConstants.BUCKET_NAME+"."+ossendpoint+"/"+fileName;
			return R.ok(url);
		 } catch (IOException e) {
			 e.printStackTrace();
			 return R.failed("文件上传异常");
		 } finally {
			 ossClient.shutdown();
		 }
	}

	/**
	 * 上传文件
	 * 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param file 资源
	 * @return R(bucketName, filename)
	 **/
	@PostMapping("/pub/formUpload")
	public R formUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

		String ossendpoint = aliyunConfigProperties.getOssendpoint();
		String accessKeyId = aliyunConfigProperties.getAccessKeyId();
		String accessKeySecret = aliyunConfigProperties.getAccessKeySecret();

		OSS ossClient = new OSSClientBuilder().build(ossendpoint, accessKeyId, accessKeySecret);
		PutObjectResult result = null;
		try {
			String fileName = "upload/"+System.currentTimeMillis()+"-"+file.getOriginalFilename();
			ossClient.putObject(CommonConstants.BUCKET_NAME, fileName, file.getInputStream());
			String url = "https://"+CommonConstants.BUCKET_NAME+"."+ossendpoint+"/"+fileName;
			UpdataData updataData=new UpdataData();
			updataData.setName(fileName);
			updataData.setUrl(url);
			return R.ok(updataData);
		} catch (IOException e) {
			e.printStackTrace();
			return R.failed("文件上传异常");
		} finally {
			ossClient.shutdown();
		}
	}

	/**
	 * 获取文件上传参数
	 *
	 * @return
	 */
	@ApiOperation(value = "获取文件上传参数", notes = "获取文件上传参数")
	@GetMapping("/pub/alists")
	public R alists() {
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunConfigProperties.getAccessKeyId(), aliyunConfigProperties.getAccessKeySecret());
		IAcsClient client = new DefaultAcsClient(profile);

		//构造请求，设置参数。关于参数含义和设置方法，请参见API参考。
		AssumeRoleRequest request = new AssumeRoleRequest();
		request.setRegionId("cn-hangzhou");
		request.setRoleArn(aliyunConfigProperties.getRoleArn());
		request.setRoleSessionName(aliyunConfigProperties.getRoleSessionName());

		//发起请求，并得到响应。
		try {
			AssumeRoleResponse response = client.getAcsResponse(request);
			return R.ok(response);
		} catch (ServerException e) {
			e.printStackTrace();
			log.error("阿里云配置错误");
			return R.failed("阿里云配置错误，请联系管理员。");
		} catch (ClientException e) {
			log.error("ErrCode:" + e.getErrCode());
			log.error("ErrMsg:" + e.getErrMsg());
			log.error("RequestId:" + e.getRequestId());
			return R.failed("阿里云配置错误，请联系管理员。");
		}


	}


}
