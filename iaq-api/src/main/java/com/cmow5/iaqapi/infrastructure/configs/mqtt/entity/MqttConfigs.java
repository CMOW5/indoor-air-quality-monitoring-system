package com.cmow5.iaqapi.infrastructure.configs.mqtt.entity;

public class MqttConfigs {

    private String endpoint;

    private String awsRegion;

    private String identityPoolId;

    public MqttConfigs() {

    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAwsRegion() {
        return awsRegion;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    public String getIdentityPoolId() {
        return identityPoolId;
    }

    public void setIdentityPoolId(String identityPoolId) {
        this.identityPoolId = identityPoolId;
    }
}
