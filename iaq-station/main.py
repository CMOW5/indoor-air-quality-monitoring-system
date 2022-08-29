from typing import Tuple

from application.infrastructure.app.app import App
from application.infrastructure.app.argument_parser.argument_parser import ArgumentParser
from application.infrastructure.app.config.application_config import AppConfig
from application.infrastructure.app.config.application_config_factory import create_app_config
from application.infrastructure.app.dependency_container.dependency_container import DependencyContainer
from application.infrastructure.app.hardware_registry.hardware_registry_factory import create_hardware_registry
from application.infrastructure.app.sender_registry.sender_registry_factory import create_sender_registry


def create_config_and_dependency_container() -> Tuple[AppConfig, DependencyContainer]:
    parser = ArgumentParser()
    app_config = create_app_config(parser.properties_file)
    hardware_registry = create_hardware_registry(parser.hardware_registry_name)
    sender_registry = create_sender_registry(parser.sender_registry_name, app_config)
    container = DependencyContainer(hardware_registry, sender_registry)

    return app_config, container


config, dependency_container = create_config_and_dependency_container()
app = App(config, dependency_container)
app.setup()
app.loop_forever()

# python3 main.py --help

# fake sensors with local mosquitto
# python3 main.py local-mqtt generic-linux generic-local-pc

# fake sensors with aws-iot-core
# python3 main.py aws-iot-core generic-linux generic-pc

# raspberry-pi with real sensors with aws-iot-core
# python3 main.py aws-iot-core raspberry-pi raspberry-pi

# raspberry-pi with real sensors with blackhole
# python3 main.py aws-iot-core black-hole raspberry-pi

