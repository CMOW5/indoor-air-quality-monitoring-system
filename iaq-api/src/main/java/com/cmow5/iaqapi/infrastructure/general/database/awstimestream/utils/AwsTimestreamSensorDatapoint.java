package com.cmow5.iaqapi.infrastructure.general.database.awstimestream.utils;

import com.google.common.collect.Iterables;
import software.amazon.awssdk.services.timestreamquery.model.ColumnInfo;
import software.amazon.awssdk.services.timestreamquery.model.Datum;
import software.amazon.awssdk.services.timestreamquery.model.Row;

import java.util.List;

public class AwsTimestreamSensorDatapoint {

    private final String VALUE_FIELD = "value";

    private final List<ColumnInfo> columnInfo;

    private final Row row;

    public AwsTimestreamSensorDatapoint(List<ColumnInfo> columnInfo, Row row) {

        this.columnInfo = columnInfo;
        this.row = row;
    }

    public String getStationId() {
        return this.parseField("station_id", this.columnInfo, row);
    }

    public String getTimestamp() {
        return this.parseField("timestamp", this.columnInfo, row);
    }

    public int getMeasureValue() {
        return Integer.parseInt(this.parseField(VALUE_FIELD, this.columnInfo, row));
    }

    @Deprecated
    public int getMeasureAsBigint() {
        return Integer.parseInt(this.parseField("measure_value::bigint", this.columnInfo, row));
    }

    @Deprecated
    public String getMeasureAsDouble() {
        return this.parseField("measure_value::double", this.columnInfo, row);
    }

    private String parseField(String field, List<ColumnInfo> columnInfo, Row row) {
        int index = Iterables.indexOf(columnInfo, c -> field.equals(c.name()));
        List<Datum> data = row.data();
        return data.get(index).scalarValue();
    }


}
