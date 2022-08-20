package com.cmow5.iaqapi.infrastructure.humidity.mongo.entity;

import com.cmow5.iaqapi.domain.humidity.HumidityDataPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.humidity.mongo.entity.HumidityEntity.HUMIDITY_COLLECTION_NAME;
import static java.util.stream.Collectors.toList;

@Document(HUMIDITY_COLLECTION_NAME)
public class HumidityEntity {
    public static final String HUMIDITY_COLLECTION_NAME = "humidity";

    public static final String TIMESTAMP_FIELD = "timestamp";

    public static final String STATION_ID_FIELD = "metadata.stationId";

    @Id
    private String id;

    @Field(TIMESTAMP_FIELD)
    private Instant timestamp;

    private int humidity;

    private HumidityMetadata metadata;

    public HumidityEntity() {}

    public static List<HumidityDataPoint> toDomain(List<HumidityEntity> entities) {
        return entities.stream().map(HumidityEntity::toDomain).collect(toList());
    }

    public static HumidityDataPoint toDomain(HumidityEntity entity) {
        return new HumidityDataPoint(entity.getMetadata().getStationId(), entity.getTimestamp(), entity.getHumidity());
    }

    public static List<HumidityEntity> fromDomain(List<HumidityDataPoint> entities) {
        return entities.stream().map(HumidityEntity::fromDomain).collect(toList());
    }

    public static HumidityEntity fromDomain(HumidityDataPoint dataPoint) {
        return new HumidityEntityBuilder()
                .stationId(dataPoint.getStationId())
                .humidity(dataPoint.getValue())
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

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public HumidityMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(HumidityMetadata metadata) {
        this.metadata = metadata;
    }

    public static class HumidityMetadata {
        private String stationId;
        private String type = "humidity";

        public HumidityMetadata() {

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
            return "HumidityMetadata{" +
                    "sensorId=" + stationId +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HumidityEntity{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", humidity=" + humidity +
                ", metadata=" + metadata +
                '}';
    }
}
