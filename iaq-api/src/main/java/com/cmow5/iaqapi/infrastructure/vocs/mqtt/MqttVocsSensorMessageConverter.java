package com.cmow5.iaqapi.infrastructure.vocs.mqtt;

import com.cmow5.iaqapi.domain.vocs.VocsDataPoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MqttVocsSensorMessageConverter {
    private static final Logger log = LogManager.getLogger(MqttVocsSensorMessageConverter.class);

    private static final String SENSOR_ID_JSON_FIELD = "stationId";
    private static final String VOCS_JSON_FIELD = "value";
    private static final String TIMESTAMP_JSON_FIELD = "timestamp";

    /**
     * converts a MQTT message to a TempSensorEntity. The MQTT message contains a string JSON payload with the sensor
     * data
     * @param message the message containing a JSON payload
     * @return the entity to be saved in the database
     * @throws JsonProcessingException in case there's an issue parsing the JSON message
     */
    public VocsDataPoint fromMessage(Message<?> message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree((String) message.getPayload());
        String stationId = jsonNode.get(SENSOR_ID_JSON_FIELD).textValue();
        Instant timestamp = Instant.parse(jsonNode.get(TIMESTAMP_JSON_FIELD).textValue());
        int vocs = jsonNode.get(VOCS_JSON_FIELD).intValue();
        return new VocsDataPoint(stationId, timestamp, vocs);
    }

    public Message<?> toMessage(Object payload, MessageHeaders headers) {
        return null;
    }
}
