package com.cmow5.iaqapi.infrastructure.temperature.repository;

import com.cmow5.iaqapi.domain.temperature.TempSensorDataPoint;
import com.cmow5.iaqapi.domain.temperature.repository.TempSensorRepository;
import com.cmow5.iaqapi.infrastructure.temperature.entity.TempSensorEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.temperature.repository.TempSensorMongoRepository.TEMP_SENSOR_MONGO_REPOSITORY_BEAN;
import static com.cmow5.iaqapi.infrastructure.general.mongo.MongoConfig.TEST_DB_MONGODB_TEMPLATE_BEAN;
import static com.cmow5.iaqapi.infrastructure.temperature.entity.TempSensorEntity.STATION_ID_FIELD;
import static com.cmow5.iaqapi.infrastructure.temperature.entity.TempSensorEntity.TIMESTAMP_FIELD;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * The mongo repository to store the temperature sensor data into mongo db
 */
@Repository(TEMP_SENSOR_MONGO_REPOSITORY_BEAN)
public class TempSensorMongoRepository implements TempSensorRepository {

    public static final String TEMP_SENSOR_MONGO_REPOSITORY_BEAN = "tempSensorMongoRepository";

    private static final Logger log = LogManager.getLogger(TempSensorMongoRepository.class);

    private final MongoOperations mongodbTemplate;

    @Autowired
    public TempSensorMongoRepository(@Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN) MongoOperations mongodbTemplate) {
        this.mongodbTemplate = mongodbTemplate;
    }

    // db.temperature_seconds.find({"metadata.sensorId": 5578, timestamp:{ $gte: ISODate("2021-05-18T00:00:00Z"), $lte: ISODate("2021-05-19T20:00:00.000+00:00") }})
    public List<TempSensorDataPoint> findBetweenDates(String stationId, Instant start, Instant end) {
        List<TempSensorEntity> entities = mongodbTemplate.query(TempSensorEntity.class)
                       .matching(query(where(STATION_ID_FIELD)
                                                   .is(stationId)
                                                   .and(TIMESTAMP_FIELD).gte(start).lte(end)
                                       )).all();
        return TempSensorEntity.toDomain(entities);
    }

    @Override
    public TempSensorDataPoint save(TempSensorDataPoint dataPoint) {
        TempSensorEntity entity = TempSensorEntity.fromDomain(dataPoint);
        TempSensorEntity savedEntity = mongodbTemplate.save(entity);
        return TempSensorEntity.toDomain(savedEntity);
    }


}
