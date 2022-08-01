from datetime import datetime

from station.application.domain.pm25.pm25_hardware import PM25Hardware
from station.application.domain.sensor.sensor import Sensor, SensorData


class PM25Sensor(Sensor):
    """
        A class that represents a pm2.5 sensor.

        Attributes
        ----------
        hardware :
            the physical pm2.5 sensor/hardware from which we can read the pm2.5 value
    """

    def __init__(self, hardware: PM25Hardware):
        self.hardware = hardware

    def read(self) -> SensorData:
        """
           reads the pm2.5 value from the sensor.

           :return the pm2.5 sensor data
        """
        pm25 = self.hardware.read_pm25()
        timestamp = datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ')
        return SensorData(pm25, timestamp)
