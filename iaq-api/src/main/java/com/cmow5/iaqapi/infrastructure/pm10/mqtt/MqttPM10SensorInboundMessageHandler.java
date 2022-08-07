package com.cmow5.iaqapi.infrastructure.pm10.mqtt;

import com.cmow5.iaqapi.domain.pm10.Pm10DataPoint;
import com.cmow5.iaqapi.domain.pm10.service.Pm10Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

import static com.cmow5.iaqapi.infrastructure.pm10.mqtt.MqttPM10SensorInboundMessageHandler.MQTT_PM10_SENSOR_INBOUND_HANDLER;

@Service
@Qualifier(MQTT_PM10_SENSOR_INBOUND_HANDLER)
public class MqttPM10SensorInboundMessageHandler implements MessageHandler {

    public static final String MQTT_PM10_SENSOR_INBOUND_HANDLER = "MQTT_PM10_SENSOR_INBOUND_HANDLER";

    private static final Logger log = LogManager.getLogger(MqttPM10SensorInboundMessageHandler.class);

    private final Pm10Service pm10Service;

    /**
     * the message converter to convert from a string JSON payload to an TempSensorEntity, so it can be saved
     * in mongo
     */
    private final MqttPm10SensorMessageConverter mqttPm10SensorMessageConverter;

    @Autowired
    public MqttPM10SensorInboundMessageHandler(Pm10Service pm10Service, MqttPm10SensorMessageConverter mqttPm10SensorMessageConverter) {
        this.pm10Service = pm10Service;
        this.mqttPm10SensorMessageConverter = mqttPm10SensorMessageConverter;
    }

    /**
     * saves the incoming message to mongo
     * @param message the sensor data containing a JSON payload
     */
    @Override
    public void handleMessage(Message<?> message) {
        try {
            log.info("received payload = " + message.getPayload());
            Pm10DataPoint dataPoint = mqttPm10SensorMessageConverter.fromMessage(message);
            log.info("received dataPoint = " + dataPoint);
            pm10Service.save(dataPoint);
        } catch (Exception exception) {
            log.error("failed to parse the JSON MQTT message", exception);
        }
    }
}
