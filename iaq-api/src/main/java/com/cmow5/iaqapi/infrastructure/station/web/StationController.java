package com.cmow5.iaqapi.infrastructure.station.web;

import com.cmow5.iaqapi.domain.station.Station;
import com.cmow5.iaqapi.domain.station.service.StationService;
import com.cmow5.iaqapi.infrastructure.station.web.dto.StationResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/station")
    public List<Station> findAllStations() {
        return this.stationService.findAll();
    }
}
