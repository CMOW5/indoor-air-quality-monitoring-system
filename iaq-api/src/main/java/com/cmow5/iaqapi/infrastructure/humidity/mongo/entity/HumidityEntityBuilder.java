package com.cmow5.iaqapi.infrastructure.humidity.mongo.entity;


import java.time.Instant;

public class HumidityEntityBuilder {
    private String stationId;

    private Instant timestamp;

    private int humidity;

    private String type = "humidity";

    public HumidityEntityBuilder stationId(String stationId) {
        this.stationId = stationId;
        return this;
    }

    public HumidityEntityBuilder humidity(int humidity) {
        this.humidity = humidity;
        return this;
    }

    public HumidityEntityBuilder timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public HumidityEntity build() {
        HumidityEntity.HumidityMetadata metadata = new HumidityEntity.HumidityMetadata();
        metadata.setStationId(stationId);
        metadata.setType(type);

        HumidityEntity entity = new HumidityEntity();
        entity.setMetadata(metadata);
        entity.setHumidity(humidity);
        entity.setTimestamp(timestamp);

        return entity;
    }

}
