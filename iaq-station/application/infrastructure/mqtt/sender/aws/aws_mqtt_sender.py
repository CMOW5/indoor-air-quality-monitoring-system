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
        print('sending via MQTT to topic = ', self.mqtt_config.topic, ' , data = ', datapoint.to_string())

        try:
            future_result = self.aws_connection.publish(topic=self.mqtt_config.topic, datapoint=datapoint)
            print('DONE sending via MQTT ', future_result.result(timeout=0.5))
        except Exception as exception:
            print("ERROR trying to send message over AWS MQTT, exception = ", exception)
            raise SendMqttMessageFailedException()
