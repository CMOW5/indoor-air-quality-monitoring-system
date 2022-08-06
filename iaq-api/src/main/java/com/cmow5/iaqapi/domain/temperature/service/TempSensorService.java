package com.cmow5.iaqapi.domain.temperature.service;

import com.cmow5.iaqapi.domain.temperature.repository.TempSensorRepository;
import com.cmow5.iaqapi.domain.temperature.TempSensorDataPoint;


import java.time.Instant;
import java.util.List;

public class TempSensorService {

    private final TempSensorRepository tempSensorRepository;

    public TempSensorService(TempSensorRepository tempSensorRepository) {
        this.tempSensorRepository = tempSensorRepository;
    }

    public List<TempSensorDataPoint> getTemperatureBetweenDates(String stationId, Instant start, Instant end) {
        return this.tempSensorRepository.findBetweenDates(stationId, start, end);
    }

    public TempSensorDataPoint save(TempSensorDataPoint dataPoint) {
        return this.tempSensorRepository.save(dataPoint);
    }
}
