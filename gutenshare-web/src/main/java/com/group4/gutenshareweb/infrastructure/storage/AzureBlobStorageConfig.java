package com.group4.gutenshareweb.infrastructure.storage;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@EnableConfigurationProperties(value = AzureBlobStorageProperties.class)
@Configuration
public class AzureBlobStorageConfig {

    private final AzureBlobStorageProperties azureBlobStorageProperties;

    public AzureBlobStorageConfig(AzureBlobStorageProperties azureBlobStorageProperties) {
        this.azureBlobStorageProperties = azureBlobStorageProperties;
    }

    @Profile("azure")
    @Bean
    public AzureBlobStorageRepository azureBlobStorageRepository() throws IOException {
        return new AzureBlobStorageRepository(this.azureBlobStorageProperties.getAccountName(), this
                .azureBlobStorageProperties.getAccountKey(), this.azureBlobStorageProperties.getContainerName());
    }
}
