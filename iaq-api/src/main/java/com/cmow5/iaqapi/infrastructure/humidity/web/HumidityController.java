package com.cmow5.iaqapi.infrastructure.humidity.web;

import com.cmow5.iaqapi.domain.humidity.HumidityDataPoint;
import com.cmow5.iaqapi.domain.humidity.service.HumidityService;
import com.cmow5.iaqapi.infrastructure.general.web.dto.SensorHistoricDataResponse;
import com.cmow5.iaqapi.infrastructure.humidity.web.dto.HumidityDtoConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
public class HumidityController {
    private final HumidityService humidityService;

    private final HumidityDtoConverter converter = new HumidityDtoConverter();

    public HumidityController(HumidityService humidityService) {
        this.humidityService = humidityService;
    }

    @GetMapping("/station/{stationId}/metric/humidity/historic")
    public SensorHistoricDataResponse<Integer> getStationHumidityBetweenDates(@PathVariable String stationId,
                                                                         @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
                                                                         @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end,
                                                                         @RequestParam(value = "sort", defaultValue = "DESC") String sort)
    {
        List<HumidityDataPoint> dataPoints = this.humidityService.getHumidityBetweenDates(stationId, start, end, sort);
        return new SensorHistoricDataResponse<>(stationId, converter.convert(dataPoints));
    }
}
