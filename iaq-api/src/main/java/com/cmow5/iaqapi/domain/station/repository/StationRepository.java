package com.cmow5.iaqapi.domain.station.repository;

import com.cmow5.iaqapi.domain.station.Station;

import java.util.List;

public interface StationRepository {

    List<Station> findAll();
}
