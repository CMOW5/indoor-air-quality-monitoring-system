from application.domain.sensor.sender.sensor_sender import SensorSender


class SenderRegistry:
    """
      A class that hold the available senders we can interface with.


      Attributes
      ----------
      senders : dict
          a dictionary containing the available sensor senders
    """

    def __init__(self):
        self.senders = dict()

    def register(self, name, hardware):
        self.senders[name] = hardware

    def get_sender(self, name: str):
        return self.senders.get(name)

    def get_mqtt_temp_sender(self) -> SensorSender:
        return self.get_sender('temperature')

    def get_mqtt_humidity_sender(self) -> SensorSender:
        return self.get_sender('humidity')

    def get_mqtt_pm25_sender(self) -> SensorSender:
        return self.get_sender('pm25')

    def get_mqtt_pm10_sender(self) -> SensorSender:
        return self.get_sender('pm10')

    def get_mqtt_co2_sender(self) -> SensorSender:
        return self.get_sender('co2')

    def get_mqtt_vocs_sender(self) -> SensorSender:
        return self.get_sender('vocs')