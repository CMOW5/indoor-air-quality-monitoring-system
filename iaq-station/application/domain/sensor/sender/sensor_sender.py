from abc import abstractmethod

from application.domain.sensor.sensor import SensorData


class SensorSender:
    """
        An interface to represent a sender to send the sensor data.
    """

    @abstractmethod
    def send(self, sensor_data: SensorData):
        """
        sends the sensor data.

        :param sensor_data: the sensor data to send
        """
        raise NotImplementedError


class SendMqttMessageFailedException(Exception):

    def __init__(self, message="Failed to send message over MQTT"):
        self.message = message
        super().__init__(self.message)
