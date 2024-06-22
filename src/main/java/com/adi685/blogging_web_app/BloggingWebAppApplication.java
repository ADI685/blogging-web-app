package com.adi685.blogging_web_app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BloggingWebAppApplication  {

	public static void main(String[] args) {
		SpringApplication.run(BloggingWebAppApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

/*
	implements CommandLineRunner
	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.getPasswordEncoder().encode("pwd"));
	}
 */

}
