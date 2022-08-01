from typing import Tuple

from application.infrastructure.app.app import App
from application.infrastructure.app.config.application_config import AppConfig
from application.infrastructure.app.hardware_registry.generic_linux.generic_linux_hardware_registry import \
    GenericLinuxHardwareRegistry
from application.infrastructure.app.hardware_registry.hardware_registry import HardwareRegistry


def bootstrap_hardware_registry_and_config() -> Tuple[AppConfig, HardwareRegistry]:

    print('LOADED GENERIC_LINUX_HARDWARE')
    return AppConfig("application/resources/application.config.generic-pc.yml"), GenericLinuxHardwareRegistry()


config, hardware_registry = bootstrap_hardware_registry_and_config()
app = App(config, hardware_registry)
app.setup()
app.loop_forever()
