package com.group4.gutenshareweb.infrastructure.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;

@ConfigurationProperties(value = "content-repo")
@Validated
class FilesystemStorageProperties {

    private FileSystemResource basePath;

    public FileSystemResource getBasePath() {
        return basePath;
    }

    public void setBasePath(FileSystemResource basePath) {
        this.basePath = basePath;
    }
}
