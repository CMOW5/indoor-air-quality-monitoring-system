package com.cmow5.iaqapi.infrastructure.vocs.repository;

import com.cmow5.iaqapi.domain.vocs.VocsDataPoint;
import com.cmow5.iaqapi.domain.vocs.repository.VocsRepository;
import com.cmow5.iaqapi.infrastructure.vocs.entity.VocsEntity;
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
import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.TEST_DB_MONGODB_TEMPLATE_BEAN;
import static com.cmow5.iaqapi.infrastructure.pm10.entity.Pm10Entity.STATION_ID_FIELD;
import static com.cmow5.iaqapi.infrastructure.vocs.entity.VocsEntity.TIMESTAMP_FIELD;
import static com.cmow5.iaqapi.infrastructure.vocs.repository.VocsMongoRepository.VOCS_SENSOR_MONGO_REPOSITORY_BEAN;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository(VOCS_SENSOR_MONGO_REPOSITORY_BEAN)
@Profile(MONGO_PROFILE)
public class VocsMongoRepository implements VocsRepository {

    public static final String VOCS_SENSOR_MONGO_REPOSITORY_BEAN = "vocsSensorMongoRepository";

    private static final Logger log = LogManager.getLogger(VocsMongoRepository.class);

    private final MongoOperations mongodbTemplate;

    @Autowired
    public VocsMongoRepository(@Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN) MongoOperations mongodbTemplate) {
        this.mongodbTemplate = mongodbTemplate;
    }

    // db.temperature_seconds.find({"metadata.sensorId": 5578, timestamp:{ $gte: ISODate("2021-05-18T00:00:00Z"), $lte: ISODate("2021-05-19T20:00:00.000+00:00") }})
    public List<VocsDataPoint> findBetweenDates(String stationId, Instant start, Instant end) {
        log.info("getting VOCS datapoints from mongo");
        List<VocsEntity> entities = mongodbTemplate.query(VocsEntity.class)
                .matching(query(where(STATION_ID_FIELD)
                        .is(stationId)
                        .and(TIMESTAMP_FIELD).gte(start).lte(end)
                )).all();
        return VocsEntity.toDomain(entities);
    }

    @Override
    public void save(VocsDataPoint dataPoint) {
        VocsEntity entity = VocsEntity.fromDomain(dataPoint);
        this.mongodbTemplate.save(entity);
    }
}
