package com.cmow5.iaqapi.domain.pm10.repository;

import com.cmow5.iaqapi.domain.pm10.Pm10DataPoint;

import java.time.Instant;
import java.util.List;

public interface Pm10Repository {

    List<Pm10DataPoint> findBetweenDates(String stationId, Instant start, Instant end);

    void save(Pm10DataPoint dataPoint);
}
