from datetime import datetime

from station.application.domain.sensor.sensor import Sensor, SensorData
from station.application.domain.vocs.vocs_hardware import VOCsHardware


class VOCsSensor(Sensor):
    """
     A class that represents a VOCS sensor.

     Attributes
     ----------
     hardware :
         the physical VOCS sensor/hardware from which we can read the tVOCS value
    """

    def __init__(self, hardware: VOCsHardware):
        self.hardware = hardware

    def __read_from_hardware(self):
        """
        reads the tVOCS value from the sensor.

        :return the tVOCS value
        """
        return self.hardware.read_tvocs()

