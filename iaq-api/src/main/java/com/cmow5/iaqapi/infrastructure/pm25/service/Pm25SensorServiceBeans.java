package com.cmow5.iaqapi.infrastructure.pm25.service;

import com.cmow5.iaqapi.domain.pm25.repository.Pm25SensorRepository;
import com.cmow5.iaqapi.domain.pm25.service.Pm25SensorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Pm25SensorServiceBeans {

    @Bean
    public Pm25SensorService pm25SensorService(Pm25SensorRepository pm25SensorRepository) {
        return new Pm25SensorService(pm25SensorRepository);
    }
}
