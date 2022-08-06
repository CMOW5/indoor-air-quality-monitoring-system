package com.cmow5.iaqapi.infrastructure.pm25.service;

import com.cmow5.iaqapi.domain.pm25.repository.Pm25SensorRepository;
import com.cmow5.iaqapi.domain.pm25.service.Pm25SensorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cmow5.iaqapi.infrastructure.pm25.repository.Pm25SensorMongoRepository.PM25_SENSOR_MONGO_REPOSITORY_BEAN;

@Configuration
public class Pm25SensorServiceBeans {

    @Bean
    public Pm25SensorService pm25SensorService(@Qualifier(PM25_SENSOR_MONGO_REPOSITORY_BEAN) Pm25SensorRepository pm25SensorRepository) {
        return new Pm25SensorService(pm25SensorRepository);
    }
}
