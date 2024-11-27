package com.brytech.prescription_service.filestorage;

import java.io.IOException;
import java.io.InputStream;

public interface FileStorageClient {

    String uploadFile(String containerName, String originalFileName, InputStream data, long length, String mimeType)
        throws IOException;
}
