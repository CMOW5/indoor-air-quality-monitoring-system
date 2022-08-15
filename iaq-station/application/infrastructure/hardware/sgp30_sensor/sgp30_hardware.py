import threading
import time

import board
import adafruit_sgp30

from application.domain.co2.co2_hardware import CO2Hardware
from application.domain.humidity.humidity_hardware import HumidityHardware
from application.domain.temperature.temperature_hardware import TemperatureHardware
from application.domain.vocs.vocs_hardware import VOCsHardware
from application.infrastructure.hardware.sgp30_sensor.baseline.sgp30_baseline_repository import SGP30Repository


class SGP30(CO2Hardware, VOCsHardware):
    """
     An implementation of the SGP30 sensor. This class is a wrapper for adafruit_sgp30 library to
     read the tvocs and eco2 values using I2C communication
    """

    threadLock = threading.Lock()

    BASELINE_CALIBRATION_12_HOURS_SECONDS = 43200

    BASELINE_DEFAULT_WRITE_TIME_SECONDS = 3600

    def __init__(self, temperature_hardware: TemperatureHardware, humidity_hardware: HumidityHardware,
                 sgp30_repository: SGP30Repository):
        self.temperature_hardware = temperature_hardware
        self.humidity_hardware = humidity_hardware
        self.sgp30_repository = sgp30_repository
        self.cache_time_to_live_seconds = 1
        self.cached_sensor_data = dict()
        self.last_read = time.time()
        self.i2c = board.I2C()
        self.sgp30 = adafruit_sgp30.Adafruit_SGP30(self.i2c)

        self.baseline_write_time_seconds = SGP30.BASELINE_DEFAULT_WRITE_TIME_SECONDS
        self.baseline_last_save = time.time()
        self.init_iaq_baseline()

    def init_iaq_baseline(self):
        """
        Restarting the sensor without reading back a previously stored baseline will result in the sensor
        trying to determine a new baseline. Here, we are getting a previously stored baseline from the persistent
        storage. If we can't get a baseline, the sensor needs to run for about 12H to get a new baseline
        @return:
        """
        self.set_temperature_and_humidity_compensation()

        try:
            eco2_baseline, tvocs_baseline = self.sgp30_repository.get_iaq_baseline()
            print("INFO setting iaq baseline = co2:{} , tvocs:{}".format(eco2_baseline, tvocs_baseline))
            self.sgp30.set_iaq_baseline(eco2_baseline, tvocs_baseline)
        except ValueError as error:
            print("WARNING while getting the iaq_baseline because we don't have one. setting the "
                  "baseline_write_time_seconds to 12 hours to start the calibration", error)
            """
            If no stored baseline is available after initializing the baseline algorithm, 
            the sensor has to run for 12 hours until the baseline can be stored.
            """
            self.baseline_write_time_seconds = SGP30.BASELINE_CALIBRATION_12_HOURS_SECONDS
        except FileNotFoundError as error:
            print('WARNING while setting the iaq_baseline ', error)

    def set_temperature_and_humidity_compensation(self):
        celsius = self.temperature_hardware.read_temperature()
        relative_humidity = self.humidity_hardware.read_humidity()
        self.sgp30.set_iaq_relative_humidity(celsius=celsius, relative_humidity=relative_humidity)

    def read_sensor_data(self) -> dict:
        """
           reads the data from the SGP30 sensor via I2C.
           The method is synchronized to avoid two threads trying to communicate at the same time with the sensor.
           This is because we don't want a thread to get in the middle of a read started by a different thread and
           potentially corrupting the data/communication

           @return a dictionary containing the TVOC (key= TVOC) and
                   co2 (key= eCO2) values
        """
        with self.threadLock:
            read_time = time.time()

            if self.cache_expired(read_time):
                self.cached_sensor_data["TVOC"] = self.sgp30.TVOC
                self.cached_sensor_data["eCO2"] = self.sgp30.eCO2
                self.last_read = read_time

            self.save_iqa_baseline()

            return self.cached_sensor_data

    def cache_expired(self, read_time) -> bool:
        return (read_time - self.last_read > self.cache_time_to_live_seconds) or not self.cached_sensor_data

    def read_tvocs(self):
        return self.read_sensor_data()["TVOC"]

    def read_co2(self):
        return self.read_sensor_data()["eCO2"]

    def read_eco2_baseline(self):
        return self.sgp30.baseline_eCO2

    def read_tvoc_baseline(self):
        return self.sgp30.baseline_TVOC

    def save_iqa_baseline(self):
        """
        Every hour, the save the sensor baseline in a persistent storage so the next time the sensor is turned on
        it can use the baseline (to avoid having the sensor run for 12 hours)
        Restarting the sensor without reading back a previously stored baseline will result in the sensor
        trying to determine a new baseline.
        """
        current_time = time.time()

        if self.should_save_iaq_baseline(current_time):
            eco2_baseline = self.read_eco2_baseline()
            tvoc_baseline = self.read_tvoc_baseline()
            print("INFO saving new baseline = eco2: {}, tvocs:{}".format(eco2_baseline, tvoc_baseline))
            self.sgp30_repository.save_iaq_baseline(eco2_baseline, tvoc_baseline)
            self.baseline_last_save = current_time
            self.baseline_write_time_seconds = SGP30.BASELINE_DEFAULT_WRITE_TIME_SECONDS  # override the 12hour cadence
            self.set_temperature_and_humidity_compensation()

    def should_save_iaq_baseline(self, current_time) -> bool:
        return current_time - self.baseline_last_save > self.baseline_write_time_seconds
