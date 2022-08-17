package com.cmow5.iaqapi.infrastructure.station.repository.awstimestream;

import com.cmow5.iaqapi.domain.station.Station;
import com.cmow5.iaqapi.domain.station.StationMetadata;
import com.cmow5.iaqapi.domain.station.repository.StationRepository;
import com.google.common.collect.Iterables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.timestreamquery.TimestreamQueryClient;
import software.amazon.awssdk.services.timestreamquery.model.*;
import software.amazon.awssdk.services.timestreamquery.paginators.QueryIterable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.AWS_TIMESTREAM_PROFILE;
import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.AWS_TIMESTREAM_QUERY_CLIENT_BEAN;

@Repository
@Profile(AWS_TIMESTREAM_PROFILE)
public class StationAwsTimestreamRepository implements StationRepository {

    private final TimestreamQueryClient timestreamQueryClient;

    private final static String FIND_ALL_QUERY = "SELECT * FROM \"monitoring\".\"stations\" LIMIT 2";

    private static final Logger log = LogManager.getLogger(StationAwsTimestreamRepository.class);

    public StationAwsTimestreamRepository(@Qualifier(AWS_TIMESTREAM_QUERY_CLIENT_BEAN) TimestreamQueryClient timestreamQueryClient) {
        this.timestreamQueryClient = timestreamQueryClient;
    }

    @Override
    public List<Station> findAll() {

        log.info("getting this from timestream :)");

        QueryRequest queryRequest = QueryRequest.builder().queryString(FIND_ALL_QUERY).build();
        final QueryIterable queryResponseIterator = timestreamQueryClient.queryPaginator(queryRequest);

        List<Station> stations = new ArrayList<>();

        for(QueryResponse queryResponse : queryResponseIterator) {
            stations.addAll(parseStationsFromQueryResult(queryResponse));
        }

        return stations;
    }

    private List<Station> parseStationsFromQueryResult(QueryResponse response) {
        List<ColumnInfo> columnInfo = response.columnInfo();
        List<Row> rows = response.rows();
        List<Station> stations = new ArrayList<>();

        for (Row row : rows) {
            stations.add(parseStation(columnInfo, row));
            System.out.println(parseRow(columnInfo, row));
        }

        return stations;
    }

    private Station parseStation(List<ColumnInfo> columnInfo, Row row) {
        String name = parseField("name", columnInfo, row);
        String id = parseField("id", columnInfo, row);
        String principal = parseField("metadata.principal", columnInfo, row);

        StationMetadata metadata = new StationMetadata(Boolean.parseBoolean(principal));
        return new Station(id, name, metadata);
    }

    private String parseField(String field, List<ColumnInfo> columnInfo, Row row) {
        int index = Iterables.indexOf(columnInfo, c -> field.equals(c.name()));
        List<Datum> data = row.data();
        return data.get(index).scalarValue();
    }

    /**
     * testing. delete this
     */

    private static final long ONE_GB_IN_BYTES = 1073741824L;

    private void parseQueryResult(QueryResponse response) {
        final QueryStatus currentStatusOfQuery = response.queryStatus();

        System.out.println("Query progress so far: " + currentStatusOfQuery.progressPercentage() + "%");

        double bytesScannedSoFar = ((double) currentStatusOfQuery.cumulativeBytesScanned() / ONE_GB_IN_BYTES);
        System.out.println("Bytes scanned so far: " + bytesScannedSoFar + " GB");

        double bytesMeteredSoFar = ((double) currentStatusOfQuery.cumulativeBytesMetered() / ONE_GB_IN_BYTES);
        System.out.println("Bytes metered so far: " + bytesMeteredSoFar + " GB");

        List<ColumnInfo> columnInfo = response.columnInfo();
        List<Row> rows = response.rows();

        System.out.println("Metadata: " + columnInfo);
        System.out.println("Data: ");

        // iterate every row
        for (Row row : rows) {
            System.out.println(parseRow(columnInfo, row));
        }
    }

    private String parseRow(List<ColumnInfo> columnInfo, Row row) {
        List<Datum> data = row.data();
        List<String> rowOutput = new ArrayList<>();
        // iterate every column per row
        for (int j = 0; j < data.size(); j++) {
            ColumnInfo info = columnInfo.get(j);
            Datum datum = data.get(j);
            log.info("cmex = info = " + info);
            log.info("cmex = datum = " + datum);
            rowOutput.add(parseDatum(info, datum));
        }
        return String.format("{%s}", rowOutput.stream().map(Object::toString).collect(Collectors.joining(",")));
    }

    private String parseDatum(ColumnInfo info, Datum datum) {
        if (datum.nullValue() != null && datum.nullValue()) {
            return info.name() + "=" + "NULL";
        }
        Type columnType = info.type();
        // If the column is of TimeSeries Type
        if (columnType.timeSeriesMeasureValueColumnInfo() != null) {
            return parseTimeSeries(info, datum);
        }
        // If the column is of Array Type
        else if (columnType.arrayColumnInfo() != null) {
            List<Datum> arrayValues = datum.arrayValue();
            return info.name() + "=" + parseArray(info.type().arrayColumnInfo(), arrayValues);
        }
        // If the column is of Row Type
        else if (columnType.rowColumnInfo() != null && columnType.rowColumnInfo().size() > 0) {
            List<ColumnInfo> rowColumnInfo = info.type().rowColumnInfo();
            Row rowValues = datum.rowValue();
            return parseRow(rowColumnInfo, rowValues);
        }
        // If the column is of Scalar Type
        else {
            return parseScalarType(info, datum);
        }
    }

    private String parseTimeSeries(ColumnInfo info, Datum datum) {
        List<String> timeSeriesOutput = new ArrayList<>();
        for (TimeSeriesDataPoint dataPoint : datum.timeSeriesValue()) {
            timeSeriesOutput.add("{time=" + dataPoint.time() + ", value=" +
                    parseDatum(info.type().timeSeriesMeasureValueColumnInfo(), dataPoint.value()) + "}");
        }
        return String.format("[%s]", timeSeriesOutput.stream().map(Object::toString).collect(Collectors.joining(",")));
    }

    private String parseScalarType(ColumnInfo info, Datum datum) {
        return parseColumnName(info) + datum.scalarValue();
    }

    private String parseColumnName(ColumnInfo info) {
        return info.name() == null ? "" : info.name() + "=";
    }

    private String parseArray(ColumnInfo arrayColumnInfo, List<Datum> arrayValues) {
        List<String> arrayOutput = new ArrayList<>();
        for (Datum datum : arrayValues) {
            arrayOutput.add(parseDatum(arrayColumnInfo, datum));
        }
        return String.format("[%s]", arrayOutput.stream().map(Object::toString).collect(Collectors.joining(",")));
    }
}
