from abc import abstractmethod


class CO2Hardware:
    """
        An interface to represent a physical co2 sensor/hardware.
    """

    @abstractmethod
    def read_co2(self) -> float:
        """
        reads the co2 value from the actual physical sensor/hardware.

        :returns the co2 value
        """
        raise NotImplementedError
