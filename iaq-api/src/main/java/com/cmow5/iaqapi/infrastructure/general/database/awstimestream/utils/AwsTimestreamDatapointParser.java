package com.cmow5.iaqapi.infrastructure.general.database.awstimestream.utils;

import com.google.common.collect.Iterables;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.timestreamquery.model.ColumnInfo;
import software.amazon.awssdk.services.timestreamquery.model.Datum;
import software.amazon.awssdk.services.timestreamquery.model.Row;

import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.AWS_TIMESTREAM_PROFILE;

@Service
@Profile(AWS_TIMESTREAM_PROFILE)
public class AwsTimestreamDatapointParser {

    public String parseField(String field, List<ColumnInfo> columnInfo, Row row) {
        int index = Iterables.indexOf(columnInfo, c -> field.equals(c.name()));
        List<Datum> data = row.data();
        return data.get(index).scalarValue();
    }
}
