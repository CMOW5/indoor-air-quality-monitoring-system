package com.cmow5.iaqapi.infrastructure.pm10.web.dto;

import com.cmow5.iaqapi.domain.pm10.Pm10DataPoint;
import com.cmow5.iaqapi.infrastructure.general.web.dto.converter.SensorDataPointToDtoConverter;

public class Pm10DtoConverter implements SensorDataPointToDtoConverter<Integer, Pm10DataPoint> {
}
