package com.findservices.serviceprovider.user.service;

import com.findservices.serviceprovider.common.constants.FirebaseConstants;
import com.findservices.serviceprovider.user.model.ImageId;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.findservices.serviceprovider.common.constants.FirebaseConstants.FIREBASE_BUCKET;

@Service
public class FirebaseService {

    @Autowired
    private StorageClient storageClient;

    public ImageId uploadFile(MultipartFile multipartFile) {
        try {
            String objectName = generateFileName(multipartFile);

            File file = convertMultiPartToFile(multipartFile);
            Path filePath = file.toPath();

            BlobId blobId = BlobId.of(FirebaseConstants.FIREBASE_BUCKET, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();

            storageClient.bucket(FIREBASE_BUCKET).getStorage().create(blobInfo, Files.readAllBytes(filePath));

            file.delete();
            return new ImageId(blobId.getName());
        } catch (Exception e) {
            return new ImageId(null);
        }
    }

    public void deleteFile(String fileName) {
        try {
            BlobId blobId = BlobId.of(FirebaseConstants.FIREBASE_BUCKET, fileName);
            storageClient.bucket(FIREBASE_BUCKET).getStorage().delete(blobId);
        } catch (Exception e) {

        }
    }

    public String getImageUrl(String fileName) {
        try {
            BlobId blobId = BlobId.of(FirebaseConstants.FIREBASE_BUCKET, fileName);
            Blob blob = storageClient.bucket(FIREBASE_BUCKET).getStorage().get(blobId);

            Duration duration = Duration.ofMinutes(5);

            URL imageUrl = blob.signUrl(duration.toMillis(), TimeUnit.MILLISECONDS);

            return imageUrl.getFile();
        } catch (Exception e) {
            return null;
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }
}
