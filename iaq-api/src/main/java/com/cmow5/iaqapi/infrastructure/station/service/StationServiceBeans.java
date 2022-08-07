package com.cmow5.iaqapi.infrastructure.station.service;

import com.cmow5.iaqapi.domain.station.repository.StationRepository;
import com.cmow5.iaqapi.domain.station.service.StationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cmow5.iaqapi.infrastructure.station.repository.StationMongoRepository.STATION_MONGO_REPOSITORY_BEAN;


@Configuration
public class StationServiceBeans {

    @Bean
    public StationService stationService(@Qualifier(STATION_MONGO_REPOSITORY_BEAN) StationRepository tempSensorRepository) {
        return new StationService(tempSensorRepository);
    }
}
