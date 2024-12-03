package com.sistema.sah.reservas.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.providers.DefaultAwsRegionProviderChain;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Slf4j
public class S3Config {

    @Bean
    public S3Client s3Client() {
        try {
            // Leer variables de entorno configuradas en Azure
            String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
            String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
            String regionName = System.getenv("AWS_REGION");

            if (accessKey == null || secretKey == null || regionName == null) {
                throw new IllegalStateException("AWS environment variables are not set");
            }

            // Configurar las credenciales de AWS
            AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

            // Crear el cliente S3 con las credenciales y región especificadas
            Region region = Region.of(regionName);
            S3Client client = S3Client.builder()
                    .region(region)
                    .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                    .build();

            log.info("S3 Client successfully created for region: {}", region);
            return client;
        } catch (Exception e) {
            log.error("Error creating S3 Client: ", e);
            throw e;
        }
    }

}
