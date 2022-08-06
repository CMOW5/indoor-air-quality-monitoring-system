package com.cmow5.iaqapi.domain.humidity.service;


import com.cmow5.iaqapi.domain.humidity.HumidityDataPoint;
import com.cmow5.iaqapi.domain.humidity.repository.HumidityRepository;

import java.time.Instant;
import java.util.List;

public class HumidityService {

    private final HumidityRepository co2Repository;

    public HumidityService(HumidityRepository co2Repository) {
        this.co2Repository = co2Repository;
    }

    public List<HumidityDataPoint> getHumidityBetweenDates(String stationId, Instant start, Instant end) {
        return this.co2Repository.findBetweenDates(stationId, start, end);
    }

    public void save(HumidityDataPoint dataPoint) {
        this.co2Repository.save(dataPoint);
    }
}
