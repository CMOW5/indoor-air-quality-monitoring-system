package com.cmow5.iaqapi.domain.station.service;

import com.cmow5.iaqapi.domain.station.Station;
import com.cmow5.iaqapi.domain.station.repository.StationRepository;

import java.util.List;

public class StationService {

    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public List<Station> findAll() {
        return this.stationRepository.findAll();
    }
}
