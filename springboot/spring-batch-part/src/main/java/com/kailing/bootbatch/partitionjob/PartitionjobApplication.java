package com.kailing.bootbatch.partitionjob;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableBatchProcessing
@EnableConfigurationProperties
@EnableBatchIntegration
public class PartitionjobApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartitionjobApplication.class, args);
	}
}
