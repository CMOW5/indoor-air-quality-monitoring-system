package com.cmow5.iaqapi.infrastructure.humidity.awstimestream.repository;

import com.cmow5.iaqapi.domain.humidity.HumidityDataPoint;
import com.cmow5.iaqapi.domain.humidity.repository.HumidityRepository;
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
import static com.cmow5.iaqapi.infrastructure.humidity.awstimestream.repository.HumidityAwsTimestreamRepository.HUMIDITY_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN;

@Repository(HUMIDITY_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN)
@Profile(AWS_TIMESTREAM_PROFILE)
public class HumidityAwsTimestreamRepository implements HumidityRepository {

    private static final String AWS_TIMESTREAM_HUMIDITY_SENSOR_TABLE = "humidity";

    public static final String HUMIDITY_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN = "humidityAwsTimestreamRepository";

    private static final Logger log = LogManager.getLogger(HumidityAwsTimestreamRepository.class);

    private final TimestreamQueryClient timestreamQueryClient;

    @Autowired
    public HumidityAwsTimestreamRepository(@Qualifier(AWS_TIMESTREAM_QUERY_CLIENT_BEAN) TimestreamQueryClient timestreamQueryClient) {
        this.timestreamQueryClient = timestreamQueryClient;
    }

    @Override
    public List<HumidityDataPoint> findBetweenDates(String stationId, Instant start, Instant end, String sort) {
        String query = new AwsTimestreamQueryBuilder(DATABASE, AWS_TIMESTREAM_HUMIDITY_SENSOR_TABLE, stationId)
                .start(start)
                .end(end)
                .sort(sort)
                .bigIntMeasure()
                .build();

        log.info("created query = " + query);

        QueryRequest queryRequest = QueryRequest.builder().queryString(query).build();
        final QueryIterable queryResponseIterator = timestreamQueryClient.queryPaginator(queryRequest);

        List<HumidityDataPoint> dataPoints = new ArrayList<>();

        for(QueryResponse queryResponse : queryResponseIterator) {
            dataPoints.addAll(parseDatapointFromQueryResult(queryResponse));
        }

        return dataPoints;
    }

    private List<HumidityDataPoint> parseDatapointFromQueryResult(QueryResponse response) {
        List<ColumnInfo> columnInfo = response.columnInfo();
        List<Row> rows = response.rows();
        List<HumidityDataPoint> dataPoints = new ArrayList<>();

        for (Row row : rows) {
            dataPoints.add(parseHumidityDatapoint(columnInfo, row));
        }

        return dataPoints;
    }

    private HumidityDataPoint parseHumidityDatapoint(List<ColumnInfo> columnInfo, Row row) {
        AwsTimestreamSensorDatapoint datapoint = new AwsTimestreamSensorDatapoint(columnInfo, row);

        String stationId = datapoint.getStationId();
        String timestamp = datapoint.getTimestamp();
        int value = datapoint.getMeasureValue();

        return new HumidityDataPoint(stationId, Instant.ofEpochMilli(Long.parseLong(timestamp)), value);
    }

    @Override
    public void save(HumidityDataPoint dataPoint) {

    }
}

