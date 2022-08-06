package com.cmow5.iaqapi.infrastructure.temperature.web;

import com.cmow5.iaqapi.domain.temperature.TempSensorDataPoint;
import com.cmow5.iaqapi.domain.temperature.service.TempSensorService;
import com.cmow5.iaqapi.infrastructure.general.web.dto.SensorHistoricDataResponse;
import com.cmow5.iaqapi.infrastructure.temperature.web.dto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

import java.util.List;

@RestController
//@RequestMapping("/sensors/temperature")
public class TemperatureController {

    private static final Logger log = LogManager.getLogger(TemperatureController.class);

    private final TempSensorService tempSensorService;

    private TempSensorDtoConverter converter = new TempSensorDtoConverter();

    @Autowired
    public TemperatureController(TempSensorService tempSensorService) {
        this.tempSensorService = tempSensorService;
    }


    @GetMapping("/station/{stationId}/metric/temperature/historic")
    public SensorHistoricDataResponse<Integer> getStationTemperatureBetweenDates(@PathVariable String stationId,
                                                                                 @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
                                                                                 @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end)
    {
        List<TempSensorDataPoint> dataPoints = tempSensorService.getTemperatureBetweenDates(stationId, start, end);
        return new SensorHistoricDataResponse<>(stationId, converter.convert(dataPoints));
    }
}
