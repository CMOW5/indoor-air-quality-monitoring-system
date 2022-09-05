from application.infrastructure.app.sender_registry.sender_registry import SenderRegistry
from application.infrastructure.app.config.application_config import AppConfig
from application.infrastructure.app.sender_registry.aws_iot_core.aws_iot_core_sender_registry import \
    AwsIotCoreSenderRegistry
from application.infrastructure.app.sender_registry.black_hole_sender.black_hole_sender_registry import \
    BlackHoleSenderRegistry
from application.infrastructure.app.sender_registry.local_mqtt.local_mqtt_sender_registry import LocalMqttSenderRegistry

AWS_IOT_CORE = 'aws-iot-core'
LOCAL_MQTT = 'local-mqtt'
BLACK_HOLE = 'black-hole'


def create_sender_registry(sender_registry_name: str, app_config: AppConfig) -> SenderRegistry:
    if sender_registry_name == AWS_IOT_CORE:
        print('LOADED AWS_IOT_CORE_SENDER')
        return AwsIotCoreSenderRegistry(app_config)
    elif sender_registry_name == LOCAL_MQTT:
        print('LOADED LOCAL_MQTT_SENDER')
        return LocalMqttSenderRegistry(app_config)
    elif sender_registry_name == BLACK_HOLE:
        print('LOADED BLACK_HOLE_MQTT_SENDER')
        return BlackHoleSenderRegistry(app_config)
