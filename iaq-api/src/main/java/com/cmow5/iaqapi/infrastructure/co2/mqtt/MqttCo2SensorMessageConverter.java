package com.cmow5.iaqapi.infrastructure.co2.mqtt;

import com.cmow5.iaqapi.domain.co2.Co2DataPoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MqttCo2SensorMessageConverter {
    private static final Logger log = LogManager.getLogger(MqttCo2SensorMessageConverter.class);

    private static final String SENSOR_ID_JSON_FIELD = "stationId";
    private static final String CO2_JSON_FIELD = "value";
    private static final String TIMESTAMP_JSON_FIELD = "timestamp";

    /**
     * converts a MQTT message to a TempSensorEntity. The MQTT message contains a string JSON payload with the sensor
     * data
     * @param message the message containing a JSON payload
     * @return the entity to be saved in the database
     * @throws JsonProcessingException in case there's an issue parsing the JSON message
     */
    public Co2DataPoint fromMessage(Message<?> message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree((String) message.getPayload());
        String stationId = jsonNode.get(SENSOR_ID_JSON_FIELD).textValue();
        Instant timestamp = Instant.parse(jsonNode.get(TIMESTAMP_JSON_FIELD).textValue());
        int co2 = jsonNode.get(CO2_JSON_FIELD).intValue();
        return new Co2DataPoint(stationId, timestamp, co2);
    }
}
