
from application.domain.sensor.sensor import Sensor
from application.domain.temperature.temperature_hardware import TemperatureHardware


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

    def read_from_hardware(self):
        """
        reads the temperature value from the sensor.

        :return the temperature value in celsius
        """
        return self.hardware.read_temperature()
