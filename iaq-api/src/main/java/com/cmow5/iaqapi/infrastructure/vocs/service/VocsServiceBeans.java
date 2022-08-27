package com.cmow5.iaqapi.infrastructure.vocs.service;

import com.cmow5.iaqapi.domain.vocs.repository.VocsRepository;
import com.cmow5.iaqapi.domain.vocs.service.VocsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class VocsServiceBeans {

    @Bean
    public VocsService vocsService(VocsRepository vocsRepository) {
        return new VocsService(vocsRepository);
    }
}
