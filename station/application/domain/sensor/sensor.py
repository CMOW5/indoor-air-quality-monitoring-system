from abc import abstractmethod
import json
from datetime import datetime


class SensorData:
    """
      A class that represents the appropriate format read from a sensor.

      Attributes
      ----------
      value : number
          the variable value we're reading from the sensor
      timestamp : str
          the time at which we read the data
    """

    def __init__(self, value, timestamp):
        self.value = value
        self.timestamp = timestamp

    def to_json(self) -> dict:
        """
        the json representation of the data.

        :returns the sensor data as a json dictionary
        """
        return {
            'value': self.value,
            'timestamp': self.timestamp
        }

    def to_string(self) -> str:
        """
        the string representation of the data in json format.

        :returns the sensor data as a json string
        """
        return json.dumps(self.to_json())


class Sensor:
    """
        An interface to represent a sensor from which we want to read data.
    """
    DATE_FORMATTER = '%Y-%m-%dT%H:%M:%SZ'

    def read(self) -> SensorData:
        """
        reads the data from the sensor.

        :return the sensor data
        """
        sensor_value = self.read_from_hardware()
        timestamp = datetime.utcnow().strftime(Sensor.DATE_FORMATTER)
        return SensorData(sensor_value, timestamp)

    @abstractmethod
    def read_from_hardware(self):
        """
        reads the data from the actual physical hardware.
        reading from the hardware depends on the Sensor implementation

        :return the sensor data
        """
        raise NotImplementedError
