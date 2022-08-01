from abc import abstractmethod

from application.domain.sensor.sender.sensor_sender import SensorSender
from application.domain.sensor.sensor import SensorData


class FakeMqttSender(SensorSender):

    def send(self, sensor_data: SensorData):
        print('sent sensor data via FAKE MQTT')

