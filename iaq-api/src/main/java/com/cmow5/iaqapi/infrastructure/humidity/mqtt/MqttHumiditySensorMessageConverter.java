package com.cmow5.iaqapi.infrastructure.humidity.mqtt;

import com.cmow5.iaqapi.domain.humidity.HumidityDataPoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MqttHumiditySensorMessageConverter {
    private static final Logger log = LogManager.getLogger(MqttHumiditySensorMessageConverter.class);

    private static final String SENSOR_ID_JSON_FIELD = "stationId";
    private static final String HUMIDITY_JSON_FIELD = "value";
    private static final String TIMESTAMP_JSON_FIELD = "timestamp";

    /**
     * converts a MQTT message to a TempSensorEntity. The MQTT message contains a string JSON payload with the sensor
     * data
     * @param message the message containing a JSON payload
     * @return the entity to be saved in the database
     * @throws JsonProcessingException in case there's an issue parsing the JSON message
     */
    public HumidityDataPoint fromMessage(Message<?> message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree((String) message.getPayload());
        String stationId = jsonNode.get(SENSOR_ID_JSON_FIELD).textValue();
        Instant timestamp = Instant.parse(jsonNode.get(TIMESTAMP_JSON_FIELD).textValue());
        int humidity = jsonNode.get(HUMIDITY_JSON_FIELD).intValue();
        return new HumidityDataPoint(stationId, timestamp, humidity);
    }
}
