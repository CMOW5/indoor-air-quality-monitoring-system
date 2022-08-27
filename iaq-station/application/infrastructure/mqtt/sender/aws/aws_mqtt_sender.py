from awscrt import mqtt
from concurrent.futures import Future

from typing import Tuple
from functools import partial
from application.domain.sensor.sender.sensor_sender import SensorSender
from application.domain.sensor.sensor import SensorData
from application.infrastructure.mqtt.config.mqtt_config import MqttConfig
from application.infrastructure.mqtt.sender.aws.aws_mqtt_connection import AwsMqttConnection
from application.infrastructure.mqtt.sender.mqtt_data_dto import MqttDataDto


class AwsMqttSender(SensorSender):

    def __init__(self, mqtt_config: MqttConfig):
        self.mqtt_config = mqtt_config
        self.aws_connection = AwsMqttConnection(mqtt_config)

    def send(self, sensor_data: SensorData):
        data_point = MqttDataDto(self.mqtt_config.station_id, sensor_data.value, sensor_data.timestamp)
        print('sending via MQTT to topic = ', self.mqtt_config.topic, ' , data = ', data_point.to_string())
        result: Tuple[Future, int] = self.aws_connection.get_mqtt_connection().publish(
            topic=self.mqtt_config.topic,
            payload=data_point.to_string(),
            qos=mqtt.QoS.AT_LEAST_ONCE)

        future_result, package_id = result
        future_result.add_done_callback(partial(self.send_result, data_point))

    def send_result(self, data_point: MqttDataDto, future: Future):
        try:
            if future.exception():
                print('ERROR: failed to send via MQTT to topic = ', self.mqtt_config.topic,
                      ' , data = ', data_point.to_string(), ' exception code is ', future.exception())
        except Exception as exception:
            print('ERROR: failed to parse FUTURE, exception is = ', exception)
