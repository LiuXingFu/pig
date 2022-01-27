package com.pig4cloud.pig.casee;

import com.pig4cloud.pig.common.feign.annotation.EnablePigFeignClients;
import com.pig4cloud.pig.common.job.annotation.EnablePigXxlJob;
import com.pig4cloud.pig.common.security.annotation.EnablePigResourceServer;
import com.pig4cloud.pig.common.swagger.annotation.EnablePigSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaojun archetype
 * <p>
 * 项目启动类
 */
@EnablePigXxlJob
@EnablePigSwagger2
@EnablePigResourceServer
@EnablePigFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class P10CaseeApplication {
    public static void main(String[] args) {
        SpringApplication.run(P10CaseeApplication.class, args);
    }
}
