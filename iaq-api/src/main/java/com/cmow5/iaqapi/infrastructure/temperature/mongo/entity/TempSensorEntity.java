package com.cmow5.iaqapi.infrastructure.temperature.mongo.entity;

import com.cmow5.iaqapi.domain.temperature.TempSensorDataPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * The entity we save in mongo to store the data from the temperature sensor
 */
@Document(TempSensorEntity.TEMP_SENSOR_COLLECTION_NAME)
public class TempSensorEntity {

    public static final String TIMESTAMP_FIELD = "timestamp";

    public static final String STATION_ID_FIELD = "metadata.stationId";

    public final static String TEMP_SENSOR_COLLECTION_NAME = "temperature";

    @Id
    private String id;

    @Field(TIMESTAMP_FIELD)
    private Instant timestamp;

    private int temperature;

    private TempSensorMetadata metadata;

    public TempSensorEntity() {}

    public static List<TempSensorDataPoint> toDomain(List<TempSensorEntity> entities) {
        return entities.stream().map(TempSensorEntity::toDomain).collect(toList());
    }

    public static TempSensorDataPoint toDomain(TempSensorEntity entity) {
        return new TempSensorDataPoint(entity.getMetadata().getStationId(), entity.getTimestamp(), entity.getTemperature());
    }

    public static List<TempSensorEntity> fromDomain(List<TempSensorDataPoint> dataPoints) {
        return dataPoints.stream().map(TempSensorEntity::fromDomain).collect(toList());
    }

    public static TempSensorEntity fromDomain(TempSensorDataPoint dataPoint) {
        return new TempSensorEntityBuilder().stationId(dataPoint.getStationId())
                                            .temperature(dataPoint.getValue())
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

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public TempSensorMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(TempSensorMetadata metadata) {
        this.metadata = metadata;
    }

    public static class TempSensorMetadata {
        private String stationId;
        private String type = "temperature";

        public TempSensorMetadata() {

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
            return "TempSensorMetadata{" +
                    "sensorId=" + stationId +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TempSensorEntity{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", temp=" + temperature +
                ", metadata=" + metadata +
                '}';
    }
}
