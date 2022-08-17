package com.cmow5.iaqapi.infrastructure.pm10.repository;

import com.cmow5.iaqapi.domain.pm10.Pm10DataPoint;
import com.cmow5.iaqapi.domain.pm10.repository.Pm10Repository;
import com.cmow5.iaqapi.infrastructure.pm10.entity.Pm10Entity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.MONGO_PROFILE;
import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.TEST_DB_MONGODB_TEMPLATE_BEAN;
import static com.cmow5.iaqapi.infrastructure.pm10.entity.Pm10Entity.STATION_ID_FIELD;
import static com.cmow5.iaqapi.infrastructure.pm10.entity.Pm10Entity.TIMESTAMP_FIELD;
import static com.cmow5.iaqapi.infrastructure.pm10.repository.Pm10MongoRepository.PM10_SENSOR_MONGO_REPOSITORY_BEAN;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository(PM10_SENSOR_MONGO_REPOSITORY_BEAN)
@Profile(MONGO_PROFILE)
public class Pm10MongoRepository implements Pm10Repository {

    public static final String PM10_SENSOR_MONGO_REPOSITORY_BEAN = "pm10MongoRepository";

    private final MongoOperations mongodbTemplate;

    public Pm10MongoRepository(@Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN) MongoOperations mongodbTemplate) {
        this.mongodbTemplate = mongodbTemplate;
    }

    @Override
    public List<Pm10DataPoint> findBetweenDates(String stationId, Instant start, Instant end) {
         List<Pm10Entity> entities = mongodbTemplate.query(Pm10Entity.class)
                .matching(query(where(STATION_ID_FIELD)
                        .is(stationId)
                        .and(TIMESTAMP_FIELD).gte(start).lte(end)
                )).all();
        return Pm10Entity.toDomain(entities);
    }

    @Override
    public void save(Pm10DataPoint dataPoint) {
        Pm10Entity entity = Pm10Entity.fromDomain(dataPoint);
        this.mongodbTemplate.save(entity);
    }
}
