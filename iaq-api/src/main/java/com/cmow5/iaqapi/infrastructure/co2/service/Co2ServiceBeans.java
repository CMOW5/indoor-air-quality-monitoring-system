package com.cmow5.iaqapi.infrastructure.co2.service;

import com.cmow5.iaqapi.domain.co2.repository.Co2Repository;
import com.cmow5.iaqapi.domain.co2.service.Co2Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cmow5.iaqapi.infrastructure.co2.repository.Co2MongoRepository.CO2_SENSOR_MONGO_REPOSITORY_BEAN;

@Configuration
public class Co2ServiceBeans {

    @Bean
    public Co2Service co2Service(@Qualifier(CO2_SENSOR_MONGO_REPOSITORY_BEAN) Co2Repository co2Repository) {
        return new Co2Service(co2Repository);
    }
}
