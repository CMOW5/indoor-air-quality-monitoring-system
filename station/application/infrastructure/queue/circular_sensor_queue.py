from application.domain.sensor.sensor import SensorData
from application.domain.sensor.sensor_queue import SensorQueue
from application.utils.decorators.synchronized import synchronized


class CircularSensorQueue(SensorQueue):

    def __init__(self, capacity=100):
        self.capacity = capacity
        self.queue = [None] * self.capacity
        self.read_pointer = 0
        self.write_pointer = 0

    @synchronized
    def put(self, value: SensorData):
        self.queue[self.write_pointer] = value
        self.write_pointer = (self.write_pointer + 1) % self.capacity

    @synchronized
    def get(self) -> SensorData:
        if self.is_empty():
            raise EmptySensorQueueException

        read_value = self.queue[self.read_pointer]
        self.queue[self.read_pointer] = None
        self.read_pointer = (self.read_pointer + 1) % self.capacity
        return read_value

    def is_empty(self) -> bool:
        return self.read_pointer == self.write_pointer

    def is_full(self) -> bool:
        return self.read_pointer == self.write_pointer


class EmptySensorQueueException(Exception):

    def __init__(self, message="Sensor Queue is empty"):
        self.message = message
        super().__init__(self.message)
