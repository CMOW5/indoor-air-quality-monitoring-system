from station.application.domain.co2.co2_hardware import CO2Hardware
from station.application.domain.sensor.sensor import Sensor


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

    def __read_from_hardware(self):
        """
        reads the co2 value from the sensor.

        :return the co2 value
        """
        return self.hardware.read_co2()

