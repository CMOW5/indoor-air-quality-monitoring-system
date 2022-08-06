package com.cmow5.iaqapi.infrastructure.general.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Configuration class to set the connection to mongo
 */
@Configuration
public class MongoConfig {

    public final static String CONNECTION_STRING = "mongodb://localhost:27017";

    public final static String TEST_DB_NAME = "monitoring";

    public final static String TEST_DB_MONGODB_TEMPLATE_BEAN = "TEST_DB_MONGODB_TEMPLATE";

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(CONNECTION_STRING);
    }

    @Bean
    @Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN)
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, TEST_DB_NAME);
    }
}
