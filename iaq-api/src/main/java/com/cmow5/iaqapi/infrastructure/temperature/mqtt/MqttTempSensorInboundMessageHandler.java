package com.cmow5.iaqapi.infrastructure.temperature.mqtt;

import com.cmow5.iaqapi.domain.temperature.TempSensorDataPoint;
import com.cmow5.iaqapi.domain.temperature.service.TempSensorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

import static com.cmow5.iaqapi.infrastructure.temperature.mqtt.MqttTempSensorInboundMessageHandler.MQTT_TEMP_SENSOR_INBOUND_HANDLER;

/**
 * Handles the incoming messages from topic /sensor/temperature
 * The incoming message in saved to MONGO, so it can be queried for historic data
 */
@Service
@Qualifier(MQTT_TEMP_SENSOR_INBOUND_HANDLER)
public class MqttTempSensorInboundMessageHandler implements MessageHandler {

    public static final String MQTT_TEMP_SENSOR_INBOUND_HANDLER = "MQTT_TEMP_SENSOR_INBOUND_HANDLER";

    private static final Logger log = LogManager.getLogger(MqttTempSensorInboundMessageHandler.class);

    private final TempSensorService tempSensorService;

    /**
     * the message converter to convert from a string JSON payload to an TempSensorEntity, so it can be saved
     * in mongo
     */
    private final MqttTempSensorMessageConverter mqttTempSensorMessageConverter;

    public MqttTempSensorInboundMessageHandler(TempSensorService tempSensorService, MqttTempSensorMessageConverter mqttTempSensorMessageConverter) {
        this.tempSensorService = tempSensorService;
        this.mqttTempSensorMessageConverter = mqttTempSensorMessageConverter;
    }

    /**
     * saves the incoming message to mongo
     * @param message the sensor data containing a JSON payload
     */
    @Override
    public void handleMessage(Message<?> message) {
        try {
            log.info("received payload = " + message.getPayload());
            TempSensorDataPoint dataPoint = mqttTempSensorMessageConverter.fromMessage(message);
            log.info("received dataPoint = " + dataPoint);
            tempSensorService.save(dataPoint);
        } catch (Exception exception) {
            log.error("failed to handle the MQTT message, exception is = ", exception);
        }
    }
}
