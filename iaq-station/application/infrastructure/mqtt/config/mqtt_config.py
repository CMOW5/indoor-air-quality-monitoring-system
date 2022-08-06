from application.infrastructure.app.config.application_config import AppConfig


class MqttConfig:
    """
      A class that holds the config values for MQTT like the mqtt endpoint, port, topics, etc.
      the values are grabbed from the general AppConfig, except the topic which is different depending on
      the sensor
    """

    def __init__(self, app_config: AppConfig, topic: str):
        self.app_config = app_config
        self.station_id = app_config.station_id
        self.host = app_config.mqtt_host
        self.port = app_config.mqtt_port
        self.keepalive = app_config.mqtt_keepalive
        self.topic = topic



