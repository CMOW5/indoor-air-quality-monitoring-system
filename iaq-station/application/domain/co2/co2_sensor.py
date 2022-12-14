from application.domain.co2.co2_hardware import CO2Hardware
from application.domain.sensor.sensor import Sensor


class CO2Sensor(Sensor):
    """
     A class that represents a co2 sensor.

     Attributes
     ----------
     hardware :
         the physical co2 sensor/hardware from which we can read the co2 value
    """
    def __init__(self, hardware: CO2Hardware):
        super().__init__("co2")
        self.hardware = hardware

    def read_from_hardware(self):
        """
        reads the co2 value from the sensor.

        :return the co2 value
        """
        return self.hardware.read_co2()

