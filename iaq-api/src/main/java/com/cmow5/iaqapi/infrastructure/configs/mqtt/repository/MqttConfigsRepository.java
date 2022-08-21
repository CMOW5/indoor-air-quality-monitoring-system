package com.cmow5.iaqapi.infrastructure.configs.mqtt.repository;

import com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigs;

public interface MqttConfigsRepository {

    MqttConfigs findMqttConfigs();
}
