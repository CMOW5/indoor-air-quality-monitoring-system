package com.cmow5.iaqapi.infrastructure.temperature.repository.awstimestream;

import com.cmow5.iaqapi.domain.temperature.TempSensorDataPoint;
import com.cmow5.iaqapi.domain.temperature.repository.TempSensorRepository;
import com.cmow5.iaqapi.infrastructure.general.database.awstimestream.utils.AwsTimestreamDatapointParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.timestreamquery.TimestreamQueryClient;
import software.amazon.awssdk.services.timestreamquery.model.*;
import software.amazon.awssdk.services.timestreamquery.paginators.QueryIterable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.AWS_TIMESTREAM_PROFILE;
import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.AWS_TIMESTREAM_QUERY_CLIENT_BEAN;
import static com.cmow5.iaqapi.infrastructure.temperature.repository.awstimestream.TempSensorAwsTimestreamRepository.TEMP_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN;

@Repository(TEMP_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN)
@Profile(AWS_TIMESTREAM_PROFILE)
public class TempSensorAwsTimestreamRepository implements TempSensorRepository {

    public static final String TEMP_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN = "tempSensorAwsTimestreamRepository";

    private static final Logger log = LogManager.getLogger(TempSensorAwsTimestreamRepository.class);

    private final TimestreamQueryClient timestreamQueryClient;

    private final AwsTimestreamDatapointParser datapointParser;

    @Autowired
    public TempSensorAwsTimestreamRepository(@Qualifier(AWS_TIMESTREAM_QUERY_CLIENT_BEAN) TimestreamQueryClient timestreamQueryClient, AwsTimestreamDatapointParser datapointParser) {
        this.timestreamQueryClient = timestreamQueryClient;
        this.datapointParser = datapointParser;
    }

    @Override
    // db.temperature_seconds.find({"metadata.sensorId": 5578, timestamp:{ $gte: ISODate("2021-05-18T00:00:00Z"), $lte: ISODate("2021-05-19T20:00:00.000+00:00") }})
    public List<TempSensorDataPoint> findBetweenDates(String stationId, Instant start, Instant end) {
        /*
        List<TempSensorEntity> entities = mongodbTemplate.query(TempSensorEntity.class)
                .matching(query(where(STATION_ID_FIELD)
                        .is(stationId)
                        .and(TIMESTAMP_FIELD).gte(start).lte(end)
                )).all();
        return TempSensorEntity.toDomain(entities);
         */
        //log.info("getting temperature datapoints from timestream");
        //return new ArrayList<>();

        //DateTimeFormatter.ISO_INSTANT.format(timestamp);

        //String query = "SELECT * FROM \"monitoring\".\"temperature\" LIMIT 2";

        /*
        SELECT stationId AS station_id, value, timestamp FROM 'sensor/temperature'

         */

        String query = "SELECT station_id, measure_value::double, to_milliseconds(time) AS timestamp FROM \"monitoring\".\"temperature\" LIMIT 10";

        QueryRequest queryRequest = QueryRequest.builder().queryString(query).build();
        final QueryIterable queryResponseIterator = timestreamQueryClient.queryPaginator(queryRequest);

        List<TempSensorDataPoint> dataPoints = new ArrayList<>();

        for(QueryResponse queryResponse : queryResponseIterator) {
            dataPoints.addAll(parseDatapointFromQueryResult(queryResponse));
        }

        return dataPoints;
    }

    private List<TempSensorDataPoint> parseDatapointFromQueryResult(QueryResponse response) {
        List<ColumnInfo> columnInfo = response.columnInfo();
        List<Row> rows = response.rows();
        List<TempSensorDataPoint> dataPoints = new ArrayList<>();

        for (Row row : rows) {
            dataPoints.add(parseTemperatureDatapoint(columnInfo, row));
        }

        return dataPoints;
    }

    private TempSensorDataPoint parseTemperatureDatapoint(List<ColumnInfo> columnInfo, Row row) {
        String stationId = datapointParser.parseField("station_id", columnInfo, row);
        String timestamp = datapointParser.parseField("timestamp", columnInfo, row);
        String value = datapointParser.parseField("measure_value::double", columnInfo, row);
        return new TempSensorDataPoint(stationId, Instant.ofEpochMilli(Long.parseLong(timestamp)), (int)Double.parseDouble(value));
    }

    @Override
    public TempSensorDataPoint save(TempSensorDataPoint dataPoint) {
        throw new UnsupportedOperationException("unsupported save in timestream from backend");
    }


}

