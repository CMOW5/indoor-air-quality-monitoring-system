package com.cmow5.iaqapi.infrastructure.humidity.mqtt;

import com.cmow5.iaqapi.domain.humidity.HumidityDataPoint;
import com.cmow5.iaqapi.domain.humidity.service.HumidityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

import static com.cmow5.iaqapi.infrastructure.humidity.mqtt.MqttHumiditySensorInboundMessageHandler.MQTT_HUMIDITY_SENSOR_INBOUND_HANDLER;

@Service(MQTT_HUMIDITY_SENSOR_INBOUND_HANDLER)
public class MqttHumiditySensorInboundMessageHandler implements MessageHandler {
    public static final String MQTT_HUMIDITY_SENSOR_INBOUND_HANDLER = "MQTT_HUMIDITY_SENSOR_INBOUND_HANDLER";

    private static final Logger log = LogManager.getLogger(MqttHumiditySensorInboundMessageHandler.class);

    private final HumidityService humidityService;

    /**
     * the message converter to convert from a string JSON payload to an TempSensorEntity, so it can be saved
     * in mongo
     */
    private final MqttHumiditySensorMessageConverter mqttHumiditySensorMessageConverter;

    @Autowired
    public MqttHumiditySensorInboundMessageHandler(HumidityService humidityService, MqttHumiditySensorMessageConverter mqttHumiditySensorMessageConverter) {
        this.humidityService = humidityService;
        this.mqttHumiditySensorMessageConverter = mqttHumiditySensorMessageConverter;
    }

    /**
     * saves the incoming message to mongo
     * @param message the sensor data containing a JSON payload
     */
    @Override
    public void handleMessage(Message<?> message) {
        try {
            log.info("received payload = " + message.getPayload());
            HumidityDataPoint dataPoint = mqttHumiditySensorMessageConverter.fromMessage(message);
            log.info("received dataPoint = " + dataPoint);
            humidityService.save(dataPoint);
        } catch (Exception exception) {
            log.error("failed to parse the JSON MQTT message", exception);
        }
    }
}
