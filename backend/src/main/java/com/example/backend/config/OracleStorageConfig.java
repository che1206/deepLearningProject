package com.example.backend.config;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimplePrivateKeySupplier;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.nio.file.Paths;

@Configuration
public class OracleStorageConfig {

    // application.properties 또는 yml에서 주입받기
    @Value("${oracle.config.region}")
    private String regionName;

    @Bean
    public ObjectStorage objectStorageClient() throws Exception {
        final String CONFIG_PATH = "C:\\.oci\\config";
        final String PROFILE = "DEFAULT";

        AuthenticationDetailsProvider provider =
                new ConfigFileAuthenticationDetailsProvider(CONFIG_PATH, PROFILE);

        // ✅ 문자열 regionName을 Region enum으로 변환 후 설정
        ObjectStorageClient client = new ObjectStorageClient(provider);
        client.setRegion(Region.fromRegionId(regionName));

        return client;
    }
}
