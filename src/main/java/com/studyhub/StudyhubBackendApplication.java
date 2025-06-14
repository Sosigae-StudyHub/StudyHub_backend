package com.studyhub;

import com.studyhub.config.PortOneProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EntityScan("com.studyhub.domain")
@EnableConfigurationProperties(PortOneProperties.class) // ✅ 추가
public class StudyhubBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudyhubBackendApplication.class, args);
	}

}
