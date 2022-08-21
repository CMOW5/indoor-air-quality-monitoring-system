package com.cmow5.iaqapi.infrastructure.temperature.service;

import com.cmow5.iaqapi.domain.temperature.repository.TempSensorRepository;
import com.cmow5.iaqapi.domain.temperature.service.TempSensorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TempSensorServiceBeans {

    @Bean
    public TempSensorService tempSensorService(TempSensorRepository tempSensorRepository) {
        return new TempSensorService(tempSensorRepository);
    }
}
