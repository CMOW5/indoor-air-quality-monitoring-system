from application.domain.pm10.pm10_hardware import PM10Hardware
from application.domain.sensor.sensor import Sensor


class PM10Sensor(Sensor):
    """
        A class that represents a pm 10 sensor.

        Attributes
        ----------
        hardware :
            the physical pm10 sensor/hardware from which we can read the pm10 value
    """

    def __init__(self, hardware: PM10Hardware):
        super().__init__("pm10")
        self.hardware = hardware

    def read_from_hardware(self):
        """
        reads the pm10 value from the sensor.

        :return the pm10 value
        """
        return self.hardware.read_pm10()
