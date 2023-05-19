package com.demo.cicd;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class CiCdApplicationTests {

	@Test
	void contextLoads() {

		assertEquals("True", 1, 1);
	}

}
