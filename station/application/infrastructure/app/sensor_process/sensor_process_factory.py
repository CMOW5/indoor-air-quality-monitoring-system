from application.domain.sensor.consumer.sensor_consumer import SensorConsumer
from application.domain.sensor.consumer.sensor_consumer_scheduler import SensorConsumerScheduler
from application.domain.sensor.producer.sensor_producer import SensorProducer
from application.domain.sensor.producer.sensor_producer_scheduler import SensorProducerScheduler
from application.domain.sensor.sender.sensor_sender import SensorSender
from application.domain.sensor.sensor import Sensor
from application.domain.sensor.sensor_queue import SensorQueue


class SensorProcessFactory:
    """
      A factory class to create a sensor process. A sensor process consist of a sensor producer which is
      producing data from a sensor. A sensor queue in which the producer is storing values. A consumer that is
      reading the queue to 'consume' or send the sensor data using a SensorSender.
      the producer and consumer and then scheduled to run on a regular basis depending on the
      consumer_time and producer_time attributes
    """

    def __init__(self, sensor: Sensor, sensor_queue: SensorQueue, sensor_sender: SensorSender,
                 producer_time: float, consumer_time: float):
        self.sensor = sensor
        self.sensor_queue = sensor_queue
        self.sensor_sender = sensor_sender
        self.producer_time = producer_time
        self.consumer_time = consumer_time
        self._sensor_producer = None
        self._sensor_consumer = None

    def create(self):
        """
          Creates a sensor process and schedules the sensor producer and sensor consumer to run on a regular basis
          depending on the consumer_time and producer_time attributes
        """
        self._sensor_producer = SensorProducer(self.sensor, self.sensor_queue)
        sensor_producer_scheduler = SensorProducerScheduler(self._sensor_producer)
        sensor_producer_scheduler.schedule(self.producer_time)

        self._sensor_consumer = SensorConsumer(self.sensor_queue, self.sensor_sender)
        temperature_sensor_consumer_scheduler = SensorConsumerScheduler(self._sensor_consumer)
        temperature_sensor_consumer_scheduler.schedule(self.consumer_time)
