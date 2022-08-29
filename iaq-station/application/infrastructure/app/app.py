from application.domain.co2.co2_sensor import CO2Sensor
from application.domain.humidity.humidity_sensor import HumiditySensor
from application.domain.pm10.pm10_sensor import PM10Sensor
from application.domain.pm25.pm25_sensor import PM25Sensor
from application.domain.temperature.temperature_sensor import TemperatureSensor
from application.domain.vocs.vocs_sensor import VOCsSensor
from application.infrastructure.app.config.application_config import AppConfig
from application.infrastructure.app.dependency_container.dependency_container import DependencyContainer
from application.infrastructure.app.sensor_process.sensor_process_factory import SensorProcessFactory
from application.infrastructure.queue.circular_priority_sensor_queue import CircularPrioritySensorQueue


class App:

    def __init__(self, app_config: AppConfig, dependency_container: DependencyContainer):
        self.app_config = app_config
        self.dependency_container = dependency_container
        self.hardware_registry = self.dependency_container.get_hardware_registry()
        self.sender_registry = self.dependency_container.get_sender_registry()

    def setup(self):
        print('setting up application...')
        self._setup_temperature()
        self._setup_humidity()
        self._setup_pm25()
        self._setup_pm10()
        self._setup_co2()
        self._setup_vocs()

    def _setup_temperature(self):
        sensor = TemperatureSensor(self.hardware_registry.get_temperature_hardware())
        sensor_queue = CircularPrioritySensorQueue()
        temperature_sensor_sender = self.sender_registry.get_mqtt_temp_sender()

        SensorProcessFactory(sensor, sensor_queue, temperature_sensor_sender,
                             self.app_config.temperature_producer_time,
                             self.app_config.temperature_consumer_time).create()

    def _setup_humidity(self):
        sensor = HumiditySensor(self.hardware_registry.get_humidity_hardware())
        sensor_queue = CircularPrioritySensorQueue()
        humidity_sensor_sender = self.sender_registry.get_mqtt_humidity_sender()

        SensorProcessFactory(sensor, sensor_queue, humidity_sensor_sender,
                             self.app_config.humidity_producer_time, self.app_config.humidity_consumer_time).create()

    def _setup_pm25(self):
        sensor = PM25Sensor(self.hardware_registry.get_pm25_hardware())
        sensor_queue = CircularPrioritySensorQueue()
        pm25_sensor_sender = self.sender_registry.get_mqtt_pm25_sender()

        SensorProcessFactory(sensor, sensor_queue, pm25_sensor_sender,
                             self.app_config.pm25_producer_time, self.app_config.pm25_consumer_time).create()

    def _setup_pm10(self):
        sensor = PM10Sensor(self.hardware_registry.get_pm10_hardware())
        sensor_queue = CircularPrioritySensorQueue()
        pm10_sensor_sender = self.sender_registry.get_mqtt_pm10_sender()

        SensorProcessFactory(sensor, sensor_queue, pm10_sensor_sender,
                             self.app_config.pm10_producer_time, self.app_config.pm10_consumer_time).create()

    def _setup_co2(self):
        sensor = CO2Sensor(self.hardware_registry.get_co2_hardware())
        sensor_queue = CircularPrioritySensorQueue()
        co2_sensor_sender = self.sender_registry.get_mqtt_co2_sender()

        SensorProcessFactory(sensor, sensor_queue, co2_sensor_sender,
                             self.app_config.co2_producer_time, self.app_config.co2_consumer_time).create()

    def _setup_vocs(self):
        sensor = VOCsSensor(self.hardware_registry.get_vocs_hardware())
        sensor_queue = CircularPrioritySensorQueue()
        vocs_sensor_sender = self.sender_registry.get_mqtt_vocs_sender()

        SensorProcessFactory(sensor, sensor_queue, vocs_sensor_sender,
                             self.app_config.vocs_producer_time, self.app_config.vocs_consumer_time).create()

    def loop_forever(self):
        while True:
            pass
