package com.group4.gutenshareweb.infrastructure.storage;

import com.group4.core.Document;
import com.group4.core.DocumentStoreRepositoryInterface;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;

public class AzureBlobStorageRepository implements DocumentStoreRepositoryInterface {

    private final String storageConnectionString;
    private final String containerName;
    private String tempDir = System.getProperty("java.io.tmpdir");
    private CloudStorageAccount storageAccount;
    private CloudBlobClient blobClient = null;
    private CloudBlobContainer container = null;

    public AzureBlobStorageRepository(String accountName, String accountKey, String containerName) {
        storageConnectionString = buildConnectionString(accountName, accountKey);
        this.containerName = containerName;
        initializeBlobStorage();
    }

    @Override
    public Path storeDocument(Document document, InputStream inputStream) {
        try {
            File sourceFile = new File(String.format("%s/%s", tempDir, document.createFilename()));
            FileUtils.copyInputStreamToFile(inputStream, sourceFile);
            CloudBlockBlob blob = container.getBlockBlobReference(sourceFile.getName());
            blob.uploadFromFile(sourceFile.getAbsolutePath());
            return Paths.get(sourceFile.getAbsolutePath()).getFileName();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public byte[] getDocument(Document document) {
        byte[] file = new byte[0];
        try {
            CloudBlockBlob blob = container.getBlockBlobReference(document.getPathToFile().toString());
            File fileToDownload = new File(String.format("%s/%s", tempDir, document.createFilename()));
            blob.downloadToFile(fileToDownload.getAbsolutePath());
            file = Files.readAllBytes(fileToDownload.toPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void initializeBlobStorage() {
        try {
            storageAccount = CloudStorageAccount.parse(storageConnectionString);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(containerName);
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new
                    OperationContext());
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private String buildConnectionString(String accountName, String accountKey) {
        return String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows" +
                        ".net",
                accountName, accountKey);
    }
}
