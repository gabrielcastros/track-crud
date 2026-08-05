package com.gabriel.trackcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EntityScan("domain")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class TrackcrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackcrudApplication.class, args);
	}

}
