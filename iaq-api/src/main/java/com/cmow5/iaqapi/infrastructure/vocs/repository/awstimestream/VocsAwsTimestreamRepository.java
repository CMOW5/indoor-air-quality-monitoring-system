package com.cmow5.iaqapi.infrastructure.vocs.repository.awstimestream;

import com.cmow5.iaqapi.domain.vocs.VocsDataPoint;
import com.cmow5.iaqapi.domain.vocs.repository.VocsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.AWS_TIMESTREAM_PROFILE;
import static com.cmow5.iaqapi.infrastructure.vocs.repository.awstimestream.VocsAwsTimestreamRepository.VOCS_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN;

@Repository(VOCS_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN)
@Profile(AWS_TIMESTREAM_PROFILE)
public class VocsAwsTimestreamRepository implements VocsRepository {

    public static final String VOCS_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN = "vocsAwsTimestreamRepository";

    private static final Logger log = LogManager.getLogger(VocsAwsTimestreamRepository.class);

    @Autowired
    public VocsAwsTimestreamRepository() {

    }

    // db.temperature_seconds.find({"metadata.sensorId": 5578, timestamp:{ $gte: ISODate("2021-05-18T00:00:00Z"), $lte: ISODate("2021-05-19T20:00:00.000+00:00") }})
    public List<VocsDataPoint> findBetweenDates(String stationId, Instant start, Instant end) {
        /*
        List<VocsEntity> entities = mongodbTemplate.query(VocsEntity.class)
                .matching(query(where(STATION_ID_FIELD)
                        .is(stationId)
                        .and(TIMESTAMP_FIELD).gte(start).lte(end)
                )).all();
        return VocsEntity.toDomain(entities);
         */
        log.info("getting VOCS datapoints from timestream");
        return new ArrayList<>();
    }

    @Override
    public void save(VocsDataPoint dataPoint) {

    }
}
