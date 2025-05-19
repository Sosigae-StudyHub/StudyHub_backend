package com.studyhub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.studyhub.domain")
public class StudyhubBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudyhubBackendApplication.class, args);
	}

}
