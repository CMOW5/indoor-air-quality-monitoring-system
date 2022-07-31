from datetime import datetime

from station.application.domain.sensor.sensor import Sensor, SensorData
from station.application.domain.temperature.temperature_hardware import TemperatureHardware


class TemperatureSensor(Sensor):
    """
     A class that represents a temperature sensor.

     Attributes
     ----------
     hardware :
         the physical temperature sensor/hardware from which we can read the temperature value
    """

    def __init__(self, hardware: TemperatureHardware):
        self.hardware = hardware

    def read(self) -> SensorData:
        """
        reads the temperature value from the sensor.

        :return the temperature sensor data
        """

        temperature = self.hardware.read_temperature()
        timestamp = datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ')
        return SensorData(temperature, timestamp)

