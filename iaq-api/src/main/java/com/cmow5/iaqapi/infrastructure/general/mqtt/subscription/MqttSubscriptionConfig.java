package com.cmow5.iaqapi.infrastructure.general.mqtt.subscription;

import com.cmow5.iaqapi.infrastructure.co2.mqtt.MqttCo2SensorInboundMessageHandler;
import com.cmow5.iaqapi.infrastructure.humidity.mqtt.MqttHumiditySensorInboundMessageHandler;
import com.cmow5.iaqapi.infrastructure.pm10.mqtt.MqttPM10SensorInboundMessageHandler;
import com.cmow5.iaqapi.infrastructure.pm25.mqtt.MqttPM25SensorInboundMessageHandler;
import com.cmow5.iaqapi.infrastructure.temperature.mqtt.MqttTempSensorInboundMessageHandler;
import com.cmow5.iaqapi.infrastructure.vocs.mqtt.MqttVocsSensorInboundMessageHandler;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

/**
 * Configuration Beans to create an MQTT subscription for topic sensor/temperature
 */
@Configuration
@Profile("LOCAL_MQTT_ENABLED")
public class MqttSubscriptionConfig {

    public static final String MQTT_URL = "tcp://localhost:1884";

    public static final String INBOUND_CLIENT_ID = "spring-boot-server-inbound";

    public static final String INBOUND_SUBSCRIPTION_TEMPERATURE_TOPIC = "sensor/temperature";

    public static final String INBOUND_SUBSCRIPTION_HUMIDITY_TOPIC = "sensor/humidity";

    public static final String INBOUND_SUBSCRIPTION_PM25_TOPIC = "sensor/pm25";

    public static final String INBOUND_SUBSCRIPTION_PM10_TOPIC = "sensor/pm10";

    public static final String INBOUND_SUBSCRIPTION_VOCS_TOPIC = "sensor/vocs";

    public static final String INBOUND_SUBSCRIPTION_CO2_TOPIC = "sensor/co2";

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DefaultMqttPahoClientFactory pahoClientFactory() {
        DefaultMqttPahoClientFactory pahoClientFactory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions connectionOptions = new MqttConnectOptions();
        connectionOptions.setServerURIs(new String[] { MQTT_URL });
        pahoClientFactory.setConnectionOptions(connectionOptions);
        return pahoClientFactory;
    }

    @Bean
    public IntegrationFlow mqttInFlow(DefaultMqttPahoClientFactory pahoClientFactory) {
        return IntegrationFlows.from(
                        new MqttPahoMessageDrivenChannelAdapter(INBOUND_CLIENT_ID,
                                pahoClientFactory, INBOUND_SUBSCRIPTION_TEMPERATURE_TOPIC, INBOUND_SUBSCRIPTION_HUMIDITY_TOPIC,
                                                   INBOUND_SUBSCRIPTION_PM25_TOPIC, INBOUND_SUBSCRIPTION_PM10_TOPIC,
                                                   INBOUND_SUBSCRIPTION_VOCS_TOPIC, INBOUND_SUBSCRIPTION_CO2_TOPIC))
                .route("headers['" + MqttHeaders.RECEIVED_TOPIC + "']")
                .get();
    }

    @Bean
    public IntegrationFlow temperature_inbound_message_handler(MqttTempSensorInboundMessageHandler handler) {
        return IntegrationFlows.from(INBOUND_SUBSCRIPTION_TEMPERATURE_TOPIC)
                .handle((payload, headers) -> {
                    handler.handleMessage(new GenericMessage(payload, headers));
                    return null;
                })
                .get();
    }

    @Bean
    public IntegrationFlow humidity_inbound_message_handler(MqttHumiditySensorInboundMessageHandler handler) {
        return IntegrationFlows.from(INBOUND_SUBSCRIPTION_HUMIDITY_TOPIC)
                .handle((payload, headers) -> {
                    handler.handleMessage(new GenericMessage(payload, headers));
                    return null;
                })
                .get();
    }

    @Bean
    public IntegrationFlow pm25_inbound_message_handler(MqttPM25SensorInboundMessageHandler handler) {
        return IntegrationFlows.from(INBOUND_SUBSCRIPTION_PM25_TOPIC)
                .handle((payload, headers) -> {
                    handler.handleMessage(new GenericMessage(payload, headers));
                    return null;
                })
                .get();
    }

    @Bean
    public IntegrationFlow pm10_inbound_message_handler(MqttPM10SensorInboundMessageHandler handler) {
        return IntegrationFlows.from(INBOUND_SUBSCRIPTION_PM10_TOPIC)
                .handle((payload, headers) -> {
                    handler.handleMessage(new GenericMessage(payload, headers));
                    return null;
                })
                .get();
    }

    @Bean
    public IntegrationFlow vocs_inbound_message_handler(MqttVocsSensorInboundMessageHandler handler) {
        return IntegrationFlows.from(INBOUND_SUBSCRIPTION_VOCS_TOPIC)
                .handle((payload, headers) -> {
                    handler.handleMessage(new GenericMessage(payload, headers));
                    return null;
                })
                .get();
    }

    @Bean
    public IntegrationFlow co2_inbound_message_handler(MqttCo2SensorInboundMessageHandler handler) {
        return IntegrationFlows.from(INBOUND_SUBSCRIPTION_CO2_TOPIC)
                .handle((payload, headers) -> {
                    handler.handleMessage(new GenericMessage(payload, headers));
                    return null;
                })
                .get();
    }



    /*
    @Bean
    public MessageProducer inbound(DefaultMqttPahoClientFactory pahoClientFactory) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(MQTT_URL, INBOUND_CLIENT_ID, pahoClientFactory, INBOUND_SUBSCRIPTION_TEMPERATURE_TOPIC, INBOUND_SUBSCRIPTION_PM25_TOPIC);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler(@Qualifier(MQTT_TEMP_SENSOR_INBOUND_HANDLER) MessageHandler tempSensorInboundHandler) {
        return tempSensorInboundHandler;
    }
     */
}
