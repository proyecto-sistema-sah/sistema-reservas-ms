package com.sistema.sah.reservas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.providers.DefaultAwsRegionProviderChain;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        // Configurar el cliente de S3 con DefaultCredentialsProvider y DefaultAwsRegionProviderChain
        return S3Client.builder()
                .region(new DefaultAwsRegionProviderChain().getRegion()) // Detectar región automáticamente
                .credentialsProvider(DefaultCredentialsProvider.create()) // Detectar credenciales automáticamente
                .build();
    }

}
