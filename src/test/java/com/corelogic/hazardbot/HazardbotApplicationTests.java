package com.corelogic.hazardbot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles({"mock", "test"})
@SpringBootTest
public class HazardbotApplicationTests {

	@Test
	public void contextLoads() {
	}

}
