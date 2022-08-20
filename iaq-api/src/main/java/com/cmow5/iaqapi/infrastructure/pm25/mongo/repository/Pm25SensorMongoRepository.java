package com.cmow5.iaqapi.infrastructure.pm25.mongo.repository;

import com.cmow5.iaqapi.domain.pm25.repository.Pm25SensorRepository;
import com.cmow5.iaqapi.infrastructure.pm25.mongo.entity.Pm25SensorEntity;
import com.cmow5.iaqapi.domain.pm25.Pm25SensorDataPoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.MONGO_PROFILE;
import static com.cmow5.iaqapi.infrastructure.pm25.mongo.entity.Pm25SensorEntity.STATION_ID_FIELD;
import static com.cmow5.iaqapi.infrastructure.pm25.mongo.entity.Pm25SensorEntity.TIMESTAMP_FIELD;
import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.TEST_DB_MONGODB_TEMPLATE_BEAN;
import static com.cmow5.iaqapi.infrastructure.pm25.mongo.repository.Pm25SensorMongoRepository.PM25_SENSOR_MONGO_REPOSITORY_BEAN;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * The mongo repository to store the temperature sensor data into mongo db
 */
@Repository(PM25_SENSOR_MONGO_REPOSITORY_BEAN)
@Profile(MONGO_PROFILE)
public class Pm25SensorMongoRepository implements Pm25SensorRepository {

    public static final String PM25_SENSOR_MONGO_REPOSITORY_BEAN = "pm25SensorMongoRepository";

    private static final Logger log = LogManager.getLogger(Pm25SensorMongoRepository.class);

    private final MongoOperations mongodbTemplate;

    @Autowired
    public Pm25SensorMongoRepository(@Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN) MongoOperations mongodbTemplate) {
        this.mongodbTemplate = mongodbTemplate;
    }

    // db.temperature_seconds.find({"metadata.sensorId": 5578, timestamp:{ $gte: ISODate("2021-05-18T00:00:00Z"), $lte: ISODate("2021-05-19T20:00:00.000+00:00") }})
    public List<Pm25SensorDataPoint> findBetweenDates(String stationId, Instant start, Instant end, String sort) {
        List<Pm25SensorEntity> entities = mongodbTemplate.query(Pm25SensorEntity.class)
                .matching(query(where(STATION_ID_FIELD)
                        .is(stationId)
                        .and(TIMESTAMP_FIELD).gte(start).lte(end)
                )).all();
        return Pm25SensorEntity.toDomain(entities);
    }

    @Override
    public void save(Pm25SensorDataPoint dataPoint) {
        Pm25SensorEntity entity = Pm25SensorEntity.fromDomain(dataPoint);
        this.mongodbTemplate.save(entity);
    }


}
