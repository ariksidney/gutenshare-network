package com.group4.core;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * Repository interface to interact with Document database table.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */
public interface DocumentStoreRepositoryInterface {

    /**
     * Stores a document. Storage location is implementation specific.
     *
     * @param document The document to store
     * @param inputStream The physical document as inputStream
     * @return relative path to the document.
     */
    Path storeDocument(Document document, InputStream inputStream);

    /**
     * Gets a document from the implemented storage.
     *
     * @param document The document to load
     * @return Physical document as byte array
     */
    byte[] getDocument(Document document);
}
