package com.kailing.bootbatch.partitionjob;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableBatchProcessing
@EnableConfigurationProperties
public class PartitionjobApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartitionjobApplication.class, args);
	}
}
