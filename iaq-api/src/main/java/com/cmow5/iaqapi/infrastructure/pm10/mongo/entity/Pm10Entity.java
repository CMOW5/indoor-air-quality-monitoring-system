package com.cmow5.iaqapi.infrastructure.pm10.mongo.entity;

import com.cmow5.iaqapi.domain.pm10.Pm10DataPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.pm10.mongo.entity.Pm10Entity.PM10_COLLECTION_NAME;
import static java.util.stream.Collectors.toList;

@Document(PM10_COLLECTION_NAME)
public class Pm10Entity {
    public static final String PM10_COLLECTION_NAME = "pm10";

    public static final String TIMESTAMP_FIELD = "timestamp";

    public static final String STATION_ID_FIELD = "metadata.stationId";

    @Id
    private String id;

    @Field(TIMESTAMP_FIELD)
    private Instant timestamp;

    private int pm10;

    private Pm10SensorMetadata metadata;

    public Pm10Entity() {}

    public static List<Pm10DataPoint> toDomain(List<Pm10Entity> entities) {
        return entities.stream().map(Pm10Entity::toDomain).collect(toList());
    }

    public static Pm10DataPoint toDomain(Pm10Entity entity) {
        return new Pm10DataPoint(entity.getMetadata().getStationId(), entity.getTimestamp(), entity.getPm10());
    }

    public static List<Pm10Entity> fromDomain(List<Pm10DataPoint> entities) {
        return entities.stream().map(Pm10Entity::fromDomain).collect(toList());
    }

    public static Pm10Entity fromDomain(Pm10DataPoint dataPoint) {
        return new Pm10EntityBuilder()
                .stationId(dataPoint.getStationId())
                .pm10(dataPoint.getValue())
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

    public int getPm10() {
        return pm10;
    }

    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public Pm10SensorMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Pm10SensorMetadata metadata) {
        this.metadata = metadata;
    }

    public static class Pm10SensorMetadata {
        private String stationId;
        private String type = "pm10";

        public Pm10SensorMetadata() {

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
            return "Pm10SensorMetadata{" +
                    "sensorId=" + stationId +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Pm10SensorEntity{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", pm10=" + pm10 +
                ", metadata=" + metadata +
                '}';
    }
}
