package com.group4.gutenshareweb.infrastructure.storage;

import com.group4.core.Document;
import com.group4.core.DocumentStoreRepository;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FilesystemDocumentStorageRepository implements DocumentStoreRepository {

    private Path contentPath;

    public FilesystemDocumentStorageRepository(FileSystemResource basePath) throws IOException {
        this.contentPath = Files.createDirectories(basePath.getFile().toPath());
    }

    @Override
    public Path storeDocument(String title, Document document, InputStream inputStream) {
        Path fileToCreate = buildFilePath(this.contentPath, document.createFilename());
        try {
            Files.copy(inputStream, fileToCreate, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            //TODO add logging
            e.printStackTrace();
        }
        return this.contentPath.relativize(fileToCreate);
    }

    @Override
    public byte[] getDocument(Document document) {
        return new byte[0];
    }

    private static Path buildFilePath(Path basePath, String file) {
        return Paths.get(String.format("%s/%s", basePath, file));
    }
}
