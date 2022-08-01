from application.infrastructure.app.hardware_registry.hardware_registry import HardwareRegistry
from application.infrastructure.hardware.bme280_sensor.bme280_hardware import BME280
from application.infrastructure.hardware.pms5003_sensor.pms5003_hardware import PMS5003
from application.infrastructure.hardware.sgp30_sensor.baseline.sgp30_baseline_repository import SGP30Repository
from application.infrastructure.hardware.sgp30_sensor.sgp30_hardware import SGP30


class RaspberryPiHardwareRegistry(HardwareRegistry):
    def __init__(self):
        super().__init__()

        pms5003 = PMS5003()
        self.hardware['pm25'] = pms5003
        self.hardware['pm10'] = pms5003

        bme280 = BME280()
        self.hardware['temperature'] = bme280
        self.hardware['humidity'] = bme280

        # you might be wondering why we're passing the same BME280 instance twice.
        # this is because we treat each variable (temperature, humidity, pressure) as its own
        # thing. This is, we assume that we have a dedicated sensor for each variable.
        # However, having a dedicated sensor for each variable is not always possible since some
        # hardware will have 2 or more sensors embedded in the same chip, like the BME280 which
        # has the temperature, humidity, and pressure sensors embedded. We don't want to couple the code
        # with this because hardware can change over time, and we won't always have a hardware with the same amount of
        # embedded sensors
        sgp30 = SGP30(self.hardware['temperature'], self.hardware['humidity'], SGP30Repository())
        self.hardware['co2'] = sgp30
        self.hardware['vocs'] = sgp30
