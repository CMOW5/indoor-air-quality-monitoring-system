package com.cmow5.iaqapi.infrastructure.station.service;

import com.cmow5.iaqapi.domain.station.repository.StationRepository;
import com.cmow5.iaqapi.domain.station.service.StationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring injects the appropriate StationRepository depending on the profile (mongo, timestream, etc).
 * We can also create a different @Bean definition and inject the explicit Bean using a Qualifier. However, This will
 * require setting each StationService with the appropriate StationRepository, but we have to also set the profile
 * example:
 *
 *     @Bean
 *     @Profile(MONGO_PROFILE)
 *     public StationService stationService(@Qualifier(MONGO_STATION_REPO) StationRepository tempSensorRepository) {
 *         return new StationService(tempSensorRepository);
 *     }
 *
 *     @Bean
 *     @Profile(AWS_TIMESTREAM)
 *     public StationService stationService(@Qualifier(TIMESTREAM_STATION_REPO) StationRepository tempSensorRepository) {
 *         return new StationService(tempSensorRepository);
 *     }
 *
 * Should let spring do it's magic, or should we explicitly define the Beans for each database ??
 */
@Configuration
public class StationServiceBeans {

    @Bean
    public StationService stationService(StationRepository tempSensorRepository) {
        return new StationService(tempSensorRepository);
    }
}
