from application.infrastructure.app.hardware_registry.generic_linux.generic_linux_hardware_registry import \
    GenericLinuxHardwareRegistry
from application.infrastructure.app.hardware_registry.hardware_registry import HardwareRegistry

RASPBERRY_PI = 'raspberry-pi'
GENERIC_LINUX = 'generic-linux'


def create_hardware_registry(hardware_registry_name: str) -> HardwareRegistry:
    if hardware_registry_name == RASPBERRY_PI:
        from application.infrastructure.app.hardware_registry.raspberry_pi.raspberry_pi_hardware_registry import \
            RaspberryPiHardwareRegistry
        print('LOADED RASPBERRY_PI_HARDWARE')
        return RaspberryPiHardwareRegistry()
    elif hardware_registry_name == GENERIC_LINUX:
        print('LOADED GENERIC_LINUX_HARDWARE')
        return GenericLinuxHardwareRegistry()
