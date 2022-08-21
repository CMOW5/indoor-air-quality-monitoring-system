package com.cmow5.iaqapi.infrastructure.pm25.web;

import com.cmow5.iaqapi.domain.pm25.Pm25SensorDataPoint;
import com.cmow5.iaqapi.domain.pm25.service.Pm25SensorService;
import com.cmow5.iaqapi.infrastructure.pm25.web.dto.Pm25DtoConverter;
import com.cmow5.iaqapi.infrastructure.general.web.dto.SensorHistoricDataResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
public class Pm25Controller {

    private final Pm25SensorService pm25SensorService;

    private final Pm25DtoConverter converter = new Pm25DtoConverter();

    public Pm25Controller(Pm25SensorService pm25SensorService) {
        this.pm25SensorService = pm25SensorService;
    }

    @GetMapping("/station/{stationId}/metric/pm25/historic")
    public SensorHistoricDataResponse<Integer> getStationPm25BetweenDates(@PathVariable String stationId,
                                                                          @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
                                                                          @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end,
                                                                          @RequestParam(value = "sort", defaultValue = "DESC") String sort)
    {
        List<Pm25SensorDataPoint> dataPoints = this.pm25SensorService.getPm25BetweenDates(stationId, start, end, sort);
        return new SensorHistoricDataResponse<>(stationId, converter.convert(dataPoints));
    }
}
