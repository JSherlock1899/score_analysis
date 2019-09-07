package com.slxy.analysis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalysisApplicationTests {


	@Test
	public void contextLoads() {
		Jedis jedis = new Jedis("43.235.43.59",6379);
		System.out.println(jedis.ping());
		System.out.println(jedis.get("Back1"));
	}

}
