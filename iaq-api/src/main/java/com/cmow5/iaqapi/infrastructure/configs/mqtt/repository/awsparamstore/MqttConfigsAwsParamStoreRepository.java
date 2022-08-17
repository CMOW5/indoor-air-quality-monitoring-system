package com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.awsparamstore;

import com.cmow5.iaqapi.infrastructure.configs.mqtt.entity.MqttConfigs;
import com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.MqttConfigsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

import static com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.awsparamstore.client.ParameterStoreClientBeans.PARAM_STORE_PROFILE;

@Repository
@Profile(PARAM_STORE_PROFILE)
public class MqttConfigsAwsParamStoreRepository implements MqttConfigsRepository {

    private static final Logger log = LogManager.getLogger(MqttConfigsAwsParamStoreRepository.class);

    private static final String ENDPOINT_JSON_KEY = "endpoint";
    private static final String AWS_REGION_JSON_KEY = "awsRegion";
    private static final String IDENTITY_POOL_ID_JSON_KEY = "identityPoolId";

    private final SsmClient ssmClient;
    private final String mqttConfigsKey;

    public MqttConfigsAwsParamStoreRepository(SsmClient ssmClient, @Value("${mqtt.configs.key}") String mqttConfigsKey) {
        this.ssmClient = ssmClient;
        this.mqttConfigsKey = mqttConfigsKey;
    }

    @Override
    public MqttConfigs findMqttConfigs() {
        GetParameterRequest parameterRequest = GetParameterRequest.builder()
                .name(this.mqttConfigsKey)
                .withDecryption(true)
                .build();

        GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = null;
            jsonNode = mapper.readTree(parameterResponse.parameter().value());
            String endpoint = jsonNode.get(ENDPOINT_JSON_KEY).textValue();
            String awsRegion = jsonNode.get(AWS_REGION_JSON_KEY).textValue();
            String identityPoolId = jsonNode.get(IDENTITY_POOL_ID_JSON_KEY).textValue();
            return new MqttConfigs(endpoint, awsRegion, identityPoolId);
        } catch (JsonProcessingException e) {
            log.error("error getting the mqtt configs from parameter store", e);
            throw new RuntimeException(e);
        }
    }
}
