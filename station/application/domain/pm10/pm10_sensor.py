from datetime import datetime

from station.application.domain.pm10.pm10_hardware import PM10Hardware
from station.application.domain.sensor.sensor import Sensor, SensorData


class PM10Sensor(Sensor):
    """
        A class that represents a pm 10 sensor.

        Attributes
        ----------
        hardware :
            the physical pm10 sensor/hardware from which we can read the pm10 value
    """

    def __init__(self, hardware: PM10Hardware):
        self.hardware = hardware

    def read(self) -> SensorData:
        """
        reads the pm10 value from the sensor.

        :return the pm10 sensor data
        """
        pm10 = self.hardware.read_pm10()
        timestamp = datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ')
        return SensorData(pm10, timestamp)
