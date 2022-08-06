package com.cmow5.iaqapi.domain.pm25;

import com.cmow5.iaqapi.domain.SensorDataPoint;

import java.time.Instant;

public class Pm25SensorDataPoint implements SensorDataPoint<Integer> {

    private final String stationId;

    private final Instant timestamp;

    private final int pm25;

    public Pm25SensorDataPoint(String sensorId, Instant timestamp, int pm25) {
        this.stationId = sensorId;
        this.timestamp = timestamp;
        this.pm25 = pm25;
    }

    public String getStationId() {
        return stationId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getValue() {
        return pm25;
    }

    @Override
    public String toString() {
        return "Pm25SensorDataPoint{" +
                "stationId='" + stationId + '\'' +
                ", timestamp=" + timestamp +
                ", pm25=" + pm25 +
                '}';
    }
}
