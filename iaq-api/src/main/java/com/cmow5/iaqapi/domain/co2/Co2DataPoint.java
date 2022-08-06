package com.cmow5.iaqapi.domain.co2;

import com.cmow5.iaqapi.domain.SensorDataPoint;

import java.time.Instant;

public class Co2DataPoint implements SensorDataPoint<Integer> {

    private final String stationId;

    private final Instant timestamp;

    private final int co2;

    public Co2DataPoint(String stationId, Instant timestamp, int co2) {
        this.stationId = stationId;
        this.timestamp = timestamp;
        this.co2 = co2;
    }

    public String getStationId() {
        return stationId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getValue() {
        return co2;
    }

    @Override
    public String toString() {
        return "CO2DataPoint{" +
                "stationId='" + stationId + '\'' +
                ", timestamp=" + timestamp +
                ", co2=" + co2 +
                '}';
    }
}
