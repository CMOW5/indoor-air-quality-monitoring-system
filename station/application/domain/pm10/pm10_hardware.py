from abc import abstractmethod


class PM10Hardware:
    """
        An interface to represent a physical pm10 sensor/hardware.
    """

    @abstractmethod
    def read_pm10(self) -> float:
        """
        reads the pm10 value from the actual physical sensor/hardware.

        :returns the pm10 value
        """
        raise NotImplementedError
