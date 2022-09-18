package com.cmow5.iaqapi.infrastructure.pm10.awstimestream.repository;

import com.cmow5.iaqapi.domain.pm10.Pm10DataPoint;
import com.cmow5.iaqapi.domain.pm10.repository.Pm10Repository;
import com.cmow5.iaqapi.infrastructure.general.database.awstimestream.utils.AwsTimestreamQueryBuilder;
import com.cmow5.iaqapi.infrastructure.general.database.awstimestream.utils.AwsTimestreamSensorDatapoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.timestreamquery.TimestreamQueryClient;
import software.amazon.awssdk.services.timestreamquery.model.ColumnInfo;
import software.amazon.awssdk.services.timestreamquery.model.QueryRequest;
import software.amazon.awssdk.services.timestreamquery.model.QueryResponse;
import software.amazon.awssdk.services.timestreamquery.model.Row;
import software.amazon.awssdk.services.timestreamquery.paginators.QueryIterable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.*;
import static com.cmow5.iaqapi.infrastructure.pm10.awstimestream.repository.Pm10AwsTimestreamRepository.PM10_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN;

@Repository(PM10_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN)
@Profile(AWS_TIMESTREAM_PROFILE)
public class Pm10AwsTimestreamRepository implements Pm10Repository {

    public static final String PM10_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN = "Pm10AwsTimestreamRepository";

    private static final Logger log = LogManager.getLogger(Pm10AwsTimestreamRepository.class);

    private static final String AWS_TIMESTREAM_PM10_SENSOR_TABLE = "pm10";

    private final TimestreamQueryClient timestreamQueryClient;

    @Autowired
    public Pm10AwsTimestreamRepository(@Qualifier(AWS_TIMESTREAM_QUERY_CLIENT_BEAN) TimestreamQueryClient timestreamQueryClient) {
        this.timestreamQueryClient = timestreamQueryClient;
    }

    @Override
    public List<Pm10DataPoint> findBetweenDates(String stationId, Instant start, Instant end, String sort) {
        String query = new AwsTimestreamQueryBuilder(DATABASE, AWS_TIMESTREAM_PM10_SENSOR_TABLE, stationId)
                .start(start)
                .end(end)
                .sort(sort)
                .bigIntMeasure()
                .build();

        log.info("created query = " + query);

        QueryRequest queryRequest = QueryRequest.builder().queryString(query).build();
        final QueryIterable queryResponseIterator = timestreamQueryClient.queryPaginator(queryRequest);

        List<Pm10DataPoint> dataPoints = new ArrayList<>();

        for(QueryResponse queryResponse : queryResponseIterator) {
            dataPoints.addAll(parseDatapointFromQueryResult(queryResponse));
        }

        return dataPoints;
    }

    private List<Pm10DataPoint> parseDatapointFromQueryResult(QueryResponse response) {
        List<ColumnInfo> columnInfo = response.columnInfo();
        List<Row> rows = response.rows();
        List<Pm10DataPoint> dataPoints = new ArrayList<>();

        for (Row row : rows) {
            dataPoints.add(parsePm10Datapoint(columnInfo, row));
        }

        return dataPoints;
    }

    private Pm10DataPoint parsePm10Datapoint(List<ColumnInfo> columnInfo, Row row) {
        AwsTimestreamSensorDatapoint datapoint = new AwsTimestreamSensorDatapoint(columnInfo, row);

        String stationId = datapoint.getStationId();
        String timestamp = datapoint.getTimestamp();
        int value = datapoint.getMeasureValue();

        return new Pm10DataPoint(stationId, Instant.ofEpochMilli(Long.parseLong(timestamp)), value);
    }

    @Override
    public void save(Pm10DataPoint dataPoint) {

    }
}