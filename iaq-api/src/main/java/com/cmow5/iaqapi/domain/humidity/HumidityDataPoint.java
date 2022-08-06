package com.cmow5.iaqapi.domain.humidity;

import com.cmow5.iaqapi.domain.SensorDataPoint;

import java.time.Instant;

public class HumidityDataPoint implements SensorDataPoint<Integer> {
    private final String stationId;

    private final Instant timestamp;

    private final int humidity;

    public HumidityDataPoint(String stationId, Instant timestamp, int humidity) {
        this.stationId = stationId;
        this.timestamp = timestamp;
        this.humidity = humidity;
    }

    public String getStationId() {
        return stationId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getValue() {
        return humidity;
    }

    @Override
    public String toString() {
        return "HumidityDataPoint{" +
                "stationId='" + stationId + '\'' +
                ", timestamp=" + timestamp +
                ", humidity=" + humidity +
                '}';
    }
}
