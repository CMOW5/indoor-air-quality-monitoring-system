package com.cmow5.iaqapi.infrastructure.co2.awstimestream.repository;

import com.cmow5.iaqapi.domain.co2.Co2DataPoint;
import com.cmow5.iaqapi.domain.co2.repository.Co2Repository;
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

import static com.cmow5.iaqapi.infrastructure.co2.awstimestream.repository.Co2AwsTimestreamRepository.CO2_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN;
import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.*;

@Repository(CO2_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN)
@Profile(AWS_TIMESTREAM_PROFILE)
public class Co2AwsTimestreamRepository implements Co2Repository {

    public static final String CO2_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN = "co2AwsTimestreamRepository";

    private static final Logger log = LogManager.getLogger(Co2AwsTimestreamRepository.class);

    private static final String AWS_TIMESTREAM_CO2_SENSOR_TABLE = "co2";

    private final TimestreamQueryClient timestreamQueryClient;

    @Autowired
    public Co2AwsTimestreamRepository(@Qualifier(AWS_TIMESTREAM_QUERY_CLIENT_BEAN) TimestreamQueryClient timestreamQueryClient) {
        this.timestreamQueryClient = timestreamQueryClient;
    }

    @Override
    public List<Co2DataPoint> findBetweenDates(String stationId, Instant start, Instant end, String sort) {
        String query = new AwsTimestreamQueryBuilder(DATABASE, AWS_TIMESTREAM_CO2_SENSOR_TABLE, stationId)
                .start(start)
                .end(end)
                .sort(sort)
                .bigIntMeasure()
                .build();

        log.info("created query = " + query);

        QueryRequest queryRequest = QueryRequest.builder().queryString(query).build();
        final QueryIterable queryResponseIterator = timestreamQueryClient.queryPaginator(queryRequest);

        List<Co2DataPoint> dataPoints = new ArrayList<>();

        for(QueryResponse queryResponse : queryResponseIterator) {
            dataPoints.addAll(parseDatapointFromQueryResult(queryResponse));
        }

        return dataPoints;
    }

    private List<Co2DataPoint> parseDatapointFromQueryResult(QueryResponse response) {
        List<ColumnInfo> columnInfo = response.columnInfo();
        List<Row> rows = response.rows();
        List<Co2DataPoint> dataPoints = new ArrayList<>();

        for (Row row : rows) {
            dataPoints.add(parseCo2Datapoint(columnInfo, row));
        }

        return dataPoints;
    }

    private Co2DataPoint parseCo2Datapoint(List<ColumnInfo> columnInfo, Row row) {
        AwsTimestreamSensorDatapoint datapoint = new AwsTimestreamSensorDatapoint(columnInfo, row);

        String stationId = datapoint.getStationId();
        String timestamp = datapoint.getTimestamp();
        int value = datapoint.getMeasureAsBigint();

        return new Co2DataPoint(stationId, Instant.ofEpochMilli(Long.parseLong(timestamp)), value);
    }

    @Override
    public void save(Co2DataPoint dataPoint) {

    }
}
