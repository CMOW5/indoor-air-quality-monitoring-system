from application.infrastructure.app.hardware_registry.hardware_registry import HardwareRegistry
from application.infrastructure.hardware.bme280_sensor.fake_bme280_hardware import FakeBME280
from application.infrastructure.hardware.pms5003_sensor.fake_pms5003_hardware import FakePMS5003
from application.infrastructure.hardware.sgp30_sensor.fake_sgp30_hardware import FakeSGP30


class GenericLinuxHardwareRegistry(HardwareRegistry):
    """
      A generic linux hardware registry. This is, a bunch of software sensors (fake sensors) for testing purposes
    """
    def __init__(self):
        super().__init__()
        self.hardware['pm25'] = FakePMS5003()
        self.hardware['pm10'] = FakePMS5003()
        self.hardware['temperature'] = FakeBME280()
        self.hardware['humidity'] = FakeBME280()
        self.hardware['co2'] = FakeSGP30()
        self.hardware['vocs'] = FakeSGP30()
