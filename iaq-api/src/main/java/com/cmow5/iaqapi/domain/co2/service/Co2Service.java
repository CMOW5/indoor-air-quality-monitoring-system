package com.cmow5.iaqapi.domain.co2.service;


import com.cmow5.iaqapi.domain.co2.Co2DataPoint;
import com.cmow5.iaqapi.domain.co2.repository.Co2Repository;

import java.time.Instant;
import java.util.List;

public class Co2Service {

    private final Co2Repository co2Repository;

    public Co2Service(Co2Repository co2Repository) {
        this.co2Repository = co2Repository;
    }

    public List<Co2DataPoint> getCo2BetweenDates(String stationId, Instant start, Instant end, String sort) {
        return this.co2Repository.findBetweenDates(stationId, start, end, sort);
    }

    public void save(Co2DataPoint dataPoint) {
        this.co2Repository.save(dataPoint);
    }
}
