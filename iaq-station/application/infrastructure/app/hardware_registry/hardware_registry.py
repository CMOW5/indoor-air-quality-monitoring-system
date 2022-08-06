from application.domain.co2.co2_hardware import CO2Hardware
from application.domain.humidity.humidity_hardware import HumidityHardware
from application.domain.pm10.pm10_hardware import PM10Hardware
from application.domain.pm25.pm25_hardware import PM25Hardware
from application.domain.temperature.temperature_hardware import TemperatureHardware
from application.domain.vocs.vocs_hardware import VOCsHardware


class HardwareRegistry:
    """
      A class that hold the available hardware we can interface with.
      For example, we have a temp, humidity, pm and gas sensors, so we store these references in this hardware registry.
      When we need a hardware to interface with, we just ask the hardware registry to give us the hardware we are asking
      for. This allows us to simplify the hardware creation/reference across the application because in reality these
      physical sensors are actually singletons.
      This also allow us to boostrap a different set of hardware depending on the platform this code is running on
      For example, for a generic linux pc we can return 'fake' software sensors. For a raspberry pi, we can bootstrap
      a set of sensors compatible with this platform. Any platform that is able to run python code should be able
      to work if the appropriate set of hardware (a.k.a hardware registry) is provided

      Attributes
      ----------
      hardware : dict
          a dictionary containing the available hardware
    """

    def __init__(self):
        self.hardware = dict()

    def register(self, name, hardware):
        self.hardware[name] = hardware

    def get_hardware(self, name: str):
        return self.hardware.get(name)

    def get_temperature_hardware(self) -> TemperatureHardware:
        return self.get_hardware('temperature')

    def get_humidity_hardware(self) -> HumidityHardware:
        return self.get_hardware('humidity')

    def get_pm25_hardware(self) -> PM25Hardware:
        return self.get_hardware('pm25')

    def get_pm10_hardware(self) -> PM10Hardware:
        return self.get_hardware('pm10')

    def get_co2_hardware(self) -> CO2Hardware:
        return self.get_hardware('co2')

    def get_vocs_hardware(self) -> VOCsHardware:
        return self.get_hardware('vocs')
