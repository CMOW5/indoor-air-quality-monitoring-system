package com.cmow5.iaqapi.infrastructure.temperature.mqtt;

import com.cmow5.iaqapi.domain.temperature.TempSensorDataPoint;
import com.cmow5.iaqapi.infrastructure.temperature.mongo.entity.TempSensorEntityBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * converts a MQTT message to the appropriate type
 */
@Service
public class MqttTempSensorMessageConverter {

    private static final Logger log = LogManager.getLogger(MqttTempSensorMessageConverter.class);

    private static final String SENSOR_ID_JSON_FIELD = "stationId";
    private static final String TEMPERATURE_JSON_FIELD = "value";
    private static final String TIMESTAMP_JSON_FIELD = "timestamp";

    private final TempSensorEntityBuilder builder = new TempSensorEntityBuilder();

    /**
     * converts a MQTT message to a TempSensorEntity. The MQTT message contains a string JSON payload with the sensor
     * data
     * @param message the message containing a JSON payload
     * @return the entity to be saved in the database
     * @throws JsonProcessingException in case there's an issue parsing the JSON message
     */
    public TempSensorDataPoint fromMessage(Message<?> message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree((String) message.getPayload());
        String stationId = jsonNode.get(SENSOR_ID_JSON_FIELD).textValue();
        int temperature = jsonNode.get(TEMPERATURE_JSON_FIELD).intValue();
        Instant timestamp = Instant.parse(jsonNode.get(TIMESTAMP_JSON_FIELD).textValue());
        return new TempSensorDataPoint(stationId, timestamp, temperature);
    }

    public Message<?> toMessage(Object payload, MessageHeaders headers) {
        return null;
    }
}
