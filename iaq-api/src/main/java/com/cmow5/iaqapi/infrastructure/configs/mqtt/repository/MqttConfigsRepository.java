package com.cmow5.iaqapi.infrastructure.configs.mqtt.repository;

import com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigs;
import com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigsParent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import static com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigsParent.MQTT_CONFIGS_ID;
import static com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.MqttConfigsRepository.MQTT_CONFIGS_MONGO_REPOSITORY_BEAN;
import static com.cmow5.iaqapi.infrastructure.general.mongo.MongoConfig.TEST_DB_MONGODB_TEMPLATE_BEAN;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository(MQTT_CONFIGS_MONGO_REPOSITORY_BEAN)
public class MqttConfigsRepository {

    public static final String MQTT_CONFIGS_MONGO_REPOSITORY_BEAN = "mqttConfigsRepository";

    private static final Logger log = LogManager.getLogger(MqttConfigsRepository.class);

    private final MongoOperations mongodbTemplate;

    @Autowired
    public MqttConfigsRepository(@Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN) MongoOperations mongodbTemplate) {
        this.mongodbTemplate = mongodbTemplate;
    }

    public MqttConfigs findMqttConfigs() {
        return mongodbTemplate.query(MqttConfigsParent.class)
                .matching(query(where("_id")
                        .is(MQTT_CONFIGS_ID)
                )).firstValue().getMqttConfigs();
    }
}
