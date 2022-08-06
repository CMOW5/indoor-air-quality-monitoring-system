from abc import abstractmethod

from application.domain.sensor.sensor import SensorData


class SensorQueue:
    """
        An interface to represent a Queue in which we want to store the data coming from a sensor.
        We want to store the values from the sensor in a queue to avoid data loss. This is, we don't want to
        process the data immediately when we do the sensor reading (we can consume the data in the queue at the
        appropriate time)
    """

    @abstractmethod
    def put(self, value: SensorData):
        """
        puts a new entry in the queue

        :param value: the new data entry
        """
        raise NotImplementedError

    @abstractmethod
    def get(self) -> SensorData:
        """
        reads the value from the queue.

        :returns the sensor data
        """
        raise NotImplementedError

    @abstractmethod
    def is_empty(self) -> bool:
        """
        checks if the queue is empty.

        :returns True if the queue is empty, False otherwise
        """
        raise NotImplementedError

    @abstractmethod
    def is_full(self) -> bool:
        """
        checks if the queue is full.

        :returns True if the queue is full, False otherwise
        """
        raise NotImplementedError
