package com.cmow5.iaqapi.infrastructure.pm25.entity;

import com.cmow5.iaqapi.domain.pm25.Pm25SensorDataPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * The entity we save in mongo to store the data from the temperature sensor
 */
@Document(Pm25SensorEntity.PM25_SENSOR_COLLECTION_NAME)
public class Pm25SensorEntity {

    public static final String TIMESTAMP_FIELD = "timestamp";

    public static final String STATION_ID_FIELD = "metadata.stationId";

    public final static String PM25_SENSOR_COLLECTION_NAME = "pm25";

    @Id
    private String id;

    @Field(TIMESTAMP_FIELD)
    private Instant timestamp;

    private int pm25;

    private Pm25SensorMetadata metadata;

    public Pm25SensorEntity() {}

    public static List<Pm25SensorDataPoint> toDomain(List<Pm25SensorEntity> entities) {
        return entities.stream().map(Pm25SensorEntity::toDomain).collect(toList());
    }

    public static Pm25SensorDataPoint toDomain(Pm25SensorEntity entity) {
        return new Pm25SensorDataPoint(entity.getMetadata().getStationId(), entity.getTimestamp(), entity.getPm25());
    }

    public static List<Pm25SensorEntity> fromDomain(List<Pm25SensorDataPoint> entities) {
        return entities.stream().map(Pm25SensorEntity::fromDomain).collect(toList());
    }

    public static Pm25SensorEntity fromDomain(Pm25SensorDataPoint dataPoint) {
        return new Pm25SensorEntityBuilder()
                                        .stationId(dataPoint.getStationId())
                                        .pm25(dataPoint.getValue())
                                        .timestamp(dataPoint.getTimestamp())
                                        .build();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public Pm25SensorMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Pm25SensorMetadata metadata) {
        this.metadata = metadata;
    }

    public static class Pm25SensorMetadata {
        private String stationId;
        private String type = "pm25";

        public Pm25SensorMetadata() {

        }

        public String getStationId() {
            return stationId;
        }

        public void setStationId(String stationId) {
            this.stationId = stationId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Pm25SensorMetadata{" +
                    "sensorId=" + stationId +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Pm25SensorEntity{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", pm25=" + pm25 +
                ", metadata=" + metadata +
                '}';
    }
}
