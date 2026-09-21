package com.yj.yuaiagent;

import com.alibaba.cloud.ai.autoconfigure.dashscope.DashScopeConnectionProperties;
import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yj.yuaiagent.mapper")
public class YuAiAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(YuAiAgentApplication.class, args);
	}

}
