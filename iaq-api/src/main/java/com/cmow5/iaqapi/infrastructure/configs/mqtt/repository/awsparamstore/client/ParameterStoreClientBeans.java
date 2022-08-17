package com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.awsparamstore.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;

import static com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.awsparamstore.client.ParameterStoreClientBeans.PARAM_STORE_PROFILE;

@Configuration
@Profile(PARAM_STORE_PROFILE)
public class ParameterStoreClientBeans {

    public static final String PARAM_STORE_PROFILE = "paramstore";

    @Bean
    public SsmClient ssmClient() {
        return SsmClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }
}
