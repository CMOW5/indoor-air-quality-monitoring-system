package com.cmow5.iaqapi.infrastructure.configs.mqtt.web;

import com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigs;
import com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.MqttConfigsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttConfigsController {

    private final MqttConfigsRepository mqttConfigsRepository;

    public MqttConfigsController(MqttConfigsRepository mqttConfigsRepository) {
        this.mqttConfigsRepository = mqttConfigsRepository;
    }

    @GetMapping("/configs/mqtt")
    public MqttConfigs findMqttConfigs() {
        return mqttConfigsRepository.findMqttConfigs();
    }
}
