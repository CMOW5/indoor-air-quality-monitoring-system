package com.cmow5.iaqapi.infrastructure.vocs.web;

import com.cmow5.iaqapi.domain.vocs.VocsDataPoint;
import com.cmow5.iaqapi.domain.vocs.service.VocsService;
import com.cmow5.iaqapi.infrastructure.general.web.dto.SensorHistoricDataResponse;
import com.cmow5.iaqapi.infrastructure.vocs.web.dto.VocsDtoConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
public class VocsController {

    private final VocsService vocsService;

    private final VocsDtoConverter converter = new VocsDtoConverter();

    public VocsController(VocsService vocsService) {
        this.vocsService = vocsService;
    }

    @GetMapping("/station/{stationId}/metric/vocs/historic")
    public SensorHistoricDataResponse<Integer> getStationVocsBetweenDates(@PathVariable String stationId,
                                                                          @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
                                                                          @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end)
    {
        List<VocsDataPoint> dataPoints = this.vocsService.getVocsBetweenDates(stationId, start, end);
        return new SensorHistoricDataResponse<>(stationId, converter.convert(dataPoints));
    }
}
