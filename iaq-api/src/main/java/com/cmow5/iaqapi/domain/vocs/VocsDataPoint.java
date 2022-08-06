package com.cmow5.iaqapi.domain.vocs;

import com.cmow5.iaqapi.domain.SensorDataPoint;

import java.time.Instant;

public class VocsDataPoint implements SensorDataPoint<Integer> {

    private final String stationId;

    private final Instant timestamp;

    private final int vocs;

    public VocsDataPoint(String stationId, Instant timestamp, int vocs) {
        this.stationId = stationId;
        this.timestamp = timestamp;
        this.vocs = vocs;
    }

    public String getStationId() {
        return stationId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getValue() {
        return vocs;
    }

    @Override
    public String toString() {
        return "VocsDataPoint{" +
                "stationId='" + stationId + '\'' +
                ", timestamp=" + timestamp +
                ", vocs=" + vocs +
                '}';
    }
}
