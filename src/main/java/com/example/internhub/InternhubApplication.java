package com.example.internhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class InternhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternhubApplication.class, args);
		System.out.println(UUID.randomUUID());
		System.out.println(UUID.randomUUID());
//		System.out.println("hello MAIN");
	}

}
