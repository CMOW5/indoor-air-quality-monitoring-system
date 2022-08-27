from abc import abstractmethod

from application.domain.sensor.sender.sensor_sender import SensorSender
from application.domain.sensor.sensor import SensorData
from application.infrastructure.mqtt.config.mqtt_config import MqttConfig
from application.infrastructure.mqtt.sender.mqtt_data_dto import MqttDataDto


class FakeMqttSender(SensorSender):
    """
     A fake implementation of the SensorSender interface which.... does nothing
     (for testing purposes)
    """

    def __init__(self, mqtt_config: MqttConfig):
        self.mqtt_config = mqtt_config

    def send(self, sensor_data: SensorData):
        data_point = MqttDataDto(self.mqtt_config.station_id, sensor_data.value, sensor_data.timestamp)
        print('sending via FAKE MQTT to topic = ', self.mqtt_config.topic, ' , data = ', data_point.to_string())

