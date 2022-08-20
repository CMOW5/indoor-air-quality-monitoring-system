package com.cmow5.iaqapi.infrastructure.station.awsparamstore.repository;

import com.cmow5.iaqapi.domain.station.Station;
import com.cmow5.iaqapi.domain.station.StationMetadata;
import com.cmow5.iaqapi.domain.station.repository.StationRepository;
import com.cmow5.iaqapi.infrastructure.configs.mqtt.repository.awsparamstore.MqttConfigsAwsParamStoreRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

import java.util.ArrayList;
import java.util.List;

import static com.cmow5.iaqapi.infrastructure.general.database.awstimestream.AwsTimestreamConfig.AWS_TIMESTREAM_PROFILE;

@Repository
@Profile(AWS_TIMESTREAM_PROFILE)
public class StationAwsParamStoreRepository implements StationRepository {
    private static final Logger log = LogManager.getLogger(MqttConfigsAwsParamStoreRepository.class);

    private static final String ID_JSON_KEY = "id";
    private static final String NAME_JSON_KEY = "name";
    private static final String METADATA_JSON_KEY = "metadata";
    private static final String METADATA_PRINCIPAL_JSON_KEY = "principal";

    private final SsmClient ssmClient;
    private final String stationsKey;

    @Autowired
    public StationAwsParamStoreRepository(SsmClient ssmClient, @Value("${stations.paramstore.key}") String stationsKey) {
        this.ssmClient = ssmClient;
        this.stationsKey = stationsKey;
    }

    @Override
    public List<Station> findAll() {
        log.info("getting stations from param store");

        GetParameterRequest parameterRequest = GetParameterRequest.builder()
                .name(this.stationsKey)
                .withDecryption(true)
                .build();

        GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);

        List<Station> stations = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(parameterResponse.parameter().value());
            for (final JsonNode node : jsonNode) {
                String id = node.get(ID_JSON_KEY).textValue();
                String name = node.get(NAME_JSON_KEY).textValue();
                boolean principal = node.get(METADATA_JSON_KEY).get(METADATA_PRINCIPAL_JSON_KEY).asBoolean();
                Station station = new Station(id, name, new StationMetadata(principal));
                stations.add(station);
            }

        } catch (JsonProcessingException e) {
            log.error("error getting the stations from parameter store", e);
            throw new RuntimeException(e);
        }

        return stations;
    }
}