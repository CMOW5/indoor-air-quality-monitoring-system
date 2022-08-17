package com.cmow5.iaqapi.infrastructure.co2.repository.awstimestream;

import com.cmow5.iaqapi.domain.co2.Co2DataPoint;
import com.cmow5.iaqapi.domain.co2.repository.Co2Repository;
import com.cmow5.iaqapi.infrastructure.co2.entity.Co2Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.co2.repository.awstimestream.Co2AwsTimestreamRepository.CO2_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN;
import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.AWS_TIMESTREAM_PROFILE;

@Repository(CO2_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN)
@Profile(AWS_TIMESTREAM_PROFILE)
public class Co2AwsTimestreamRepository implements Co2Repository {

    public static final String CO2_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN = "co2AwsTimestreamRepository";

    private static final Logger log = LogManager.getLogger(Co2AwsTimestreamRepository.class);

    public Co2AwsTimestreamRepository() {

    }

    @Override
    public List<Co2DataPoint> findBetweenDates(String stationId, Instant start, Instant end) {
        /*
        List<Co2Entity> entities = mongodbTemplate.query(Co2Entity.class)
                .matching(query(where(STATION_ID_FIELD)
                        .is(stationId)
                        .and(TIMESTAMP_FIELD).gte(start).lte(end)
                )).all();
        return Co2Entity.toDomain(entities);
         */
        log.info("getting co2 datapoints from timestream");
        return new ArrayList<>();
    }

    @Override
    public void save(Co2DataPoint dataPoint) {

    }
}
