package com.cmow5.iaqapi.domain.temperature;

import com.cmow5.iaqapi.domain.SensorDataPoint;

import java.time.Instant;

public class TempSensorDataPoint implements SensorDataPoint<Integer> {

    private final String stationId;

    private final Instant timestamp;

    private final int temperature;

    public TempSensorDataPoint(String stationId, Instant timestamp, int temperature) {
        this.stationId = stationId;
        this.timestamp = timestamp;
        this.temperature = temperature;
    }

    public String getStationId() {
        return stationId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getValue() {
        return temperature;
    }

    @Override
    public String toString() {
        return "TempSensorDataPoint{" +
                "stationId='" + stationId + '\'' +
                ", timestamp=" + timestamp +
                ", temperature=" + temperature +
                '}';
    }
}
