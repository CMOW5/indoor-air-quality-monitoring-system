import yaml
import os
import logging
from definitions import CERT_DIR


class AppConfig:
    """
      A class that holds the config values for this application like the station id,
      the mqtt endpoint, sensor configurations, etc.

      it reads the provided yml file and creates a config instance holding the values defined in the yml file
    """

    def __init__(self, filename):
        with open(filename, "r") as stream:
            try:
                self.configs = yaml.safe_load(stream)
            except yaml.YAMLError as exception:
                logging.exception("could not load the configuration file. Exception = %s", exception)
                raise exception

    @property
    def station_id(self):
        return self.configs['station']['id']

    @property
    def mqtt_host(self):
        return self.configs['mqtt']['host']

    @property
    def mqtt_port(self):
        return self.configs['mqtt']['port']

    @property
    def mqtt_keepalive(self):
        return self.configs['mqtt']['keepalive']

    @property
    def mqtt_cert_filepath(self):
        return os.path.join(CERT_DIR, self.configs['mqtt']['cert_filepath'])

    @property
    def mqtt_private_key_filepath(self):
        return os.path.join(CERT_DIR, self.configs['mqtt']['private_key_filepath'])

    @property
    def mqtt_ca_filepath(self):
        return os.path.join(CERT_DIR, self.configs['mqtt']['ca_filepath'])

    @property
    def temperature_mqtt_topic(self):
        return self.configs['temperature']['mqtt_topic']

    @property
    def temperature_producer_time(self):
        return self.configs['temperature']['producer_time']

    @property
    def temperature_consumer_time(self):
        return self.configs['temperature']['consumer_time']

    @property
    def humidity_mqtt_topic(self):
        return self.configs['humidity']['mqtt_topic']

    @property
    def humidity_producer_time(self):
        return self.configs['humidity']['producer_time']

    @property
    def humidity_consumer_time(self):
        return self.configs['humidity']['consumer_time']

    @property
    def pm25_mqtt_topic(self):
        return self.configs['pm25']['mqtt_topic']

    @property
    def pm25_producer_time(self):
        return self.configs['pm25']['producer_time']

    @property
    def pm25_consumer_time(self):
        return self.configs['pm25']['consumer_time']

    @property
    def pm10_mqtt_topic(self):
        return self.configs['pm10']['mqtt_topic']

    @property
    def pm10_producer_time(self):
        return self.configs['pm10']['producer_time']

    @property
    def pm10_consumer_time(self):
        return self.configs['pm10']['consumer_time']

    @property
    def co2_mqtt_topic(self):
        return self.configs['co2']['mqtt_topic']

    @property
    def co2_producer_time(self):
        return self.configs['co2']['producer_time']

    @property
    def co2_consumer_time(self):
        return self.configs['co2']['consumer_time']

    @property
    def vocs_mqtt_topic(self):
        return self.configs['vocs']['mqtt_topic']

    @property
    def vocs_producer_time(self):
        return self.configs['vocs']['producer_time']

    @property
    def vocs_consumer_time(self):
        return self.configs['vocs']['consumer_time']
