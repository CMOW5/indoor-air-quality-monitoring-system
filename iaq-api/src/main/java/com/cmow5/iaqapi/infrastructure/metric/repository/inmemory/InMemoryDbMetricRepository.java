package com.cmow5.iaqapi.infrastructure.metric.repository.inmemory;

import com.cmow5.iaqapi.domain.metric.Metric;
import com.cmow5.iaqapi.domain.metric.MetricMetadata;
import com.cmow5.iaqapi.domain.metric.repository.MetricRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.inmemory.InMemoryDbConfig.IN_MEMORY_PROFILE;
import static com.cmow5.iaqapi.infrastructure.metric.repository.inmemory.InMemoryDbMetricRepository.IN_MEMORY_DB_METRICS_REPOSITORY_BEAN;


@Repository(IN_MEMORY_DB_METRICS_REPOSITORY_BEAN)
@Profile(IN_MEMORY_PROFILE)
public class InMemoryDbMetricRepository implements MetricRepository {

    public static final String IN_MEMORY_DB_METRICS_REPOSITORY_BEAN = "inMemoryDbMetricRepository";

    private static final Logger log = LogManager.getLogger(InMemoryDbMetricRepository.class);

    private final static List<Metric> HARDCODED_METRICS;

    static {
        HARDCODED_METRICS = new ArrayList<>();

        HARDCODED_METRICS.add(createMetric("temperature", "Temperature", "Temperature Description", "°C",
                "rgba(220, 53, 70, 1)", "rgba(220, 53, 70, 0.5)", "device_thermostat", 0));

        HARDCODED_METRICS.add(createMetric("humidity", "Humidity", "Humidity Description", "%",
                "rgba(0, 122, 255, 1)", "rgba(0, 122, 255, 0.5)", "airwave", 0));

        HARDCODED_METRICS.add(createMetric("pm25", "PM 2.5", "PM2.5 Description", "μg/m3",
                "rgba(0, 122, 255, 1)", "rgba(0, 122, 255, 0.5)", "snowing", 0));

        HARDCODED_METRICS.add(createMetric("pm10", "PM 10", "PM10 Description", "μg/m3",
                "rgba(0, 122, 255, 1)", "rgba(0, 122, 255, 0.5)", "grain", 0));

        HARDCODED_METRICS.add(createMetric("vocs", "VOCS", "VOCS Description", "ppb",
                "rgba(23, 162, 183, 1)", "rgba(23, 162, 183, 0.5)", "airwave", 0));

        HARDCODED_METRICS.add(createMetric("co2", "CO2", "CO2 Description", "ppm",
                "rgba(39, 168, 68, 1)", "rgba(39, 168, 68, 0.5)", "co2", 400));

        /*
        METRICS.add(createMetric("", "", "", "",
                "", "", "", 0));
        */
    }

    private static Metric createMetric(String id, String name, String description, String unit, String color,
                                       String backgroundColor, String icon, int min) {
        MetricMetadata metadata = new MetricMetadata(color, backgroundColor, icon, min);
        return new Metric(id, name, description, unit, metadata);
    }

    @Override
    public List<Metric> findAll() {
        log.info("getting metrics from inmemory");
        return HARDCODED_METRICS;
    }
}
