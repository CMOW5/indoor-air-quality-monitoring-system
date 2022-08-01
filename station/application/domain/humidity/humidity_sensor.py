from station.application.domain.humidity.humidity_hardware import HumidityHardware
from station.application.domain.sensor.sensor import Sensor


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

    def __read_from_hardware(self):
        """
        reads the relative humidity value from the sensor.

        :return the relative humidity
        """
        return self.hardware.read_humidity()

