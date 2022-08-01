from application.domain.pm10.pm10_hardware import PM10Hardware
from application.domain.pm25.pm25_hardware import PM25Hardware


class FakePMS5003(PM25Hardware, PM10Hardware):
    """
     A fake implementation of the PMS5003 sensor (pm2.5 & pm10) to be used for testing purposes.
     we're just increasing the variable by x_step to generate fake data
    """

    def __init__(self):
        self.pm25 = 0
        self.min_pm25 = 0
        self.max_pm25 = 1000
        self.pm25_step = 50

        self.pm10 = 0
        self.min_pm10 = 0
        self.max_pm10 = 1000
        self.pm10_step = 100

        self.current_pm10 = 0

    def read_pm25(self):
        self.pm25 = self.min_pm25 if self.pm25 == self.max_pm25 \
            else self.pm25 + self.pm25_step

        return self.pm25

    def read_pm10(self) -> float:
        self.pm10 = self.min_pm10 if self.pm10 == self.max_pm10 \
            else self.pm10 + self.pm10_step

        return self.pm10

