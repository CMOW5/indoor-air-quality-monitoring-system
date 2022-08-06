package com.cmow5.iaqapi.infrastructure.vocs.mqtt;

import com.cmow5.iaqapi.domain.vocs.VocsDataPoint;
import com.cmow5.iaqapi.domain.vocs.service.VocsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

import static com.cmow5.iaqapi.infrastructure.vocs.mqtt.MqttVocsSensorInboundMessageHandler.MQTT_VOCS_SENSOR_INBOUND_HANDLER;

@Service(MQTT_VOCS_SENSOR_INBOUND_HANDLER)
public class MqttVocsSensorInboundMessageHandler implements MessageHandler {

    public static final String MQTT_VOCS_SENSOR_INBOUND_HANDLER = "MQTT_VOCS_SENSOR_INBOUND_HANDLER";

    private static final Logger log = LogManager.getLogger(MqttVocsSensorInboundMessageHandler.class);

    private final VocsService vocsService;

    /**
     * the message converter to convert from a string JSON payload to an TempSensorEntity, so it can be saved
     * in mongo
     */
    private final MqttVocsSensorMessageConverter mqttVocsSensorMessageConverter;

    @Autowired
    public MqttVocsSensorInboundMessageHandler(VocsService vocsService, MqttVocsSensorMessageConverter mqttVocsSensorMessageConverter) {
        this.vocsService = vocsService;
        this.mqttVocsSensorMessageConverter = mqttVocsSensorMessageConverter;
    }

    /**
     * saves the incoming message to mongo
     * @param message the sensor data containing a JSON payload
     */
    @Override
    public void handleMessage(Message<?> message) {
        try {
            log.info("received payload = " + message.getPayload());
            VocsDataPoint dataPoint = mqttVocsSensorMessageConverter.fromMessage(message);
            log.info("received dataPoint = " + dataPoint);
            vocsService.save(dataPoint);
        } catch (Exception exception) {
            log.error("failed to parse the JSON MQTT message", exception);
        }
    }
}
