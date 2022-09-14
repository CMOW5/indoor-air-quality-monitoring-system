import logging
import paho.mqtt.client as mqtt
from application.domain.sensor.sender.sensor_sender import SensorSender
from application.domain.sensor.sensor import SensorData
from application.infrastructure.mqtt.config.mqtt_config import MqttConfig
from application.infrastructure.mqtt.sender.mqtt_data_dto import MqttDataDto


class MqttSender(SensorSender):

    def __init__(self, mqtt_config: MqttConfig):
        self.mqtt_config = mqtt_config
        self.client = mqtt.Client()
        self.client.connect(self.mqtt_config.host, self.mqtt_config.port, self.mqtt_config.keepalive)

    def send(self, sensor_data: SensorData):
        data_point = MqttDataDto(self.mqtt_config.station_id, sensor_data.value, sensor_data.timestamp)
        logging.info('sending via MQTT to topic = %s , data = %s ', self.mqtt_config.topic, data_point.to_string())
        result = self.client.publish(self.mqtt_config.topic, payload=data_point.to_string(), qos=0, retain=False)
        if not result.is_published():
            logging.error('ERROR: failed to send via MQTT to topic = %s , data = %s , error_code = %s',
                          self.mqtt_config.topic, data_point.to_string(), result.rc)
