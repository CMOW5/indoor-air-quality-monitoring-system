package com.cmow5.iaqapi.domain.vocs.service;


import com.cmow5.iaqapi.domain.vocs.VocsDataPoint;
import com.cmow5.iaqapi.domain.vocs.repository.VocsRepository;

import java.time.Instant;
import java.util.List;

public class VocsService {
    private final VocsRepository vocsRepository;

    public VocsService(VocsRepository tempSensorRepository) {
        this.vocsRepository = tempSensorRepository;
    }

    public List<VocsDataPoint> getVocsBetweenDates(String stationId, Instant start, Instant end) {
        return this.vocsRepository.findBetweenDates(stationId, start, end);
    }

    public void save(VocsDataPoint dataPoint) {
        this.vocsRepository.save(dataPoint);
    }
}
