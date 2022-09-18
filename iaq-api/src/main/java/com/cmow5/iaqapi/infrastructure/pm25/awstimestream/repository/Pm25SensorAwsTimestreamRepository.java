package com.cmow5.iaqapi.infrastructure.pm25.awstimestream.repository;

import com.cmow5.iaqapi.domain.pm25.Pm25SensorDataPoint;
import com.cmow5.iaqapi.domain.pm25.repository.Pm25SensorRepository;
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
import static com.cmow5.iaqapi.infrastructure.pm25.awstimestream.repository.Pm25SensorAwsTimestreamRepository.PM25_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN;

@Repository(PM25_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN)
@Profile(AWS_TIMESTREAM_PROFILE)
public class Pm25SensorAwsTimestreamRepository implements Pm25SensorRepository {

    public static final String PM25_SENSOR_AWS_TIMESTREAM_REPOSITORY_BEAN = "pm25SensorAwsTimestreamRepository";

    private static final Logger log = LogManager.getLogger(Pm25SensorAwsTimestreamRepository.class);

    private static final String AWS_TIMESTREAM_PM25_SENSOR_TABLE = "pm25";

    private final TimestreamQueryClient timestreamQueryClient;

    @Autowired
    public Pm25SensorAwsTimestreamRepository(@Qualifier(AWS_TIMESTREAM_QUERY_CLIENT_BEAN) TimestreamQueryClient timestreamQueryClient) {
        this.timestreamQueryClient = timestreamQueryClient;
    }

   public List<Pm25SensorDataPoint> findBetweenDates(String stationId, Instant start, Instant end, String sort) {
       String query = new AwsTimestreamQueryBuilder(DATABASE, AWS_TIMESTREAM_PM25_SENSOR_TABLE, stationId)
               .start(start)
               .end(end)
               .sort(sort)
               .bigIntMeasure()
               .build();

       log.info("created query = " + query);

       QueryRequest queryRequest = QueryRequest.builder().queryString(query).build();
       final QueryIterable queryResponseIterator = timestreamQueryClient.queryPaginator(queryRequest);

       List<Pm25SensorDataPoint> dataPoints = new ArrayList<>();

       for(QueryResponse queryResponse : queryResponseIterator) {
           dataPoints.addAll(parseDatapointFromQueryResult(queryResponse));
       }

       return dataPoints;
   }

    private List<Pm25SensorDataPoint> parseDatapointFromQueryResult(QueryResponse response) {
        List<ColumnInfo> columnInfo = response.columnInfo();
        List<Row> rows = response.rows();
        List<Pm25SensorDataPoint> dataPoints = new ArrayList<>();

        for (Row row : rows) {
            dataPoints.add(parsePm25Datapoint(columnInfo, row));
        }

        return dataPoints;
    }

    private Pm25SensorDataPoint parsePm25Datapoint(List<ColumnInfo> columnInfo, Row row) {
        AwsTimestreamSensorDatapoint datapoint = new AwsTimestreamSensorDatapoint(columnInfo, row);

        String stationId = datapoint.getStationId();
        String timestamp = datapoint.getTimestamp();
        int value = datapoint.getMeasureValue();

        return new Pm25SensorDataPoint(stationId, Instant.ofEpochMilli(Long.parseLong(timestamp)), value);
    }

    @Override
    public void save(Pm25SensorDataPoint dataPoint) {
        throw new UnsupportedOperationException("unsupported save in timestream from backend");
    }


}
