package com.cmow5.iaqapi.infrastructure.pm25.mqtt;

import com.cmow5.iaqapi.domain.pm25.Pm25SensorDataPoint;
import com.cmow5.iaqapi.domain.pm25.service.Pm25SensorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

import static com.cmow5.iaqapi.infrastructure.pm25.mqtt.MqttPM25SensorInboundMessageHandler.MQTT_PM25_SENSOR_INBOUND_HANDLER;


/**
 * Handles the incoming messages from topic /sensor/temperature
 * The incoming message in saved to MONGO, so it can be queried for historic data
 */
@Service
@Qualifier(MQTT_PM25_SENSOR_INBOUND_HANDLER)
public class MqttPM25SensorInboundMessageHandler implements MessageHandler {

    public static final String MQTT_PM25_SENSOR_INBOUND_HANDLER = "MQTT_PM25_SENSOR_INBOUND_HANDLER";

    private static final Logger log = LogManager.getLogger(MqttPM25SensorInboundMessageHandler.class);

    private final Pm25SensorService pm25SensorService;

    /**
     * the message converter to convert from a string JSON payload to an TempSensorEntity, so it can be saved
     * in mongo
     */
    private final MqttPm25SensorMessageConverter mqttPm25SensorMessageConverter;

    @Autowired
    public MqttPM25SensorInboundMessageHandler(Pm25SensorService pm25SensorService, MqttPm25SensorMessageConverter mqttPm25SensorMessageConverter) {
        this.pm25SensorService = pm25SensorService;
        this.mqttPm25SensorMessageConverter = mqttPm25SensorMessageConverter;
    }

    /**
     * saves the incoming message to mongo
     * @param message the sensor data containing a JSON payload
     */
    @Override
    public void handleMessage(Message<?> message) {
        try {
            log.info("received payload = " + message.getPayload());
            Pm25SensorDataPoint dataPoint = mqttPm25SensorMessageConverter.fromMessage(message);
            log.info("received dataPoint = " + dataPoint);
            pm25SensorService.save(dataPoint);
        } catch (Exception exception) {
            log.error("failed to parse the JSON MQTT message", exception);
        }
    }
}
