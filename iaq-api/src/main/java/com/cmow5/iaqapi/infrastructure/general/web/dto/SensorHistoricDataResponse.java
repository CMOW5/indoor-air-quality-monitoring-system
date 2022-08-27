package com.cmow5.iaqapi.infrastructure.general.web.dto;


import java.util.List;

public class SensorHistoricDataResponse<T> {
    private String stationId;

    private List<SensorDataPointDto<T>> data;

    public SensorHistoricDataResponse() {
    }

    public SensorHistoricDataResponse(String stationId, List<SensorDataPointDto<T>> data) {
        this.stationId = stationId;
        this.data = data;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public List<SensorDataPointDto<T>> getData() {
        return data;
    }

    public void setData(List<SensorDataPointDto<T>> data) {
        this.data = data;
    }
}
