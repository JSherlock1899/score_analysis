package com.slxy.analysis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.slxy.analysis.teacher.mapper")
public class AnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalysisApplication.class, args);
	}

}
