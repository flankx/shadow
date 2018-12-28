package com.denachina.shadow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShadowApplication /*extends CommandLineRunner*/{

	public static void main(String[] args) {
		SpringApplication.run(ShadowApplication.class, args);
	}

/*	@Override
	public void run(String... arg0) throws Exception {
		// clear all record if existed before do the tutorial with new data
		repository.deleteAll();
	}*/

}

