package com.cmow5.iaqapi.infrastructure.pm10.web;

import com.cmow5.iaqapi.domain.pm10.Pm10DataPoint;
import com.cmow5.iaqapi.domain.pm10.service.Pm10Service;
import com.cmow5.iaqapi.infrastructure.general.web.dto.SensorHistoricDataResponse;
import com.cmow5.iaqapi.infrastructure.pm10.web.dto.Pm10DtoConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
public class Pm10Controller {

    private final Pm10Service pm10Service;

    private final Pm10DtoConverter converter = new Pm10DtoConverter();

    public Pm10Controller(Pm10Service pm10Service) {
        this.pm10Service = pm10Service;
    }

    @GetMapping("/station/{stationId}/metric/pm10/historic")
    public SensorHistoricDataResponse<Integer> getStationPm25BetweenDates(@PathVariable String stationId,
                                                                          @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
                                                                          @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end,
                                                                          @RequestParam(value = "sort", defaultValue = "DESC") String sort)
    {
        List<Pm10DataPoint> dataPoints = this.pm10Service.getPm10BetweenDates(stationId, start, end, sort);
        return new SensorHistoricDataResponse<>(stationId, converter.convert(dataPoints));
    }
}
