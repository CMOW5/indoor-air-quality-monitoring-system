package com.cmow5.iaqapi.infrastructure.pm10.service;

import com.cmow5.iaqapi.domain.pm10.repository.Pm10Repository;
import com.cmow5.iaqapi.domain.pm10.service.Pm10Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Pm10ServiceBeans {

    @Bean
    public Pm10Service pm10Service(Pm10Repository pm10Repository) {
        return new Pm10Service(pm10Repository);
    }
}
