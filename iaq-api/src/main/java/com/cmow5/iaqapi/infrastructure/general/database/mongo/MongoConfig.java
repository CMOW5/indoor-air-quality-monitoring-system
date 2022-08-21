package com.cmow5.iaqapi.infrastructure.general.database.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.MONGO_PROFILE;

/**
 * Configuration class to set the connection to mongo
 */
@Configuration
@Profile(MONGO_PROFILE)
public class MongoConfig {

    public static final String MONGO_PROFILE = "mongo";

    public final static String CONNECTION_STRING = "mongodb://localhost:27017";

    public final static String TEST_DB_NAME = "monitoring";

    public final static String TEST_DB_MONGODB_TEMPLATE_BEAN = "TEST_DB_MONGODB_TEMPLATE";

    @Bean
    @Profile(MONGO_PROFILE)
    public MongoClient mongoClient() {
        return MongoClients.create(CONNECTION_STRING);
    }

    @Bean
    @Profile(MONGO_PROFILE)
    @Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN)
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, TEST_DB_NAME);
    }
}
