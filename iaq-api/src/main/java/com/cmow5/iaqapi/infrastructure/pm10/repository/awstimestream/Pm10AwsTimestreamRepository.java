package com.cmow5.iaqapi.infrastructure.pm10.repository.awstimestream;

import com.cmow5.iaqapi.domain.pm10.Pm10DataPoint;
import com.cmow5.iaqapi.domain.pm10.repository.Pm10Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.AWS_TIMESTREAM_PROFILE;
import static com.cmow5.iaqapi.infrastructure.pm10.repository.awstimestream.Pm10AwsTimestreamRepository.PM10_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN;

@Repository(PM10_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN)
@Profile(AWS_TIMESTREAM_PROFILE)
public class Pm10AwsTimestreamRepository implements Pm10Repository {

    public static final String PM10_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN = "Pm10AwsTimestreamRepository";

    private static final Logger log = LogManager.getLogger(Pm10AwsTimestreamRepository.class);

    public Pm10AwsTimestreamRepository() {

    }

    @Override
    public List<Pm10DataPoint> findBetweenDates(String stationId, Instant start, Instant end) {
        /*
        List<Pm10Entity> entities = mongodbTemplate.query(Pm10Entity.class)
                .matching(query(where(STATION_ID_FIELD)
                        .is(stationId)
                        .and(TIMESTAMP_FIELD).gte(start).lte(end)
                )).all();
        return Pm10Entity.toDomain(entities);
         */
        log.info("getting PM10 datapoints from timestream");
        return new ArrayList<>();
    }

    @Override
    public void save(Pm10DataPoint dataPoint) {

    }
}