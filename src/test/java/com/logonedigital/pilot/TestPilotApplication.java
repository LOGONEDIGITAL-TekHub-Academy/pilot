package com.logonedigital.pilot;

import org.springframework.boot.SpringApplication;

public class TestPilotApplication {

	public static void main(String[] args) {
		SpringApplication.from(PilotApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
