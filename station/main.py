from typing import Tuple

from application.infrastructure.app.app import App
from application.infrastructure.app.config.application_config import AppConfig
from application.infrastructure.app.hardware_registry.generic_linux.generic_linux_hardware_registry import \
    GenericLinuxHardwareRegistry
from application.infrastructure.app.hardware_registry.hardware_registry import HardwareRegistry


def bootstrap_hardware_registry_and_config() -> Tuple[AppConfig, HardwareRegistry]:
    try:
        # let's try to bootstrap a raspberry pi hardware
        from application.infrastructure.app.hardware_registry.raspberry_pi.raspberry_pi_hardware_registry import \
            RaspberryPiHardwareRegistry
        print('LOADED RASPBERRY_PI_HARDWARE')
        return AppConfig("application/resources/application.config.raspberrypi.yml"), RaspberryPiHardwareRegistry()
    except NotImplementedError:
        # the application is likely not running on a raspberry pi, so let's bootstrap a generic linux hardware.
        # This is, a bunch of fake (software) sensors
        print('LOADED GENERIC_LINUX_HARDWARE')
        return AppConfig("application/resources/application.config.generic-pc.yml"), GenericLinuxHardwareRegistry()


config, hardware_registry = bootstrap_hardware_registry_and_config()
app = App(config, hardware_registry)
app.setup()
app.loop_forever()
