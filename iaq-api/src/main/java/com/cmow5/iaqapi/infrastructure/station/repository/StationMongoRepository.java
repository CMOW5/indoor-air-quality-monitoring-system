package com.cmow5.iaqapi.infrastructure.station.repository;

import com.cmow5.iaqapi.domain.station.Station;
import com.cmow5.iaqapi.domain.station.repository.StationRepository;
import com.cmow5.iaqapi.infrastructure.station.entity.StationEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.mongo.MongoConfig.TEST_DB_MONGODB_TEMPLATE_BEAN;
import static com.cmow5.iaqapi.infrastructure.station.repository.StationMongoRepository.STATION_MONGO_REPOSITORY_BEAN;

@Repository(STATION_MONGO_REPOSITORY_BEAN)
public class StationMongoRepository implements StationRepository {

    public static final String STATION_MONGO_REPOSITORY_BEAN = "stationMongoRepository";

    private final MongoOperations mongodbTemplate;

    public StationMongoRepository(@Qualifier(TEST_DB_MONGODB_TEMPLATE_BEAN) MongoOperations mongodbTemplate) {
        this.mongodbTemplate = mongodbTemplate;
    }

    @Override
    public List<Station> findAll() {
        List<StationEntity> entities = this.mongodbTemplate.findAll(StationEntity.class);
        return StationEntity.toDomain(entities);
    }
}
