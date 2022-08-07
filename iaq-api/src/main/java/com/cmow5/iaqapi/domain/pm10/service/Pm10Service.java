package com.cmow5.iaqapi.domain.pm10.service;


import com.cmow5.iaqapi.domain.pm10.Pm10DataPoint;
import com.cmow5.iaqapi.domain.pm10.repository.Pm10Repository;

import java.time.Instant;
import java.util.List;

public class Pm10Service {
    private final Pm10Repository pm10SensorRepository;

    public Pm10Service(Pm10Repository pm10SensorRepository) {
        this.pm10SensorRepository = pm10SensorRepository;
    }

    public List<Pm10DataPoint> getPm10BetweenDates(String stationId, Instant start, Instant end) {
        return this.pm10SensorRepository.findBetweenDates(stationId, start, end);
    }

    public void save(Pm10DataPoint dataPoint) {
        this.pm10SensorRepository.save(dataPoint);
    }
}
