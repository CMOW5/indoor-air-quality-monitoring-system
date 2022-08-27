package com.cmow5.iaqapi.infrastructure.configs.mqtt.service;

import com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigs;
import com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.MqttConfigsRepository;

public class MqttConfigsService {

    private final MqttConfigsRepository mqttConfigsRepository;

    public MqttConfigsService(MqttConfigsRepository mqttConfigsRepository) {
        this.mqttConfigsRepository = mqttConfigsRepository;
    }

    public MqttConfigs findMqttConfigs() {
        return mqttConfigsRepository.findMqttConfigs();
    }

}
