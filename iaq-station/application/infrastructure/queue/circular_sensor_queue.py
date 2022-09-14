from application.domain.sensor.sensor import SensorData
from application.domain.sensor.sensor_queue import SensorQueue, EmptySensorQueueException
from application.utils.decorators.synchronized import synchronized


class CircularSensorQueue(SensorQueue):
    """
     An implementation of SensorQueue.
     When the queue is full, the older data will be overridden by most recent data
    """

    def __init__(self, capacity=100):
        super().__init__()
        self.capacity = capacity
        self.queue = [None] * self.capacity
        self.read_pointer = 0
        self.write_pointer = 0

    @synchronized
    def put(self, value: SensorData):
        """
        puts a new entry in the queue. If the queue is full, then the new record will
        be store in the beginning of the queue. This means, older data will be overridden by most
        recent data if it was not consumed in time

        :param value: the new data entry
        """
        self.queue[self.write_pointer] = value
        self.write_pointer = (self.write_pointer + 1) % self.capacity

    @synchronized
    def get(self) -> SensorData:
        """
        reads the value from the queue.

        @return the sensor data
        @raise EmptySensorQueueException if the queue is empty
        """
        if self.is_empty():
            raise EmptySensorQueueException

        read_value = self.queue[self.read_pointer]
        self.queue[self.read_pointer] = None
        self.read_pointer = (self.read_pointer + 1) % self.capacity
        return read_value

    def is_empty(self) -> bool:
        """
        checks if the queue is empty.

        :returns True if the queue is empty, False otherwise
        """
        return self.read_pointer == self.write_pointer

    def is_full(self) -> bool:
        """
        checks if the queue is full.

        :returns True if the queue is full, False otherwise
        """
        return self.read_pointer == self.write_pointer



