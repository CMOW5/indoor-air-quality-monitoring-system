package com.cmow5.iaqapi.infrastructure.vocs.service;

import com.cmow5.iaqapi.domain.vocs.repository.VocsRepository;
import com.cmow5.iaqapi.domain.vocs.service.VocsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cmow5.iaqapi.infrastructure.vocs.repository.VocsMongoRepository.VOCS_SENSOR_MONGO_REPOSITORY_BEAN;

@Configuration
public class VocsServiceBeans {

    @Bean
    public VocsService vocsService(@Qualifier(VOCS_SENSOR_MONGO_REPOSITORY_BEAN)VocsRepository vocsRepository) {
        return new VocsService(vocsRepository);
    }
}
