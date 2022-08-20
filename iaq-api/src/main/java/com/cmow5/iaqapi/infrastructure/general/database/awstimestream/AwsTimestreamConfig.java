package com.cmow5.iaqapi.infrastructure.general.database.awstimestream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.timestreamquery.TimestreamQueryClient;

import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.AWS_TIMESTREAM_PROFILE;

@Configuration
@Profile(AWS_TIMESTREAM_PROFILE)
public class AwsTimestreamConfig {

    public static final String AWS_TIMESTREAM_PROFILE = "awstimestream";

    public static final String AWS_TIMESTREAM_QUERY_CLIENT_BEAN = "timestreamQueryClient";

    public static final String DATABASE = "monitoring";

    @Bean(AWS_TIMESTREAM_QUERY_CLIENT_BEAN)
    public TimestreamQueryClient timestreamQueryClient() {
        return TimestreamQueryClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }
}
