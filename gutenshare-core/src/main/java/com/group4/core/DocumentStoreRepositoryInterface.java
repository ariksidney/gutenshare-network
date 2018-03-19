package com.group4.core;

import java.io.InputStream;
import java.nio.file.Path;

public interface DocumentStoreRepositoryInterface {

    Path storeDocument(Document document, InputStream inputStream);

    byte[] getDocument(Document document);
}
