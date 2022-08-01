from application.domain.humidity.humidity_hardware import HumidityHardware
from application.domain.temperature.temperature_hardware import TemperatureHardware


class FakeBME280(TemperatureHardware, HumidityHardware):
    """
     A fake implementation of the BME280 sensor (temperature & humidity) to be used for testing purposes.
     we're just increasing the variable by x_step to generate fake data
    """

    def __init__(self):
        self.temperature = 0
        self.min_temperature = 0
        self.max_temperature = 40
        self.temperature_step = 1

        self.relative_humidity = 0
        self.min_relative_humidity = 0
        self.max_relative_humidity = 100
        self.relative_humidity_step = 5

    def read_temperature(self) -> float:
        self.temperature = self.min_temperature if self.temperature == self.max_temperature \
                                                else self.temperature + self.temperature_step
        return self.temperature

    def read_humidity(self) -> float:
        self.relative_humidity = self.min_relative_humidity if self.relative_humidity == self.max_relative_humidity \
                                                            else self.relative_humidity + self.relative_humidity_step
        return self.relative_humidity
