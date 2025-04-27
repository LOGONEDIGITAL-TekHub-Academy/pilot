package com.logonedigital.pilot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class PilotApplicationTests {

	@Test
	void contextLoads() {
	}

}
