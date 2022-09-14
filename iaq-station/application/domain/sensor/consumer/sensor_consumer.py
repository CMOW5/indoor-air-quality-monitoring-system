import logging
from application.domain.sensor.sender.sensor_sender import SensorSender, SendMqttMessageFailedException
from application.domain.sensor.sensor_queue import SensorQueue, EmptySensorQueueException


class SensorConsumer:
    """
      A consumer which gets a value from the sensor queue and sends it

      Attributes
      ----------
      queue :
          the queue from which we can get the sensor data
      sender :
          a sender to send the data somewhere. It could send the data using different protocols depending on
          the implementation (HTTP, MQTT, sockets, etc)
    """

    def __init__(self, queue: SensorQueue, sender: SensorSender):
        self.queue = queue
        self.sender = sender

    def consume(self):
        """
        consumes the data by reading from the queue and sending that data using the SensorSender
        """
        sensor_data = None
        try:
            sensor_data = self.queue.get()
            self.sender.send(sensor_data)
            logging.info('consumed value from queue = %s, sensor_data = %s', self.queue.name, sensor_data.to_string())
        except EmptySensorQueueException:
            logging.info("sensor queue %s is empty", self.queue.name)
        except SendMqttMessageFailedException as exception:
            logging.exception("exception happened while trying to send the value over mqtt. Re-adding the datapoint to "
                              "the queue %s. Exception is %s", self.queue.name, exception)
            self.queue.put(sensor_data)
