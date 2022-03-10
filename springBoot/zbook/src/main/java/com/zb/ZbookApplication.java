package com.zb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zb"})
@MapperScan("com.zb.mapper")
public class ZbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZbookApplication.class, args);
	}

}
