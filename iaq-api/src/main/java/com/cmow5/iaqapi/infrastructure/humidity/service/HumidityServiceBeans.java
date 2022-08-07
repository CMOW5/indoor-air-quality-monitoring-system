package com.cmow5.iaqapi.infrastructure.humidity.service;

import com.cmow5.iaqapi.domain.humidity.repository.HumidityRepository;
import com.cmow5.iaqapi.domain.humidity.service.HumidityService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cmow5.iaqapi.infrastructure.humidity.repository.HumidityMongoRepository.HUMIDITY_SENSOR_MONGO_REPOSITORY_BEAN;

@Configuration
public class HumidityServiceBeans {

    @Bean
    public HumidityService humidityService(@Qualifier(HUMIDITY_SENSOR_MONGO_REPOSITORY_BEAN) HumidityRepository humidityRepository) {
        return new HumidityService(humidityRepository);
    }
}
