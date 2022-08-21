package com.cmow5.iaqapi.infrastructure.station.mongo.entity;

import com.cmow5.iaqapi.domain.station.Station;
import com.cmow5.iaqapi.domain.station.StationMetadata;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

import static com.cmow5.iaqapi.infrastructure.station.mongo.entity.StationEntity.STATIONS_COLLECTION_NAME;

@Document(STATIONS_COLLECTION_NAME)
public class StationEntity {

    public final static String STATIONS_COLLECTION_NAME = "stations";

    @Id
    private String id;

    private String name;

    private StationMetadataEntity metadata;

    public StationEntity() {
    }

    public static Station toDomain(StationEntity entity) {
        StationMetadata metadata = StationMetadataEntity.toDomain(entity.getMetadata());
        return new Station(entity.getId(), entity.getName(), metadata);
    }

    public static List<Station> toDomain(List<StationEntity> entities) {
        return entities.stream().map(StationEntity::toDomain).collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StationMetadataEntity getMetadata() {
        return metadata;
    }

    public void setMetadata(StationMetadataEntity metadata) {
        this.metadata = metadata;
    }
}
