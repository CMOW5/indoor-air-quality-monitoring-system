package com.cmow5.iaqapi.infrastructure.pm10.entity;

import java.time.Instant;

public class Pm10EntityBuilder {
    private String stationId;

    private Instant timestamp;

    private int pm10;

    private String type = "pm10";

    public Pm10EntityBuilder stationId(String stationId) {
        this.stationId = stationId;
        return this;
    }

    public Pm10EntityBuilder pm10(int pm10) {
        this.pm10 = pm10;
        return this;
    }

    public Pm10EntityBuilder timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Pm10Entity build() {
        Pm10Entity.Pm10SensorMetadata metadata = new Pm10Entity.Pm10SensorMetadata();
        metadata.setStationId(stationId);
        metadata.setType(type);

        Pm10Entity entity = new Pm10Entity();
        entity.setMetadata(metadata);
        entity.setPm10(pm10);
        entity.setTimestamp(timestamp);

        return entity;
    }

}
