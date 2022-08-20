package com.cmow5.iaqapi.infrastructure.vocs.mongo.entity;

import com.cmow5.iaqapi.domain.vocs.VocsDataPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.vocs.mongo.entity.VocsEntity.VOCS_SENSOR_COLLECTION_NAME;
import static java.util.stream.Collectors.toList;

@Document(VOCS_SENSOR_COLLECTION_NAME)
public class VocsEntity {
    public static final String TIMESTAMP_FIELD = "timestamp";

    public static final String STATION_ID_FIELD = "metadata.stationId";

    public final static String VOCS_SENSOR_COLLECTION_NAME = "vocs";

    @Id
    private String id;

    @Field(TIMESTAMP_FIELD)
    private Instant timestamp;

    private int vocs;

    private VocsMetadata metadata;

    public VocsEntity() {}

    public static List<VocsDataPoint> toDomain(List<VocsEntity> entities) {
        return entities.stream().map(VocsEntity::toDomain).collect(toList());
    }

    public static VocsDataPoint toDomain(VocsEntity entity) {
        return new VocsDataPoint(entity.getMetadata().getStationId(), entity.getTimestamp(), entity.getVocs());
    }

    public static List<VocsEntity> fromDomain(List<VocsDataPoint> entities) {
        return entities.stream().map(VocsEntity::fromDomain).collect(toList());
    }

    public static VocsEntity fromDomain(VocsDataPoint dataPoint) {
        return new VocsEntityBuilder()
                .stationId(dataPoint.getStationId())
                .vocs(dataPoint.getValue())
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

    public int getVocs() {
        return vocs;
    }

    public void setVocs(int vocs) {
        this.vocs = vocs;
    }

    public VocsMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(VocsMetadata metadata) {
        this.metadata = metadata;
    }

    public static class VocsMetadata {
        private String stationId;
        private String type = "vocs";

        public VocsMetadata() {

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
            return "VocsMetadata{" +
                    "sensorId=" + stationId +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "VocsEntity{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", vocs=" + vocs +
                ", metadata=" + metadata +
                '}';
    }
}
