package com.cmow5.iaqapi.infrastructure.humidity.web.dto;

import com.cmow5.iaqapi.domain.humidity.HumidityDataPoint;
import com.cmow5.iaqapi.infrastructure.general.web.dto.converter.SensorDataPointToDtoConverter;

public class HumidityDtoConverter implements SensorDataPointToDtoConverter<Integer, HumidityDataPoint> {
}
