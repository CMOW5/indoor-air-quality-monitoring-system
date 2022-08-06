package com.cmow5.iaqapi.infrastructure.general.web.dto.converter;

import com.cmow5.iaqapi.domain.SensorDataPoint;
import com.cmow5.iaqapi.infrastructure.general.web.dto.SensorDataPointDto;

import java.util.List;
import java.util.stream.Collectors;

public interface SensorDataPointToDtoConverter<T, K extends SensorDataPoint<T>> {

    default SensorDataPointDto<T> convert(K datapoint) {
        return new SensorDataPointDto<>(datapoint.getTimestamp(), datapoint.getValue());
    }

    default List<SensorDataPointDto<T>> convert(List<K> dataPoints) {
        return dataPoints.stream().map(this::convert).collect(Collectors.toList());
    }
}
