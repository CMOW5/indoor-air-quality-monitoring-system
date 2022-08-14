package com.cmow5.iaqapi.infrastructure.configs.mqtt.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigsParent.MQTT_CONFIGS_COLLECTION_NAME;

@Document(MQTT_CONFIGS_COLLECTION_NAME)
public class MqttConfigsParent {

    public static final String MQTT_CONFIGS_ID = "mqtt_configs";

    public static final String MQTT_CONFIGS_COLLECTION_NAME = "configs";

    public static final String MQTT_CONFIGS_FIELD = "mqttConfig";

    @Field(MQTT_CONFIGS_FIELD)
    private MqttConfigs mqttConfigs;

    public MqttConfigsParent() {

    }

    public MqttConfigs getMqttConfigs() {
        return mqttConfigs;
    }

    public void setMqttConfigs(MqttConfigs mqttConfigs) {
        this.mqttConfigs = mqttConfigs;
    }
}
