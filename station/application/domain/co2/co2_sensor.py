from datetime import datetime

from station.application.domain.co2.co2_hardware import CO2Hardware
from station.application.domain.sensor.sensor import Sensor, SensorData


class CO2Sensor(Sensor):
    """
     A class that represents a co2 sensor.

     Attributes
     ----------
     hardware :
         the physical co2 sensor/hardware from which we can read the co2 value
    """
    def __init__(self, hardware: CO2Hardware):
        self.hardware = hardware

    def read(self) -> SensorData:
        """
        reads the co2 value from the sensor.

        :return the co2 sensor data
        """
        eco2 = self.hardware.read_co2()
        timestamp = datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ')
        return SensorData(eco2, timestamp)

