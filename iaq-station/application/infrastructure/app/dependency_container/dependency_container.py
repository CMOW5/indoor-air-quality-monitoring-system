from application.infrastructure.app.hardware_registry.hardware_registry import HardwareRegistry
from application.infrastructure.app.sender_registry.sender_registry import SenderRegistry


class DependencyContainer:
    """
      A container with some of our dependencies. This is a very simplistic container to try the Dependency Injection
      principle. This is so we can change the implementation for different interfaces during the program execution
    """

    def __init__(self, hardware_registry: HardwareRegistry, sender_registry: SenderRegistry):
        self.hardware_registry = hardware_registry
        self.sender_registry = sender_registry

    def get_hardware_registry(self) -> HardwareRegistry:
        return self.hardware_registry

    def get_sender_registry(self) -> SenderRegistry:
        return self.sender_registry
