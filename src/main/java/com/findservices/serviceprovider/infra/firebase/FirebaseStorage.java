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

@Configuration
public class FirebaseStorage {

    @Value("${firebase.project_id}")
    private String projectId;
    @Value("${firebase.private_key_id}")
    private String privateKeyId;
    @Value("${firebase.private_key1}")
    private String chunkPrivateKey1;
    @Value("${firebase.private_key2}")
    private String chunkPrivateKey2;
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
                "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCWc+0IhkezrNqI\\nwZei/mWbafSElwEXuNH1yfjS9uag2mUn0wSPqXjq7Xx9bEA+WTPYJEFtMVZpmTZd\\nzf33beuMnP+eJ8faIlL9bI4w2/rqYnWNItRIYHchzTEXe3w0GYN2JnMYkxWE8fXo\\n" + chunkPrivateKey1 + "\\nfjWYvmfauiQImQWfjCtOg6fcw75la0L+6WwyuRgBGoLf5F+ZncypsrI7sqZRW0jU\\nEPMU9j+FCL1Iq+lsxQve00YX1DxY5YkjyaTbtnjKnBMpHmlbpoX6K03oe8wDnE5h\\nTyk2dDpDAgMBAAECggEADmlFQ6NFG/dLKX1Nro2qouPXyZmDP8fyyJXTkTWYKsEw\\nnkturOohTHN6ELN9RduahTIX2EM5ET1dQUf5cyliviHNtFMAKCtQgrjSt+hiVPAg\\nbWfwKIyg7KpwcNzhrUXiWz9cl/aCwazBjstKF20e0qHwfJ0aRuqCgHibWXsnQsNT\\nuHLyQn00nBmxx/5hAFAa0NlBbvZ5GQ0MItf8ju/kEcMqAcdkEwOKjLvOL5E/hC0R\\nNuUmqX6woTpjgObf4+jbhVWURS6oqGF2CovbTPmqD4Y6pnpvRpd5hdudJcyWGQu8\\nu9+rvtEsb2jtNSrvXaIpsVK/EytqCwIsytl7EuKx8QKBgQDKkkLEhbGewsW1+324\\nYluXzyXhhopV9qV+V4YqC0T00JeJ+izqlbsfo7Xjpf5yUovqwohAG453BQSfmarx\\nLDok2AKME6OWIBC2ypRlzfA07bUVVHW40QSvykevKL7OCrzETOkWrjaTCf48Y7GX\\nSc30wQ0f0DIy1nWfkuX5RHNPUQKBgQC+Ipe36dD20gxoARt1O2lT0FSqDP2oTs0V\\nIYEH8ljixaey1TYZBgon+mSYvz7cUVMQYc0dtZrkhUp8OvlP+RfokiCjUl8fPSyJ\\n6+JOPM87F4dwtU3ffqomZkOSJU/0h3ZxyQQCXK+e7v1c1V1NXfD7RgcFVo9TDq3b\\nry6mYKaTUwKBgB7alCzWqC0kQXTsb0OsR4Mh3AUzttJNFPvfXOH+gWxAcTRk1H6O\\n" + chunkPrivateKey2 + "\\nBG66Ap0dOaKsZAAKLt5zfoB63yxXiu8eIK6M0PDthxfTLms1tq12zZFhAoGAI7jq\\nGmKyoPzb2dqDZyYvZtDYZ06jhbCwdUzjKjQNISYTmNTYIiFxXQt9RPmBfPFGkuhd\\nGjzZih/E28xUtQLOzCJQQX8jYgIjURJhZWOlZ6vHBZT6uYfZvfcusNGT0RR2W3Bx\\n8WOql0i9j2iC7Bs/QGZqwUT/DpLOdVzXfejgwwMCgYB6GuP4J7+9+kwTiQAt8yDa\\nTySVJfk4mUf9yCKeKR6hx/jQf5xao6fdrqlCiyrhqze+X6XtIv8QnrdHPrEcF78o\\nFZpfShlcPmqyXMQFFkLRgzkK7ZqaUOPWmQg7AHobPcHx4XoF7m8zPn6A3muOH7PL\\nZFyp5/Yvg8sYpMNZOrIeuw==\\n-----END PRIVATE KEY-----\\n\",\n" +
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
