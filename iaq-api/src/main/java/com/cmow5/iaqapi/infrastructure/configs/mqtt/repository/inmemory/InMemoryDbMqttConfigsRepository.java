package com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.inmemory;

import com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigs;
import com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.MqttConfigsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import static com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.inmemory.InMemoryDbMqttConfigsRepository.IN_MEMORY_DB_MQTT_CONFIGS_REPOSITORY_BEAN;
import static com.cmow5.iaqapi.infrastructure.general.database.inmemory.InMemoryDbConfig.IN_MEMORY_PROFILE;

//@Repository(IN_MEMORY_DB_MQTT_CONFIGS_REPOSITORY_BEAN)
//@Profile(IN_MEMORY_PROFILE)
public class InMemoryDbMqttConfigsRepository implements MqttConfigsRepository {

    public static final String IN_MEMORY_DB_MQTT_CONFIGS_REPOSITORY_BEAN = "inMemoryDbMqttConfigsRepository";

    private static final Logger log = LogManager.getLogger(InMemoryDbMqttConfigsRepository.class);

    private final static MqttConfigs HARDCODED_CONFIGS;

    static {
        // AWS
        //HARDCODED_CONFIGS = new MqttConfigs("wss://<iot-endpoint>/mqtt", "us-east-1",
        //        "<SECRET>");

        // LOCAL
        HARDCODED_CONFIGS = new MqttConfigs("ws://localhost:9001/mqtt", "us-east-1",
                "<SECRET>");

        //HARDCODED_CONFIGS = new MqttConfigs("", "",
        //        "")

    }

    @Override
    public MqttConfigs findMqttConfigs() {
        log.info("getting mqtt configs from inmemorydb");
        return HARDCODED_CONFIGS;
    }
}
