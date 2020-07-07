package com.chaolifang;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.spring4all.mongodb.EnableMongoPlus;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.chaolifang.dao")
@EnableMongoPlus
public class ChaolifangCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChaolifangCmsApplication.class, args);
    }

}
