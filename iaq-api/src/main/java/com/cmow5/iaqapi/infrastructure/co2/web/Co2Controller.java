package com.cmow5.iaqapi.infrastructure.co2.web;

import com.cmow5.iaqapi.domain.co2.Co2DataPoint;
import com.cmow5.iaqapi.domain.co2.service.Co2Service;
import com.cmow5.iaqapi.infrastructure.co2.web.dto.Co2DtoConverter;
import com.cmow5.iaqapi.infrastructure.general.web.dto.SensorHistoricDataResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
public class Co2Controller {

    private final Co2Service co2Service;

    private final Co2DtoConverter converter = new Co2DtoConverter();

    public Co2Controller(Co2Service co2Service) {
        this.co2Service = co2Service;
    }

    @GetMapping("/station/{stationId}/metric/co2/historic")
    public SensorHistoricDataResponse<Integer> getStationCo2BetweenDates(@PathVariable String stationId,
                                                                          @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
                                                                          @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end)
    {
        List<Co2DataPoint> dataPoints = this.co2Service.getCo2BetweenDates(stationId, start, end);
        return new SensorHistoricDataResponse<>(stationId, converter.convert(dataPoints));
    }
}
