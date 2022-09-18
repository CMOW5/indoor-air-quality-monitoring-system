package com.cmow5.iaqapi.infrastructure.general.database.awstimestream.utils;

import java.time.Duration;
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

    private final static int ONE_HOUR_MINUTES = 60;

    private final static int ONE_DAY_MINUTES = 1440;

    private final static int ONE_MONTH_MINUTES = 43200;

    private final static int ONE_SECOND = 1;

    private final static int TEN_MINUTES_SECONDS = 600;

    private final static int ONE_HOUR_SECONDS = 3600;

    private final static int ONE_DAY_SECONDS = 86400;

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
        long durationInMinutes = Duration.between(start, end).toMinutes();
        int interval;

        // we want to find the average depending on the duration between the start and end dates
        // this is because using 1 second granularity in durations that are bigger than 1 hour will
        // return too many data points making the query to timeout. The frontend can't handle too many
        // data points as well
        if (durationInMinutes < ONE_HOUR_MINUTES) {
            interval = ONE_SECOND;
        } else if (durationInMinutes <= ONE_DAY_MINUTES) {
            interval = TEN_MINUTES_SECONDS;
        } else if (durationInMinutes < ONE_MONTH_MINUTES) {
            interval = ONE_HOUR_SECONDS;
        } else {
            interval = ONE_DAY_SECONDS;
        }

        // Find the average value binned at {interval} second intervals for a specific station between start and end dates.
        return String.format(
                "SELECT station_id, to_milliseconds(BIN(time, %ss)) AS timestamp, cast(ROUND(AVG(%s), 2) as Int) AS value " +
                        "FROM \"%s\".\"%s\" " +
                        "WHERE station_id = '%s' AND (time between from_milliseconds(%s) and from_milliseconds(%s)) " +
                        "GROUP BY station_id, BIN(time, %ss) " +
                        "ORDER BY timestamp %s",
                interval, measure, database, table, stationId, start.toEpochMilli(), end.toEpochMilli(), interval, sort);
    }
}
