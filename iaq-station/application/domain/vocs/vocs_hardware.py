from abc import abstractmethod


class VOCsHardware:
    """
        An interface to represent a physical VOCS sensor/hardware.
    """

    @abstractmethod
    def read_tvocs(self) -> float:
        """
        reads the tvocs value from the actual physical sensor/hardware.

        :returns the tvocs value
        """
        raise NotImplementedError
