package com.cmow5.iaqapi.domain.station;

public class Station {

    private final String id;

    private final String name;

    private final StationMetadata metadata;

    public Station(String id, String name, StationMetadata metadata) {
        this.id = id;
        this.name = name;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StationMetadata getMetadata() {
        return metadata;
    }
}
