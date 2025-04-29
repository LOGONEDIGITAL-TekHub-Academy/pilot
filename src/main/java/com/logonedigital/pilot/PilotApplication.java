package com.logonedigital.pilot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PilotApplication {

	private static final Logger logger = LoggerFactory.getLogger(PilotApplication.class);

	public static void main(String[] args) {
		try {
			SpringApplication.run(PilotApplication.class, args);
			logger.info("APPLICATION SUCCESSFULLY STARTED ON PORT: 8900");
		} catch (Exception e) {
			logger.error("ERROR WHILE STARTING APPLICATION: {}", e.getMessage(), e);
			throw e;
		}
	}

}
