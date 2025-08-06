package com.virtucon.batch_sync_service;

import org.springframework.boot.SpringApplication;

public class TestBatchSyncServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(BatchSyncServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
