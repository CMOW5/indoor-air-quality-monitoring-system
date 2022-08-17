package com.cmow5.iaqapi.infrastructure.humidity.service;

import com.cmow5.iaqapi.domain.humidity.repository.HumidityRepository;
import com.cmow5.iaqapi.domain.humidity.service.HumidityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HumidityServiceBeans {

    @Bean
    public HumidityService humidityService(HumidityRepository humidityRepository) {
        return new HumidityService(humidityRepository);
    }
}
