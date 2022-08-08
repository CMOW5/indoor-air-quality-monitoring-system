from abc import abstractmethod

from application.domain.sensor.sender.sensor_sender import SensorSender
from application.domain.sensor.sensor import SensorData
from application.infrastructure.mqtt.config.mqtt_config import MqttConfig


class FakeMqttSender(SensorSender):
    """
     A fake implementation of the SensorSender interface which.... does nothing
     (for testing purposes)
    """

    def __init__(self, mqtt_config: MqttConfig):
        self.mqtt_config = mqtt_config

    def send(self, sensor_data: SensorData):
        print('sent sensor data via FAKE MQTT')

