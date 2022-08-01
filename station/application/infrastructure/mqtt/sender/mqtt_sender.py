import json
import paho.mqtt.client as mqtt
from abc import abstractmethod
from application.domain.sensor.sender.sensor_sender import SensorSender
from application.domain.sensor.sensor import SensorData
from application.infrastructure.mqtt.config.mqtt_config import MqttConfig


class DataMqttDto:

    def __init__(self, station_id, value, timestamp):
        self.station_id = station_id
        self.value = value
        self.timestamp = timestamp

    def to_json(self):
        return {
            'stationId': self.station_id,
            'value': self.value,
            'timestamp': self.timestamp
        }

    def to_string(self):
        return json.dumps(self.to_json())


class MqttSender(SensorSender):

    def __init__(self, mqtt_config: MqttConfig):
        self.mqtt_config = mqtt_config
        self.client = mqtt.Client()
        self.client.connect(self.mqtt_config.host, self.mqtt_config.port, self.mqtt_config.keepalive)

    def send(self, sensor_data: SensorData):
        data_point = DataMqttDto(self.mqtt_config.station_id, sensor_data.value, sensor_data.timestamp)
        print('sending via MQTT to topic = ', self.mqtt_config.topic, ' , data = ', data_point.to_string())
        result = self.client.publish(self.mqtt_config.topic, payload=data_point.to_string(), qos=0, retain=False)
        if not result.is_published():
            print('ERROR: failed to send via MQTT to topic = ', self.mqtt_config.topic,
                  ' , data = ', data_point.to_string(), ' error code is ', result.rc)
