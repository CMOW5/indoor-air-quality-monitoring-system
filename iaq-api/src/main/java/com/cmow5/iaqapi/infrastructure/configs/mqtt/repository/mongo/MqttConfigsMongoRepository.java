package com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.mongo;

import com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigs;
import com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigsParent;
import com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.MqttConfigsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import static com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigsParent.MQTT_CONFIGS_ID;
import static com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.mongo.MqttConfigsMongoRepository.MQTT_CONFIGS_MONGO_REPOSITORY_BEAN;
import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.MONGO_PROFILE;
import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.TEST_DB_MONGODB_TEMPLATE_BEAN;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository(MQTT_CONFIGS_MONGO_REPOSITORY_BEAN)
@Profile(MONGO_PROFILE)
public class MqttConfigsMongoRepository implements MqttConfigsRepository {

    public static final String MQTT_CONFIGS_MONGO_REPOSITORY_BEAN = "mqttConfigsRepository";

    private static final Logger log = LogManager.getLogger(MqttConfigsMongoRepository.class);

    private final MongoOperations mongodbTemplate;

    @Autowired
    public MqttConfigsMongoRepository(@Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN) MongoOperations mongodbTemplate) {
        this.mongodbTemplate = mongodbTemplate;
    }

    @Override
    public MqttConfigs findMqttConfigs() {
        log.info("getting mqtt configs from mongo");
        return mongodbTemplate.query(MqttConfigsParent.class)
                .matching(query(where("_id")
                        .is(MQTT_CONFIGS_ID)
                )).firstValue().getMqttConfigs();
    }
}
