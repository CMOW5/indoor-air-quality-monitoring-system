package com.cmow5.iaqapi.infrastructure.station.mongo.repository;

import com.cmow5.iaqapi.domain.station.Station;
import com.cmow5.iaqapi.domain.station.repository.StationRepository;
import com.cmow5.iaqapi.infrastructure.station.mongo.entity.StationEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.MONGO_PROFILE;
import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.TEST_DB_MONGODB_TEMPLATE_BEAN;
import static com.cmow5.iaqapi.infrastructure.station.mongo.repository.StationMongoRepository.STATION_MONGO_REPOSITORY_BEAN;

@Repository(STATION_MONGO_REPOSITORY_BEAN)
@Profile(MONGO_PROFILE)
public class StationMongoRepository implements StationRepository {

    public static final String STATION_MONGO_REPOSITORY_BEAN = "stationMongoRepository";

    private static final Logger log = LogManager.getLogger(StationMongoRepository.class);

    private final MongoOperations mongodbTemplate;

    public StationMongoRepository(@Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN) MongoOperations mongodbTemplate) {
        this.mongodbTemplate = mongodbTemplate;
    }

    @Override
    public List<Station> findAll() {
        log.info("getting this from mongo :)");
        List<StationEntity> entities = this.mongodbTemplate.findAll(StationEntity.class);
        return StationEntity.toDomain(entities);
    }
}
