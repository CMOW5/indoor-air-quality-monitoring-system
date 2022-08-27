package com.cmow5.iaqapi.infrastructure.co2.mongo.entity;

import com.cmow5.iaqapi.domain.co2.Co2DataPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.co2.mongo.entity.Co2Entity.CO2_COLLECTION_NAME;
import static java.util.stream.Collectors.toList;

@Document(CO2_COLLECTION_NAME)
public class Co2Entity {

    public static final String CO2_COLLECTION_NAME = "co2";

    public static final String TIMESTAMP_FIELD = "timestamp";

    public static final String STATION_ID_FIELD = "metadata.stationId";

    @Id
    private String id;

    @Field(TIMESTAMP_FIELD)
    private Instant timestamp;

    private int co2;

    private Co2Metadata metadata;

    public Co2Entity() {}

    public static List<Co2DataPoint> toDomain(List<Co2Entity> entities) {
        return entities.stream().map(Co2Entity::toDomain).collect(toList());
    }

    public static Co2DataPoint toDomain(Co2Entity entity) {
        return new Co2DataPoint(entity.getMetadata().getStationId(), entity.getTimestamp(), entity.getCo2());
    }

    public static List<Co2Entity> fromDomain(List<Co2DataPoint> entities) {
        return entities.stream().map(Co2Entity::fromDomain).collect(toList());
    }

    public static Co2Entity fromDomain(Co2DataPoint dataPoint) {
        return new Co2EntityBuilder()
                            .stationId(dataPoint.getStationId())
                            .co2(dataPoint.getValue())
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

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public Co2Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Co2Metadata metadata) {
        this.metadata = metadata;
    }

    public static class Co2Metadata {
        private String stationId;
        private String type = "co2";

        public Co2Metadata() {

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
            return "Co2Metadata{" +
                    "sensorId=" + stationId +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Co2Entity{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", co2=" + co2 +
                ", metadata=" + metadata +
                '}';
    }
}
