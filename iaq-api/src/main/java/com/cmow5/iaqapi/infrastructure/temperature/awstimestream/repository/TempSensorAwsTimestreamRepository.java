package com.cmow5.iaqapi.infrastructure.temperature.awstimestream.repository;

import com.cmow5.iaqapi.domain.temperature.TempSensorDataPoint;
import com.cmow5.iaqapi.domain.temperature.repository.TempSensorRepository;
import com.cmow5.iaqapi.infrastructure.general.database.awstimestream.utils.AwsTimestreamQueryBuilder;
import com.cmow5.iaqapi.infrastructure.general.database.awstimestream.utils.AwsTimestreamSensorDatapoint;
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

import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.*;
import static com.cmow5.iaqapi.infrastructure.temperature.awstimestream.repository.TempSensorAwsTimestreamRepository.TEMP_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN;

@Repository(TEMP_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN)
@Profile(AWS_TIMESTREAM_PROFILE)
public class TempSensorAwsTimestreamRepository implements TempSensorRepository {

    public static final String TEMP_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN = "tempSensorAwsTimestreamRepository";

    private static final String AWS_TIMESTREAM_TEMP_SENSOR_TABLE = "temperature";

    private static final Logger log = LogManager.getLogger(TempSensorAwsTimestreamRepository.class);

    private final TimestreamQueryClient timestreamQueryClient;

    @Autowired
    public TempSensorAwsTimestreamRepository(@Qualifier(AWS_TIMESTREAM_QUERY_CLIENT_BEAN) TimestreamQueryClient timestreamQueryClient) {
        this.timestreamQueryClient = timestreamQueryClient;
    }

    @Override
    // db.temperature_seconds.find({"metadata.sensorId": 5578, timestamp:{ $gte: ISODate("2021-05-18T00:00:00Z"), $lte: ISODate("2021-05-19T20:00:00.000+00:00") }})
    public List<TempSensorDataPoint> findBetweenDates(String stationId, Instant start, Instant end, String sort) {
        /*
        String query = String.format(
                       "SELECT station_id, measure_value::bigint, to_milliseconds(time) AS timestamp " +
                       "FROM monitoring.temperature " +
                       "WHERE station_id = '%s' AND (time between from_milliseconds(%s) and from_milliseconds(%s)) ORDER BY time %s",
                       stationId, start.toEpochMilli(), end.toEpochMilli(), AwsTimestreamQueryBuilder.TimestreamSort.valueOf(sort));
        */

        String query = new AwsTimestreamQueryBuilder(DATABASE, AWS_TIMESTREAM_TEMP_SENSOR_TABLE, stationId)
                .start(start)
                .end(end)
                .sort(sort)
                .bigIntMeasure()
                .build();

        log.info("created query = " + query);

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
        AwsTimestreamSensorDatapoint datapoint = new AwsTimestreamSensorDatapoint(columnInfo, row);

        String stationId = datapoint.getStationId();
        String timestamp = datapoint.getTimestamp();
        int value = datapoint.getMeasureValue();

        return new TempSensorDataPoint(stationId, Instant.ofEpochMilli(Long.parseLong(timestamp)), value);
    }

    @Override
    public TempSensorDataPoint save(TempSensorDataPoint dataPoint) {
        throw new UnsupportedOperationException("unsupported save in timestream from backend");
    }


}

