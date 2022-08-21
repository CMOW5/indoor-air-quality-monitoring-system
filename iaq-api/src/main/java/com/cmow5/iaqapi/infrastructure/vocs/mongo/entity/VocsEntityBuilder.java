package com.cmow5.iaqapi.infrastructure.vocs.mongo.entity;

import java.time.Instant;

public class VocsEntityBuilder {

    private String stationId;

    private Instant timestamp;

    private int vocs;

    private String type = "vocs";

    public VocsEntityBuilder stationId(String stationId) {
        this.stationId = stationId;
        return this;
    }

    public VocsEntityBuilder vocs(int vocs) {
        this.vocs = vocs;
        return this;
    }

    public VocsEntityBuilder timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public VocsEntity build() {
        VocsEntity.VocsMetadata metadata = new VocsEntity.VocsMetadata();
        metadata.setStationId(stationId);
        metadata.setType(type);

        VocsEntity entity = new VocsEntity();
        entity.setMetadata(metadata);
        entity.setVocs(vocs);
        entity.setTimestamp(timestamp);

        return entity;
    }

}
