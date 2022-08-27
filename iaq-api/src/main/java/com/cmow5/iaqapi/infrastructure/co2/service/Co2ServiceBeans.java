package com.cmow5.iaqapi.infrastructure.co2.service;

import com.cmow5.iaqapi.domain.co2.repository.Co2Repository;
import com.cmow5.iaqapi.domain.co2.service.Co2Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Co2ServiceBeans {

    @Bean
    public Co2Service co2Service(Co2Repository co2Repository) {
        return new Co2Service(co2Repository);
    }
}
