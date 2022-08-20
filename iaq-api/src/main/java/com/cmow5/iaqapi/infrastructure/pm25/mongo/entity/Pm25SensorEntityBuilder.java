package com.cmow5.iaqapi.infrastructure.pm25.mongo.entity;

import java.time.Instant;

/**
 * A builder for {@link Pm25SensorEntity}
 */
public class Pm25SensorEntityBuilder {

    private String stationId;

    private Instant timestamp;

    private int pm25;

    private String type = "pm25";

    public Pm25SensorEntityBuilder stationId(String stationId) {
        this.stationId = stationId;
        return this;
    }

    public Pm25SensorEntityBuilder pm25(int pm25) {
        this.pm25 = pm25;
        return this;
    }

    public Pm25SensorEntityBuilder timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Pm25SensorEntity build() {
        Pm25SensorEntity.Pm25SensorMetadata metadata = new Pm25SensorEntity.Pm25SensorMetadata();
        metadata.setStationId(stationId);
        metadata.setType(type);

        Pm25SensorEntity entity = new Pm25SensorEntity();
        entity.setMetadata(metadata);
        entity.setPm25(pm25);
        entity.setTimestamp(timestamp);

        return entity;
    }

}
