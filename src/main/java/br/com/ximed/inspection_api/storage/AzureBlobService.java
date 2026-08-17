package br.com.ximed.inspection_api.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AzureBlobService {

    private final BlobContainerClient containerClient;

    public AzureBlobService(
            @Value("${azure.storage.connection-string}") String connectionString,
            @Value("${azure.storage.container-name}") String containerName) {
        
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        
        this.containerClient = blobServiceClient.getBlobContainerClient(containerName);
    }

    public String uploadFile(MultipartFile file, String virtualFolder) throws IOException {
        String filename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String blobName = virtualFolder != null && !virtualFolder.isEmpty() ? virtualFolder + "/" + filename : filename;

        BlobClient blobClient = containerClient.getBlobClient(blobName);
        blobClient.upload(file.getInputStream(), file.getSize(), true);
        if (file.getContentType() != null) {
            blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType(file.getContentType()));
        }

        return blobClient.getBlobUrl();
    }

    public String uploadFile(byte[] data, String blobName) {
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        blobClient.upload(new java.io.ByteArrayInputStream(data), data.length, true);
        
        String contentType = "application/octet-stream";
        if (blobName.toLowerCase().endsWith(".html")) contentType = "text/html; charset=utf-8";
        else if (blobName.toLowerCase().endsWith(".pdf")) contentType = "application/pdf";
        blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType(contentType));
        
        return blobClient.getBlobUrl();
    }

    public void deleteFileByUrl(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) return;

        try {
            java.net.URL url = new java.net.URL(fileUrl);
            String path = url.getPath(); // Format: /<containerName>/<blobName>
            String containerPrefix = "/" + containerClient.getBlobContainerName() + "/";

            if (path.startsWith(containerPrefix)) {
                String blobName = path.substring(containerPrefix.length());
                BlobClient blobClient = containerClient.getBlobClient(blobName);
                blobClient.deleteIfExists();
            }
        } catch (Exception e) {
            System.err.println("Erro ao deletar blob " + fileUrl + ": " + e.getMessage());
        }
    }
}
