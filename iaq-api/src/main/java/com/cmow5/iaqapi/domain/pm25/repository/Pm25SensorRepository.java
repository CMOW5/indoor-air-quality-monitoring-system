package com.cmow5.iaqapi.domain.pm25.repository;

import com.cmow5.iaqapi.domain.pm25.Pm25SensorDataPoint;

import java.time.Instant;
import java.util.List;

public interface Pm25SensorRepository {

    List<Pm25SensorDataPoint> findBetweenDates(String stationId, Instant start, Instant end, String sort);

    void save(Pm25SensorDataPoint dataPoint);
}
