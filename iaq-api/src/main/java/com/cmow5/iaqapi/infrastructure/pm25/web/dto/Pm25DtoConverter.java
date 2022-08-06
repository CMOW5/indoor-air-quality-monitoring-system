package com.cmow5.iaqapi.infrastructure.pm25.web.dto;

import com.cmow5.iaqapi.domain.pm25.Pm25SensorDataPoint;
import com.cmow5.iaqapi.infrastructure.general.web.dto.converter.SensorDataPointToDtoConverter;

public class Pm25DtoConverter implements SensorDataPointToDtoConverter<Integer, Pm25SensorDataPoint> {
}
