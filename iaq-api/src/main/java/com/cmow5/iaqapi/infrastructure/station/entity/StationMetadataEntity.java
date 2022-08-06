package com.cmow5.iaqapi.infrastructure.station.entity;

import com.cmow5.iaqapi.domain.station.StationMetadata;

public class StationMetadataEntity {

    private boolean principal;

    public StationMetadataEntity() {
    }

    public static StationMetadata toDomain(StationMetadataEntity entity) {
        return new StationMetadata(entity.isPrincipal());
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }
}
