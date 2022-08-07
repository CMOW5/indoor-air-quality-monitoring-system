package com.cmow5.iaqapi.domain.station;

public class StationMetadata {

    private final boolean principal;

    public StationMetadata(boolean principal) {
        this.principal = principal;
    }

    public boolean isPrincipal() {
        return principal;
    }
}
