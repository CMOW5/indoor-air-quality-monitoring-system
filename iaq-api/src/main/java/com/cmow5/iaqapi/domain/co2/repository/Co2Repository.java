package com.cmow5.iaqapi.domain.co2.repository;


import com.cmow5.iaqapi.domain.co2.Co2DataPoint;

import java.time.Instant;
import java.util.List;

public interface Co2Repository {
    List<Co2DataPoint> findBetweenDates(String stationId, Instant start, Instant end, String sort);

    void save(Co2DataPoint dataPoint);
}
