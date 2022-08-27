package com.cmow5.iaqapi.infrastructure.configs.mqtt.web;

import com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigs;
import com.cmow5.iaqapi.infrastructure.configs.mqtt.service.MqttConfigsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttConfigsController {

    private final MqttConfigsService mqttConfigsService;

    public MqttConfigsController(MqttConfigsService mqttConfigsService) {
        this.mqttConfigsService = mqttConfigsService;
    }

    @GetMapping("/configs/mqtt")
    public MqttConfigs findMqttConfigs() {
        return mqttConfigsService.findMqttConfigs();
    }
}
