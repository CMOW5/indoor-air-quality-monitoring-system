from queue import PriorityQueue
from application.domain.sensor.sensor import SensorData
from application.domain.sensor.sensor_queue import SensorQueue, EmptySensorQueueException
from application.utils.decorators.synchronized import synchronized


class CircularPrioritySensorQueue(SensorQueue):
    """
     An implementation of SensorQueue.
     When the queue is full, the older data will be overridden by most recent data.
    """

    def __init__(self, capacity=100):
        self.queue = PriorityQueue(capacity)
        self.capacity = capacity

    @synchronized
    def put(self, value: SensorData):
        """
        puts a new entry in the queue. If the queue is full, then the oldest record will
        be deleted to make room for the newer data

        :param value: the new data entry
        """
        if not value:
            return

        if self.is_full():
            # if queue is full, then let's delete the older data to make room for newer data
            self.queue.get()

        self.queue.put(value)

    @synchronized
    def get(self) -> SensorData:
        """
        reads the value from the queue.

        @return the sensor data
        @raise EmptySensorQueueException if the queue is empty
        """
        if self.is_empty():
            raise EmptySensorQueueException

        return self.queue.get()

    def is_empty(self) -> bool:
        """
        checks if the queue is empty.

        :returns True if the queue is empty, False otherwise
        """
        return self.queue.qsize() == 0

    def is_full(self) -> bool:
        """
        checks if the queue is full.

        :returns True if the queue is full, False otherwise
        """
        return self.queue.qsize() >= self.capacity
