package com.cmow5.iaqapi.infrastructure.vocs.awstimestream.repository;

import com.cmow5.iaqapi.domain.vocs.VocsDataPoint;
import com.cmow5.iaqapi.domain.vocs.repository.VocsRepository;
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
import static com.cmow5.iaqapi.infrastructure.vocs.awstimestream.repository.VocsAwsTimestreamRepository.VOCS_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN;

@Repository(VOCS_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN)
@Profile(AWS_TIMESTREAM_PROFILE)
public class VocsAwsTimestreamRepository implements VocsRepository {

    public static final String VOCS_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN = "vocsAwsTimestreamRepository";

    private static final Logger log = LogManager.getLogger(VocsAwsTimestreamRepository.class);

    private static final String AWS_TIMESTREAM_VOCS_SENSOR_TABLE = "vocs";

    private final TimestreamQueryClient timestreamQueryClient;

    @Autowired
    public VocsAwsTimestreamRepository(@Qualifier(AWS_TIMESTREAM_QUERY_CLIENT_BEAN) TimestreamQueryClient timestreamQueryClient) {
        this.timestreamQueryClient = timestreamQueryClient;
    }

    public List<VocsDataPoint> findBetweenDates(String stationId, Instant start, Instant end, String sort) {

        String query = new AwsTimestreamQueryBuilder(DATABASE, AWS_TIMESTREAM_VOCS_SENSOR_TABLE, stationId)
                .start(start)
                .end(end)
                .sort(sort)
                .bigIntMeasure()
                .build();

        log.info("created query = " + query);

        QueryRequest queryRequest = QueryRequest.builder().queryString(query).build();
        final QueryIterable queryResponseIterator = timestreamQueryClient.queryPaginator(queryRequest);

        List<VocsDataPoint> dataPoints = new ArrayList<>();

        for(QueryResponse queryResponse : queryResponseIterator) {
            dataPoints.addAll(parseDatapointFromQueryResult(queryResponse));
        }

        return dataPoints;
    }

    private List<VocsDataPoint> parseDatapointFromQueryResult(QueryResponse response) {
        List<ColumnInfo> columnInfo = response.columnInfo();
        List<Row> rows = response.rows();
        List<VocsDataPoint> dataPoints = new ArrayList<>();

        for (Row row : rows) {
            dataPoints.add(parseVocsDatapoint(columnInfo, row));
        }

        return dataPoints;
    }

    private VocsDataPoint parseVocsDatapoint(List<ColumnInfo> columnInfo, Row row) {
        AwsTimestreamSensorDatapoint datapoint = new AwsTimestreamSensorDatapoint(columnInfo, row);

        String stationId = datapoint.getStationId();
        String timestamp = datapoint.getTimestamp();
        int value = datapoint.getMeasureAsBigint();

        return new VocsDataPoint(stationId, Instant.ofEpochMilli(Long.parseLong(timestamp)), value);
    }

    @Override
    public void save(VocsDataPoint dataPoint) {

    }
}
