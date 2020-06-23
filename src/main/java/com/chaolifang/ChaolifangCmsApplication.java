package com.chaolifang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chaolifang.dao")
public class ChaolifangCmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChaolifangCmsApplication.class, args);
	}

}
