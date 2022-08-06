package com.cmow5.iaqapi.domain.pm10;

import com.cmow5.iaqapi.domain.SensorDataPoint;

import java.time.Instant;

public class Pm10DataPoint implements SensorDataPoint<Integer> {

    private final String stationId;

    private final Instant timestamp;

    private final int pm10;

    public Pm10DataPoint(String sensorId, Instant timestamp, int pm10) {
        this.stationId = sensorId;
        this.timestamp = timestamp;
        this.pm10 = pm10;
    }

    public String getStationId() {
        return stationId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getValue() {
        return pm10;
    }

    @Override
    public String toString() {
        return "Pm10DataPoint{" +
                "stationId='" + stationId + '\'' +
                ", timestamp=" + timestamp +
                ", pm10=" + pm10 +
                '}';
    }
}
