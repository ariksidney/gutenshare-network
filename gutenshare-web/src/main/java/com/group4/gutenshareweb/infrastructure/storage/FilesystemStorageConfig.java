package com.group4.gutenshareweb.infrastructure.storage;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@EnableConfigurationProperties(value = FilesystemStorageProperties.class)
@Configuration
public class FilesystemStorageConfig {

    private final FilesystemStorageProperties filesystemStorageProperties;

    public FilesystemStorageConfig(FilesystemStorageProperties contentRepoProperties) {
        this.filesystemStorageProperties = contentRepoProperties;
    }

    @Bean
    public FilesystemDocumentStorageRepository documentContentRepository() throws IOException {
        return new FilesystemDocumentStorageRepository(this.filesystemStorageProperties.getBasePath());
    }
}
