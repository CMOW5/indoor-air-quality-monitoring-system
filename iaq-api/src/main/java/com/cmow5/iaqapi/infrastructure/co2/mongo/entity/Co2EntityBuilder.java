package com.cmow5.iaqapi.infrastructure.co2.mongo.entity;


import java.time.Instant;

public class Co2EntityBuilder {
    private String stationId;

    private Instant timestamp;

    private int co2;

    private String type = "co2";

    public Co2EntityBuilder stationId(String stationId) {
        this.stationId = stationId;
        return this;
    }

    public Co2EntityBuilder co2(int co2) {
        this.co2 = co2;
        return this;
    }

    public Co2EntityBuilder timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Co2Entity build() {
        Co2Entity.Co2Metadata metadata = new Co2Entity.Co2Metadata();
        metadata.setStationId(stationId);
        metadata.setType(type);

        Co2Entity entity = new Co2Entity();
        entity.setMetadata(metadata);
        entity.setCo2(co2);
        entity.setTimestamp(timestamp);

        return entity;
    }

}
