from station.application.domain.sensor.sensor import Sensor
from station.application.domain.sensor.sensor_queue import SensorQueue


class SensorProducer:
    """
     A producer that reads the data from the sensor and puts it into the queue to be processed later by one of the
     consumers

     Attributes
     ----------
     sensor :
         the sensor to read data from
     queue :
         the queue from which we can get the sensor data
    """

    def __init__(self, sensor: Sensor, queue: SensorQueue):
        self.sensor = sensor
        self.queue = queue

    def produce(self):
        """
         produces data by reading the sensor and putting the value into the queue
        """
        try:
            sensor_data = self.sensor.read()
            self.queue.put(sensor_data)
            print('produced value = ', sensor_data.to_string())
        except Exception as exception:
            print("something wrong happened while trying to read the data from the sensor, exception is = ", exception)

