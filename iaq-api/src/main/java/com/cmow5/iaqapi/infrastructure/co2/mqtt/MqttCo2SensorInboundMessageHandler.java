package com.cmow5.iaqapi.infrastructure.co2.mqtt;

import com.cmow5.iaqapi.domain.co2.Co2DataPoint;
import com.cmow5.iaqapi.domain.co2.service.Co2Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

import static com.cmow5.iaqapi.infrastructure.co2.mqtt.MqttCo2SensorInboundMessageHandler.MQTT_CO2_SENSOR_INBOUND_HANDLER;

@Service(MQTT_CO2_SENSOR_INBOUND_HANDLER)
public class MqttCo2SensorInboundMessageHandler implements MessageHandler {
    public static final String MQTT_CO2_SENSOR_INBOUND_HANDLER = "MQTT_CO2_SENSOR_INBOUND_HANDLER";

    private static final Logger log = LogManager.getLogger(MqttCo2SensorInboundMessageHandler.class);

    private final Co2Service co2Service;

    /**
     * the message converter to convert from a string JSON payload to an TempSensorEntity, so it can be saved
     * in mongo
     */
    private final MqttCo2SensorMessageConverter mqttCo2SensorMessageConverter;

    @Autowired
    public MqttCo2SensorInboundMessageHandler(Co2Service co2Service, MqttCo2SensorMessageConverter mqttCo2SensorMessageConverter) {
        this.co2Service = co2Service;
        this.mqttCo2SensorMessageConverter = mqttCo2SensorMessageConverter;
    }

    /**
     * saves the incoming message to mongo
     * @param message the sensor data containing a JSON payload
     */
    @Override
    public void handleMessage(Message<?> message) {
        try {
            log.info("received payload = " + message.getPayload());
            Co2DataPoint dataPoint = mqttCo2SensorMessageConverter.fromMessage(message);
            log.info("received dataPoint = " + dataPoint);
            co2Service.save(dataPoint);
        } catch (Exception exception) {
            log.error("failed to parse the JSON MQTT message", exception);
        }
    }
}
