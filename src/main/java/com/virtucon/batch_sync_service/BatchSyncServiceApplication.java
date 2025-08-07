package com.virtucon.batch_sync_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BatchSyncServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchSyncServiceApplication.class, args);
	}

}
