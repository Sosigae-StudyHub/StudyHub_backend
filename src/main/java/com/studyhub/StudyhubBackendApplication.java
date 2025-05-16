package com.studyhub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.studyhub.dao")
public class StudyhubBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudyhubBackendApplication.class, args);
	}

}
