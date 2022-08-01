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

    def read(self) -> SensorData:
        """
        reads the tVOCS value from the sensor.

        :return the tVOCS sensor data
        """
        tvocs = self.hardware.read_tvocs()
        timestamp = datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ')
        return SensorData(tvocs, timestamp)

