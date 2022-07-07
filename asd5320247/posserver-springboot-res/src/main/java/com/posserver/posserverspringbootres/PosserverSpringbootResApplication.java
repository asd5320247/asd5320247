package com.posserver.posserverspringbootres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("package com.posserver.posserverspringbootres.dao")
public class PosserverSpringbootResApplication {
	public static void main(String[] args) {
		SpringApplication.run(PosserverSpringbootResApplication.class, args);
	}

}
