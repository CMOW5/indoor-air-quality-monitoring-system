from abc import abstractmethod


class HumidityHardware:
    """
        An interface to represent a physical humidity sensor/hardware.
    """
    @abstractmethod
    def read_humidity(self) -> float:
        """
        reads the humidity value from the actual physical sensor/hardware.

        :returns the humidity value
        """
        raise NotImplementedError
