package com.cmow5.iaqapi.infrastructure.general.database.awstimestream.utils;

import java.time.Instant;

public class AwsTimestreamQueryBuilder {

    public enum TimestreamSort {
        ASC("ASC"), DESC("DESC");

        private final String sortKey;

        TimestreamSort(String sortKey) {
            this.sortKey = sortKey;
        }
    }

    private final static String BIGINT_MEASURE = "measure_value::bigint";

    private final static String DOUBLE_MEASURE = "measure_value::double";

    private final String database;

    private final String table;

    private final String stationId;

    private Instant start;

    private Instant end;

    private String measure = BIGINT_MEASURE;

    private TimestreamSort sort = TimestreamSort.DESC;


    public AwsTimestreamQueryBuilder(String database, String table, String stationId) {
        this.database = database;
        this.table = table;
        this.stationId = stationId;
    }

    public AwsTimestreamQueryBuilder start(Instant start) {
        this.start = start;
        return this;
    }

    public AwsTimestreamQueryBuilder end(Instant end) {
        this.end = end;
        return this;
    }

    public AwsTimestreamQueryBuilder sort(String sort) {
        this.sort = TimestreamSort.valueOf(sort);
        return this;
    }

    public AwsTimestreamQueryBuilder bigIntMeasure() {
        this.measure = BIGINT_MEASURE;
        return this;
    }

    public AwsTimestreamQueryBuilder doubleMeasure() {
        this.measure = DOUBLE_MEASURE;
        return this;
    }


    public String build() {
        return String.format(
                        "SELECT station_id, %s, to_milliseconds(time) AS timestamp " +
                        "FROM \"%s\".\"%s\" " +
                        "WHERE station_id = '%s' AND (time between from_milliseconds(%s) and from_milliseconds(%s)) ORDER BY time %s",
                        measure, database, table, stationId, start.toEpochMilli(), end.toEpochMilli(), sort);
    }
}
