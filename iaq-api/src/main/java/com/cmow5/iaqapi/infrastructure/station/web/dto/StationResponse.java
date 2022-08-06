package com.cmow5.iaqapi.infrastructure.station.web.dto;

import com.cmow5.iaqapi.domain.station.Station;

import java.util.List;
import java.util.stream.Collectors;

public class StationResponse {

    private String id;

    private String name;

    public StationResponse() {
    }

    public static StationResponse fromDomain(Station station) {
        StationResponse response = new StationResponse();
        response.setId(station.getId());
        response.setName(station.getName());
        return response;
    }

    public static List<StationResponse> fromDomain(List<Station> stations) {
        return stations.stream().map(StationResponse::fromDomain).collect(Collectors.toList());
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
}
