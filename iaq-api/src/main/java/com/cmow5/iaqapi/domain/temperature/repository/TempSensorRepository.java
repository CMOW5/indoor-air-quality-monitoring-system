package com.cmow5.iaqapi.domain.temperature.repository;

import com.cmow5.iaqapi.domain.temperature.TempSensorDataPoint;

import java.time.Instant;
import java.util.List;

public interface TempSensorRepository {

    List<TempSensorDataPoint> findBetweenDates(String stationId, Instant start, Instant end);

    TempSensorDataPoint save(TempSensorDataPoint dataPoint);
}
