import logging
from application.domain.sensor.sender.sensor_sender import SensorSender, SendMqttMessageFailedException
from application.domain.sensor.sensor import SensorData
from application.infrastructure.mqtt.config.mqtt_config import MqttConfig
from application.infrastructure.mqtt.sender.aws.aws_mqtt_connection import AwsMqttConnection
from application.infrastructure.mqtt.sender.mqtt_data_dto import MqttDataDto


class AwsMqttSender(SensorSender):

    def __init__(self, mqtt_config: MqttConfig):
        self.mqtt_config = mqtt_config
        self.aws_connection = AwsMqttConnection(mqtt_config)

    def send(self, sensor_data: SensorData):
        datapoint = MqttDataDto(self.mqtt_config.station_id, sensor_data.value, sensor_data.timestamp)
        logging.info('sending the message via MQTT to topic = %s, data = %s',
                     self.mqtt_config.topic, datapoint.to_string())
        try:
            future_result = self.aws_connection.publish(topic=self.mqtt_config.topic, datapoint=datapoint)
            logging.info('done sending the message via MQTT %s', future_result.result(timeout=0.5))
        except Exception as exception:
            logging.exception("ERROR trying to send the message over AWS MQTT, exception = %s", exception)
            raise SendMqttMessageFailedException(exception)
