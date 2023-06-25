package com.findservices.serviceprovider.infra.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseStorage {

    @Value("${firebase.project_id}")
    private String projectId;
    @Value("${firebase.private_key_id}")
    private String privateKeyId;
    @Value("${firebase.private_key}")
    private String chunkPrivateKey;
    private String privateKey = "-----BEGIN PRIVATE KEY-----\\n%s==\\n-----END PRIVATE KEY-----\\n";
    @Value("${firebase.client_email}")
    private String clientEmail;
    @Value("${firebase.client_id}")
    private String clientId;
    @Value("${firebase.client_x509_cert_url}")
    private String clientX509CertUrl;
    @PostConstruct
    public void initialize() throws IOException {

        String credentialsString = "{\n" +
                "  \"type\": \"service_account\",\n" +
                "  \"project_id\": \"" + projectId + "\",\n" +
                "  \"private_key_id\": \"" + privateKeyId + "\",\n" +
                "  \"private_key\": \"" + String.format(privateKey, chunkPrivateKey) + "\",\n" +
                "  \"client_email\": \"" + clientEmail + "\",\n" +
                "  \"client_id\": \"" + clientId + "\",\n" +
                "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                "  \"client_x509_cert_url\": \"" + clientX509CertUrl + "\",\n" +
                "  \"universe_domain\": \"googleapis.com\"\n" +
                "}";

        InputStream serviceAccount = new ByteArrayInputStream(credentialsString.getBytes());
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp.initializeApp(options);
    }

    @Bean
    public StorageClient storageClient() {
        return StorageClient.getInstance();
    }

}
