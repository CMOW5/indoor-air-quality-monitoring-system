package com.cmow5.iaqapi.infrastructure.temperature.entity;

import java.time.Instant;

/**
 * A builder for {@link TempSensorEntity}
 */
public class TempSensorEntityBuilder {

    private String stationId;

    private Instant timestamp;

    private int temperature;

    private String type = "temperature";

    public TempSensorEntityBuilder stationId(String stationId) {
        this.stationId = stationId;
        return this;
    }

    public TempSensorEntityBuilder temperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public TempSensorEntityBuilder timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public TempSensorEntity build() {
        TempSensorEntity.TempSensorMetadata metadata = new TempSensorEntity.TempSensorMetadata();
        metadata.setStationId(stationId);
        metadata.setType(type);

        TempSensorEntity entity = new TempSensorEntity();
        entity.setMetadata(metadata);
        entity.setTemperature(temperature);
        entity.setTimestamp(timestamp);

        return entity;
    }

}
