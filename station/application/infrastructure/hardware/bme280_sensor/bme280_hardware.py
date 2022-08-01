import threading
import time

import board
from adafruit_bme280 import basic as adafruit_bme280

from application.domain.humidity.humidity_hardware import HumidityHardware
from application.domain.temperature.temperature_hardware import TemperatureHardware


class BME280(TemperatureHardware, HumidityHardware):
    """
     An implementation of the BME280 sensor. This class is a wrapper for adafruit_bme280 library to
     read the temperature and humidity sensor using I2C communication
    """

    threadLock = threading.Lock()

    def __init__(self):
        self.i2c = board.I2C()  # uses board.SCL and board.SDA
        self.bme280 = adafruit_bme280.Adafruit_BME280_I2C(self.i2c)
        self.last_read = time.time()
        self.cached_sensor_data = dict()
        self.cache_time_to_live_seconds = 1

    def read_sensor_data(self) -> dict:
        with self.threadLock:
            read_time = time.time()

            if self.cache_expired(read_time):
                self.cached_sensor_data["temperature"] = self.bme280.temperature
                self.cached_sensor_data["relative_humidity"] = self.bme280.relative_humidity
                self.last_read = read_time

            return self.cached_sensor_data

    def cache_expired(self, read_time) -> bool:
        return (read_time - self.last_read > self.cache_time_to_live_seconds) or not self.cached_sensor_data

    def read_temperature(self):
        return self.read_sensor_data()["temperature"]

    def read_humidity(self):
        return self.read_sensor_data()["relative_humidity"]

