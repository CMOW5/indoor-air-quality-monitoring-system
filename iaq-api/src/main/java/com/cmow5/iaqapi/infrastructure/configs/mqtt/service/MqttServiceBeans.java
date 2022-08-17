package com.cmow5.iaqapi.infrastructure.configs.mqtt.service;

import com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.MqttConfigsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttServiceBeans {

    @Bean
    public MqttConfigsService mqttConfigsService(MqttConfigsRepository mqttConfigsRepository) {
        return new MqttConfigsService(mqttConfigsRepository);
    }
}
