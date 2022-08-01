from application.domain.sensor.sender.sensor_sender import SensorSender
from application.domain.sensor.sensor_queue import SensorQueue


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
        try:
            sensor_data = self.queue.get()
            self.sender.send(sensor_data)
            print('consumed value = ', sensor_data.to_string())
        except Exception as exception:
            print("exception happened while trying to consume the value, exception = ", exception)
