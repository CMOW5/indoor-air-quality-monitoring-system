package com.cmow5.iaqapi.infrastructure.humidity.mongo.repository;

import com.cmow5.iaqapi.domain.humidity.HumidityDataPoint;
import com.cmow5.iaqapi.domain.humidity.repository.HumidityRepository;
import com.cmow5.iaqapi.infrastructure.humidity.mongo.entity.HumidityEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.MONGO_PROFILE;
import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.TEST_DB_MONGODB_TEMPLATE_BEAN;
import static com.cmow5.iaqapi.infrastructure.humidity.mongo.entity.HumidityEntity.STATION_ID_FIELD;
import static com.cmow5.iaqapi.infrastructure.humidity.mongo.entity.HumidityEntity.TIMESTAMP_FIELD;
import static com.cmow5.iaqapi.infrastructure.humidity.mongo.repository.HumidityMongoRepository.HUMIDITY_SENSOR_MONGO_REPOSITORY_BEAN;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository(HUMIDITY_SENSOR_MONGO_REPOSITORY_BEAN)
@Profile(MONGO_PROFILE)
public class HumidityMongoRepository implements HumidityRepository {
    public static final String HUMIDITY_SENSOR_MONGO_REPOSITORY_BEAN = "HumidityMongoRepository";

    private final MongoOperations mongodbTemplate;

    public HumidityMongoRepository(@Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN) MongoOperations mongodbTemplate) {
        this.mongodbTemplate = mongodbTemplate;
    }

    @Override
    public List<HumidityDataPoint> findBetweenDates(String stationId, Instant start, Instant end, String sort) {
        List<HumidityEntity> entities = mongodbTemplate.query(HumidityEntity.class)
                .matching(query(where(STATION_ID_FIELD)
                        .is(stationId)
                        .and(TIMESTAMP_FIELD).gte(start).lte(end)
                )).all();
        return HumidityEntity.toDomain(entities);
    }

    @Override
    public void save(HumidityDataPoint dataPoint) {
        HumidityEntity entity = HumidityEntity.fromDomain(dataPoint);
        this.mongodbTemplate.save(entity);
    }
}
