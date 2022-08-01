import threading
import serial
import time
from adafruit_pm25.uart import PM25_UART

from application.domain.pm10.pm10_hardware import PM10Hardware
from application.domain.pm25.pm25_hardware import PM25Hardware


class PMS5003(PM25Hardware, PM10Hardware):
    threadLock = threading.Lock()

    def __init__(self):
        self.uart = serial.Serial("/dev/ttyS0", baudrate=9600)  # todo: move this to app config
        self.pm25 = PM25_UART(self.uart)
        self.last_read = time.time()
        self.cache_time_to_live_seconds = 1
        self.cached_sensor_data = None

    def read_sensor_data(self) -> dict:
        """
           reads the data from the PMS5003 sensor via UART. Since this is a serial communication, we cannot do parallel reads
           nor can we read data to quickly. Otherwise, we will get weird exceptions from the pm25 uart library.
           Because of this, we are limiting the amount of reads to the actual sensor. Tho achieve this, we are caching the
           latest sensor data read. If we ask for data too often (depending on the max_read_time)
           we just return the cached value. If the cache expires, then we can read the data from the sensor again
           and update the value in the cache
        """
        with self.threadLock:
            read_time = time.time()

            if self.cache_expired(read_time):
                self.cached_sensor_data = self.pm25.read()
                self.last_read = read_time

            return self.cached_sensor_data

    def cache_expired(self, read_time) -> bool:
        return (read_time - self.last_read > self.cache_time_to_live_seconds) or not self.cached_sensor_data

    def read_pm25(self) -> float:
        return self.read_sensor_data()["pm25 standard"]

    def read_pm10(self) -> float:
        return self.read_sensor_data()["pm10 standard"]
