package com.cmow5.iaqapi.infrastructure.co2.repository;

import com.cmow5.iaqapi.domain.co2.Co2DataPoint;
import com.cmow5.iaqapi.domain.co2.repository.Co2Repository;
import com.cmow5.iaqapi.infrastructure.co2.entity.Co2Entity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.co2.entity.Co2Entity.STATION_ID_FIELD;
import static com.cmow5.iaqapi.infrastructure.co2.entity.Co2Entity.TIMESTAMP_FIELD;
import static com.cmow5.iaqapi.infrastructure.co2.repository.Co2MongoRepository.CO2_SENSOR_MONGO_REPOSITORY_BEAN;
import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.MONGO_PROFILE;
import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.TEST_DB_MONGODB_TEMPLATE_BEAN;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository(CO2_SENSOR_MONGO_REPOSITORY_BEAN)
@Profile(MONGO_PROFILE)
public class Co2MongoRepository implements Co2Repository {

    public static final String CO2_SENSOR_MONGO_REPOSITORY_BEAN = "Co2MongoRepository";

    private final MongoOperations mongodbTemplate;

    public Co2MongoRepository(@Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN) MongoOperations mongodbTemplate) {
        this.mongodbTemplate = mongodbTemplate;
    }

    @Override
    public List<Co2DataPoint> findBetweenDates(String stationId, Instant start, Instant end) {
        List<Co2Entity> entities = mongodbTemplate.query(Co2Entity.class)
                .matching(query(where(STATION_ID_FIELD)
                        .is(stationId)
                        .and(TIMESTAMP_FIELD).gte(start).lte(end)
                )).all();
        return Co2Entity.toDomain(entities);
    }

    @Override
    public void save(Co2DataPoint dataPoint) {
        Co2Entity entity = Co2Entity.fromDomain(dataPoint);
        this.mongodbTemplate.save(entity);
    }
}
