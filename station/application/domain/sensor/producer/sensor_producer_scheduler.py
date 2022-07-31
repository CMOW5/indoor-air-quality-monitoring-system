import threading

from station.application.domain.sensor.producer.sensor_producer import SensorProducer


class SensorProducerScheduler:
    """
       This class is used to schedule a sensor producer to produce data every X amount of time.
       This class purpose is to make sure that the producer is producing data on a regular basis.
       It is a producer wrapper, and it's constantly asking the producer to produce the data

       Attributes
       ----------
       sensor_producer :
           the producer we're constantly asking to produce data
    """

    def __init__(self, sensor_producer: SensorProducer):
        self.sensor_producer = sensor_producer
        self.__scheduled = False

    def schedule(self, time):
        """
        schedule the producer to produce data every X amount of time defined by the time parameter
        :param time: the time in seconds to define how often the data should be produced
       """
        threading.Timer(time, self.schedule, [time]).start()
        self.sensor_producer.produce()
        self.__scheduled = True

    def is_scheduled(self):
        """
        checks if this scheduler has been scheduled
        :return True if the scheduler has been scheduled
        """
        return self.__scheduled
