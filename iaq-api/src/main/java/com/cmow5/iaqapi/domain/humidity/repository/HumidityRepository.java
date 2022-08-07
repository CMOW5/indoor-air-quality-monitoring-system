package com.cmow5.iaqapi.domain.humidity.repository;


import com.cmow5.iaqapi.domain.humidity.HumidityDataPoint;

import java.time.Instant;
import java.util.List;

public interface HumidityRepository {
    List<HumidityDataPoint> findBetweenDates(String stationId, Instant start, Instant end);

    void save(HumidityDataPoint dataPoint);
}
