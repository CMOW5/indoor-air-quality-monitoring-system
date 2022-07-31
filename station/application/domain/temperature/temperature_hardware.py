from abc import abstractmethod


class TemperatureHardware:
    """
        An interface to represent a physical temperature sensor/hardware.
    """

    @abstractmethod
    def read_temperature(self) -> float:
        """
        the temperature value from the actual physical sensor/hardware.

        :returns the temperature value
        """
        raise NotImplementedError
