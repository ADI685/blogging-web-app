package com.adi685.blogging_web_app;

import com.adi685.blogging_web_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BloggingWebAppApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	void testService() {
		String className = userService.getClass().getName();
		String packageName = userService.getClass().getPackageName();
		System.out.println(className + " ::::::::::::  " + packageName);
	}

}
