from abc import abstractmethod


class PM25Hardware:
    """
       An interface to represent a physical pm2.5 sensor/hardware.
    """

    @abstractmethod
    def read_pm25(self) -> float:
        """
        reads the pm2.5 value from the actual physical sensor/hardware.

        :returns the pm2.5 value
        """
        raise NotImplementedError
