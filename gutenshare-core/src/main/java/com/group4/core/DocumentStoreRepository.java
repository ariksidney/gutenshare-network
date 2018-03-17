package com.group4.core;

import java.io.InputStream;
import java.nio.file.Path;

public interface DocumentStoreRepository {

    Path storeDocument(String title, Document document, InputStream inputStream);

    byte[] getDocument(Document document);
}
