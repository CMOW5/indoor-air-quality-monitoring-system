package com.cmow5.iaqapi.infrastructure.humidity.repository.awstimestream;

import com.cmow5.iaqapi.domain.humidity.HumidityDataPoint;
import com.cmow5.iaqapi.domain.humidity.repository.HumidityRepository;
import com.cmow5.iaqapi.infrastructure.humidity.entity.HumidityEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.AWS_TIMESTREAM_PROFILE;
import static com.cmow5.iaqapi.infrastructure.general.database.mongo.MongoConfig.TEST_DB_MONGODB_TEMPLATE_BEAN;
import static com.cmow5.iaqapi.infrastructure.humidity.entity.HumidityEntity.STATION_ID_FIELD;
import static com.cmow5.iaqapi.infrastructure.humidity.entity.HumidityEntity.TIMESTAMP_FIELD;
import static com.cmow5.iaqapi.infrastructure.humidity.repository.awstimestream.HumidityAwsTimestreamRepository.HUMIDITY_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository(HUMIDITY_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN)
@Profile(AWS_TIMESTREAM_PROFILE)
public class HumidityAwsTimestreamRepository implements HumidityRepository {

    public static final String HUMIDITY_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN = "humidityAwsTimestreamRepository";

    private static final Logger log = LogManager.getLogger(HumidityAwsTimestreamRepository.class);

    public HumidityAwsTimestreamRepository() {
    }

    @Override
    public List<HumidityDataPoint> findBetweenDates(String stationId, Instant start, Instant end) {
        /*
        List<HumidityEntity> entities = mongodbTemplate.query(HumidityEntity.class)
                .matching(query(where(STATION_ID_FIELD)
                        .is(stationId)
                        .and(TIMESTAMP_FIELD).gte(start).lte(end)
                )).all();
        return HumidityEntity.toDomain(entities);
         */
        log.info("getting Humidity datapoints from timestream");
        return new ArrayList<>();
    }

    @Override
    public void save(HumidityDataPoint dataPoint) {

    }
}

