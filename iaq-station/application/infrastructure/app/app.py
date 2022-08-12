from application.domain.co2.co2_sensor import CO2Sensor
from application.domain.humidity.humidity_sensor import HumiditySensor
from application.domain.pm10.pm10_sensor import PM10Sensor
from application.domain.pm25.pm25_sensor import PM25Sensor
from application.domain.temperature.temperature_sensor import TemperatureSensor
from application.domain.vocs.vocs_sensor import VOCsSensor
from application.infrastructure.app.config.application_config import AppConfig
from application.infrastructure.app.hardware_registry.hardware_registry import HardwareRegistry
from application.infrastructure.app.sensor_process.sensor_process_factory import SensorProcessFactory
from application.infrastructure.mqtt.config.mqtt_config import MqttConfig
from application.infrastructure.mqtt.sender.aws.aws_mqtt_sender import AwsMqttSender
from application.infrastructure.queue.circular_sensor_queue import CircularSensorQueue


class App:

    def __init__(self, app_config: AppConfig, hardware_registry: HardwareRegistry):
        self.app_config = app_config
        self.hardware_registry = hardware_registry

    def setup(self):
        print('setting up application...')
        self._setup_temperature()
        #self._setup_humidity()
        #self._setup_pm25()
        #self._setup_pm10()
        #self._setup_co2()
        #self._setup_vocs()

    def _setup_temperature(self):
        sensor = TemperatureSensor(self.hardware_registry.get_temperature_hardware())
        sensor_queue = CircularSensorQueue()
        temperature_mqtt_config = MqttConfig(app_config=self.app_config, topic=self.app_config.temperature_mqtt_topic)
        #temperature_sensor_sender = FakeMqttSender(temperature_mqtt_config)
        #temperature_sensor_sender = MqttSender(temperature_mqtt_config)
        temperature_sensor_sender = AwsMqttSender(temperature_mqtt_config)

        SensorProcessFactory(sensor, sensor_queue, temperature_sensor_sender,
                             self.app_config.temperature_producer_time,
                             self.app_config.temperature_consumer_time).create()

    def _setup_humidity(self):
        sensor = HumiditySensor(self.hardware_registry.get_humidity_hardware())
        sensor_queue = CircularSensorQueue()
        humidity_mqtt_config = MqttConfig(app_config=self.app_config, topic=self.app_config.humidity_mqtt_topic)
        #humidity_sensor_sender = FakeMqttSender(humidity_mqtt_config)
        #humidity_sensor_sender = MqttSender(humidity_mqtt_config)
        humidity_sensor_sender = AwsMqttSender(humidity_mqtt_config)

        SensorProcessFactory(sensor, sensor_queue, humidity_sensor_sender,
                             self.app_config.humidity_producer_time, self.app_config.humidity_consumer_time).create()

    def _setup_pm25(self):
        sensor = PM25Sensor(self.hardware_registry.get_pm25_hardware())
        sensor_queue = CircularSensorQueue()
        pm25_mqtt_config = MqttConfig(app_config=self.app_config, topic=self.app_config.pm25_mqtt_topic)
        #pm25_sensor_sender = FakeMqttSender(pm25_mqtt_config)
        #pm25_sensor_sender = MqttSender(pm25_mqtt_config)
        pm25_sensor_sender = AwsMqttSender(pm25_mqtt_config)

        SensorProcessFactory(sensor, sensor_queue, pm25_sensor_sender,
                             self.app_config.pm25_producer_time, self.app_config.pm25_consumer_time).create()

    def _setup_pm10(self):
        sensor = PM10Sensor(self.hardware_registry.get_pm10_hardware())
        sensor_queue = CircularSensorQueue()
        pm10_mqtt_config = MqttConfig(app_config=self.app_config, topic=self.app_config.pm10_mqtt_topic)
        #pm10_sensor_sender = FakeMqttSender(pm10_mqtt_config)
        #pm10_sensor_sender = MqttSender(pm10_mqtt_config)
        pm10_sensor_sender = AwsMqttSender(pm10_mqtt_config)

        SensorProcessFactory(sensor, sensor_queue, pm10_sensor_sender,
                             self.app_config.pm10_producer_time, self.app_config.pm10_consumer_time).create()

    def _setup_co2(self):
        sensor = CO2Sensor(self.hardware_registry.get_co2_hardware())
        sensor_queue = CircularSensorQueue()
        co2_mqtt_config = MqttConfig(app_config=self.app_config, topic=self.app_config.co2_mqtt_topic)
        #co2_sensor_sender = FakeMqttSender(co2_mqtt_config)
        #co2_sensor_sender = MqttSender(co2_mqtt_config)
        co2_sensor_sender = AwsMqttSender(co2_mqtt_config)

        SensorProcessFactory(sensor, sensor_queue, co2_sensor_sender,
                             self.app_config.co2_producer_time, self.app_config.co2_consumer_time).create()

    def _setup_vocs(self):
        sensor = VOCsSensor(self.hardware_registry.get_vocs_hardware())
        sensor_queue = CircularSensorQueue()
        vocs_mqtt_config = MqttConfig(app_config=self.app_config, topic=self.app_config.vocs_mqtt_topic)
        #vocs_sensor_sender = FakeMqttSender(vocs_mqtt_config)
        #vocs_sensor_sender = MqttSender(vocs_mqtt_config)
        vocs_sensor_sender = AwsMqttSender(vocs_mqtt_config)

        SensorProcessFactory(sensor, sensor_queue, vocs_sensor_sender,
                             self.app_config.vocs_producer_time, self.app_config.vocs_consumer_time).create()

    def loop_forever(self):
        while True:
            pass
