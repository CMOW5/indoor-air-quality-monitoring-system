package com.cmow5.iaqapi.infrastructure.pm25.repository.timestream;

import com.cmow5.iaqapi.domain.pm25.Pm25SensorDataPoint;
import com.cmow5.iaqapi.domain.pm25.repository.Pm25SensorRepository;
import com.cmow5.iaqapi.infrastructure.general.database.awstimestream.utils.AwsTimestreamDatapointParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.timestreamquery.TimestreamQueryClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.AWS_TIMESTREAM_PROFILE;
import static com.cmow5.iaqapi.infrastructure.pm25.repository.timestream.Pm25SensorAwsTimestreamRepository.PM25_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN;

@Repository(PM25_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN)
@Profile(AWS_TIMESTREAM_PROFILE)
public class Pm25SensorAwsTimestreamRepository implements Pm25SensorRepository {

    public static final String PM25_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN = "pm25SensorAwsTimestreamRepository";

    private static final Logger log = LogManager.getLogger(Pm25SensorAwsTimestreamRepository.class);

    private final TimestreamQueryClient timestreamQueryClient;

    private final AwsTimestreamDatapointParser datapointParser;

    @Autowired
    public Pm25SensorAwsTimestreamRepository(TimestreamQueryClient timestreamQueryClient, AwsTimestreamDatapointParser datapointParser) {
        this.timestreamQueryClient = timestreamQueryClient;
        this.datapointParser = datapointParser;
    }

   public List<Pm25SensorDataPoint> findBetweenDates(String stationId, Instant start, Instant end) {
        /*
        List<Pm25SensorEntity> entities = mongodbTemplate.query(Pm25SensorEntity.class)
                .matching(query(where(STATION_ID_FIELD)
                        .is(stationId)
                        .and(TIMESTAMP_FIELD).gte(start).lte(end)
                )).all();
        return Pm25SensorEntity.toDomain(entities);
         */
       log.info("getting PM25 datapoints from timestream");
       return new ArrayList<>();
    }

    @Override
    public void save(Pm25SensorDataPoint dataPoint) {
        throw new UnsupportedOperationException("unsupported save in timestream from backend");
    }


}
