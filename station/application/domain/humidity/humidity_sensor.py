from datetime import datetime

from station.application.domain.humidity.humidity_hardware import HumidityHardware
from station.application.domain.sensor.sensor import Sensor, SensorData


class HumiditySensor(Sensor):
    """
     A class that represents a humidity sensor.

     Attributes
     ----------
     hardware :
         the physical humidity sensor/hardware from which we can read the relative humidity value
    """
    def __init__(self, hardware: HumidityHardware):
        self.hardware = hardware

    def read(self) -> SensorData:
        """
        reads the relative humidity value from the sensor.

        :return the relative humidity sensor data
        """
        humidity = self.hardware.read_humidity()
        timestamp = datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ')
        return SensorData(humidity, timestamp)

