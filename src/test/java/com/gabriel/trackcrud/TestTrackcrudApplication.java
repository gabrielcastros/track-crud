package com.gabriel.trackcrud;

import org.springframework.boot.SpringApplication;

public class TestTrackcrudApplication {

	public static void main(String[] args) {
		SpringApplication.from(TrackcrudApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
