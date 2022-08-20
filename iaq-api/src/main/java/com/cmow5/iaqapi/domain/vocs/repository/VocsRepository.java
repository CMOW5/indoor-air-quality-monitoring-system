package com.cmow5.iaqapi.domain.vocs.repository;

import com.cmow5.iaqapi.domain.vocs.VocsDataPoint;

import java.time.Instant;
import java.util.List;

public interface VocsRepository {

    List<VocsDataPoint> findBetweenDates(String stationId, Instant start, Instant end, String sort);

    void save(VocsDataPoint dataPoint);
}
