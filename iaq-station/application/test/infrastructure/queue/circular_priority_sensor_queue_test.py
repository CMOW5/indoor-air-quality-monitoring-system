import unittest

from application.domain.sensor.sensor import SensorData
from application.infrastructure.queue.circular_priority_sensor_queue import CircularPrioritySensorQueue, \
    EmptySensorQueueException


class TestStringMethods(unittest.TestCase):
    """
    Unit Tests for CircularSensorPriorityQueue
    """

    def test_queue_gets_data_in_the_right_order(self):
        """
        CircularSensorPriorityQueue should return the oldest data first
        """
        queue = CircularPrioritySensorQueue(10)

        older_sensor_data = SensorData(1, "2022-08-15T12:00:01Z")
        queue.put(older_sensor_data)

        newer_sensor_data = SensorData(2, "2022-08-15T12:00:02Z")
        queue.put(newer_sensor_data)

        queue_value = queue.get()
        self.assertEqual(older_sensor_data, queue_value, "objects should be equal. older_sensor_data = {}, "
                                                         "queue_value = {}".format(older_sensor_data.to_string(),
                                                                                   queue_value.to_string()))
        queue_value = queue.get()
        self.assertEqual(newer_sensor_data, queue_value, "objects should be equal. newer_sensor_data = {}, "
                                                         "queue_value = {}".format(newer_sensor_data.to_string(),
                                                                                   queue_value.to_string()))

    def test_queue_deletes_older_data_if_full(self):
        """
        When CircularSensorPriorityQueue is full and we insert a new entry, the oldest data should be deleted
        to make room for the most recent data
        """
        queue = CircularPrioritySensorQueue(2)

        sensor_data_1 = SensorData(1, "2022-08-15T12:00:01Z")
        queue.put(sensor_data_1)

        sensor_data_2 = SensorData(2, "2022-08-15T12:00:02Z")
        queue.put(sensor_data_2)

        sensor_data_3 = SensorData(3, "2022-08-15T12:00:03Z")
        queue.put(sensor_data_3)

        self.assertTrue(queue.is_full())

        queue_value = queue.get()
        self.assertEqual(sensor_data_2, queue_value, "objects should be equal. sensor_data_2 = {}, "
                                                     "queue_value = {}".format(sensor_data_2.to_string(),
                                                                               queue_value.to_string()))
        queue_value = queue.get()
        self.assertEqual(sensor_data_3, queue_value, "objects should be equal. sensor_data_3 = {}, "
                                                     "queue_value = {}".format(sensor_data_3.to_string(),
                                                                               queue_value.to_string()))
        self.assertTrue(queue.is_empty())

    def test_queue_throws_exception_if_empty(self):
        """
        When CircularSensorPriorityQueue is empty, and we try to read the data, then it should raise
        an EmptySensorQueueException
        """
        queue = CircularPrioritySensorQueue(10)

        with self.assertRaises(EmptySensorQueueException):
            queue.get()


if __name__ == '__main__':
    unittest.main()
