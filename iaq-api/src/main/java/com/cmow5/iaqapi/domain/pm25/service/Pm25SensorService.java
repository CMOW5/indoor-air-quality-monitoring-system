package com.cmow5.iaqapi.domain.pm25.service;

import com.cmow5.iaqapi.domain.pm25.repository.Pm25SensorRepository;
import com.cmow5.iaqapi.domain.pm25.Pm25SensorDataPoint;

import java.time.Instant;
import java.util.List;

public class Pm25SensorService {

    private final Pm25SensorRepository pm25SensorRepository;

    public Pm25SensorService(Pm25SensorRepository pm25SensorRepository) {
        this.pm25SensorRepository = pm25SensorRepository;
    }

    public List<Pm25SensorDataPoint> getPm25BetweenDates(String stationId, Instant start, Instant end, String sort) {
        return this.pm25SensorRepository.findBetweenDates(stationId, start, end, sort);
    }

    public void save(Pm25SensorDataPoint dataPoint) {
        this.pm25SensorRepository.save(dataPoint);
    }
}
