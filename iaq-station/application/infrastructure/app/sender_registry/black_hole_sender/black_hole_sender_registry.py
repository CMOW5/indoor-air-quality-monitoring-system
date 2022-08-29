from application.infrastructure.app.config.application_config import AppConfig
from application.infrastructure.app.sender_registry.sender_registry import SenderRegistry
from application.infrastructure.mqtt.config.mqtt_config import MqttConfig
from application.infrastructure.mqtt.sender.fake.fake_mqtt_sender import FakeMqttSender


class BlackHoleSenderRegistry(SenderRegistry):
    """
      The sender registry containing the senders that are just black holding the messages (not sent anywhere)
      usage: just ask for the sender you want, and the registry should give you the provided implementation
      temp_sender = sender_registry.get_mqtt_temp_sender()
      custom_sender = sender_registry.get_sender('custom_sender')
    """
    def __init__(self, app_config: AppConfig):
        super().__init__()
        self.app_config = app_config

        temperature_mqtt_config = MqttConfig(app_config=self.app_config, topic=self.app_config.temperature_mqtt_topic)
        self.senders['temperature'] = FakeMqttSender(temperature_mqtt_config)

        humidity_mqtt_config = MqttConfig(app_config=self.app_config, topic=self.app_config.humidity_mqtt_topic)
        self.senders['humidity'] = FakeMqttSender(humidity_mqtt_config)

        pm25_mqtt_config = MqttConfig(app_config=self.app_config, topic=self.app_config.pm25_mqtt_topic)
        self.senders['pm25'] = FakeMqttSender(pm25_mqtt_config)

        pm10_mqtt_config = MqttConfig(app_config=self.app_config, topic=self.app_config.pm10_mqtt_topic)
        self.senders['pm10'] = FakeMqttSender(pm10_mqtt_config)

        co2_mqtt_config = MqttConfig(app_config=self.app_config, topic=self.app_config.co2_mqtt_topic)
        self.senders['co2'] = FakeMqttSender(co2_mqtt_config)

        vocs_mqtt_config = MqttConfig(app_config=self.app_config, topic=self.app_config.vocs_mqtt_topic)
        self.senders['vocs'] = FakeMqttSender(vocs_mqtt_config)
