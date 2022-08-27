package com.cmow5.iaqapi.infrastructure.temperature.web.dto;

import com.cmow5.iaqapi.domain.temperature.TempSensorDataPoint;
import com.cmow5.iaqapi.infrastructure.general.web.dto.converter.SensorDataPointToDtoConverter;

public class TempSensorDtoConverter implements SensorDataPointToDtoConverter<Integer, TempSensorDataPoint> {
}
