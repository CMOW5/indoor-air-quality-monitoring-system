package com.cmow5.iaqapi.infrastructure.temperature.service;

import com.cmow5.iaqapi.domain.temperature.repository.TempSensorRepository;
import com.cmow5.iaqapi.domain.temperature.service.TempSensorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cmow5.iaqapi.infrastructure.temperature.repository.TempSensorMongoRepository.TEMP_SENSOR_MONGO_REPOSITORY_BEAN;

@Configuration
public class TempSensorServiceBeans {

    @Bean
    public TempSensorService tempSensorService(@Qualifier(TEMP_SENSOR_MONGO_REPOSITORY_BEAN) TempSensorRepository tempSensorRepository) {
        return new TempSensorService(tempSensorRepository);
    }
}
