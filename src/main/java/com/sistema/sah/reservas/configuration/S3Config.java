package com.sistema.sah.reservas.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.providers.DefaultAwsRegionProviderChain;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Slf4j
public class S3Config {

    @Bean
    public S3Client s3Client() {
        try {
            Region region = new DefaultAwsRegionProviderChain().getRegion();
            log.info("Detected AWS Region: {}", region);

            S3Client client = S3Client.builder()
                    .region(region)
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .build();
            log.info("S3 Client successfully created.");
            return client;
        } catch (Exception e) {
            log.error("Error creating S3 Client: ", e);
            throw e;
        }
    }

}
