import threading

from application.domain.sensor.consumer.sensor_consumer import SensorConsumer


class SensorConsumerScheduler:
    """
        This class is used to schedule a sensor consumer to consume data every X amount of time.
        This class purpose is to make sure that the consumer is consuming data on a regular basis.
        It is a consumer wrapper, and it's constantly asking the consumer to consume the data

        Attributes
        ----------
        sensor_consumer :
            the consumer we're constantly asking to consume data
    """

    def __init__(self, sensor_consumer: SensorConsumer):
        self.__scheduled = False
        self.sensor_consumer = sensor_consumer

    def schedule(self, time):
        """
        schedule the consumer to consume data every X amount of time defined by the time parameter
        :param time: the time in seconds to define how often the data should be consumed
        """
        threading.Timer(time, self.schedule, [time]).start()
        self.sensor_consumer.consume()
        self.__scheduled = True

    def is_scheduled(self):
        """
        checks if this scheduler has been scheduled
        :return True if the scheduler has been scheduled
        """
        return self.__scheduled
