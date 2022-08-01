import time

from application.domain.sensor.consumer.sensor_consumer_scheduler import SensorConsumerScheduler
from application.domain.sensor.consumer.sensor_consumer import SensorConsumer
from application.domain.sensor.producer.sensor_producer import SensorProducer
from application.domain.sensor.producer.sensor_producer_scheduler import SensorProducerScheduler
from application.domain.temperature.temperature_sensor import TemperatureSensor
from application.infrastructure.hardware.bme280_sensor.fake_bme280_hardware import FakeBME280
from application.infrastructure.mqtt.sender.fake_mqtt_sender import FakeMqttSender
from application.infrastructure.queue.circular_sensor_queue import CircularSensorQueue

queue = CircularSensorQueue()
bme280 = FakeBME280()
temp_sensor = TemperatureSensor(bme280)

sensor_producer = SensorProducer(temp_sensor, queue)
sensor_producer_scheduler = SensorProducerScheduler(sensor_producer)
sensor_producer_scheduler.schedule(2)

sensor_consumer = SensorConsumer(queue, FakeMqttSender())
sensor_consumer_scheduler = SensorConsumerScheduler(sensor_consumer)
sensor_consumer_scheduler.schedule(1)


"""
while True:
    val = temp_sensor.read()
    print('value from sensor = ', val.to_string())
    time.sleep(1)
"""