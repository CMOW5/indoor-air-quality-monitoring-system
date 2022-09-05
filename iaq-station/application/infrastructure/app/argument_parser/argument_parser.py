import argparse


class ArgumentParser:
    """
      A class the parse the command line arguments to configure the application. This give us the flexibility to
      change the implementations for several interfaces like Hardware, SensorSenders, etc.
      For example, if you want the program to use a local mqtt connection with a broker like mosquitto instead of
      aws-iot-core, just pass in the right argument and the program should bootstrap the right implementation for you

      example:
      python3 main.py --help
      python3 main.py local-mqtt generic-linux generic-local-pc
    """

    def __init__(self):
        self.arg_parser = argparse.ArgumentParser(
            usage="%(prog)s [OPTION] [FILE]...",
            description="Project description. python3 main.py local-mqtt generic-linux generic-local-pc")

        self.arg_parser.add_argument('sender_registry_name',
                                     metavar='sender_registry_name',
                                     type=str,
                                     help='the sender_registry_name. The value can be: aws-iot-core, '
                                          'local-mqtt, black-hole')

        self.arg_parser.add_argument('hardware_registry_name',
                                     metavar='hardware_registry_name',
                                     type=str,
                                     help='the hardware registry name. The value can be: raspberry-pi, generic-linux')

        self.arg_parser.add_argument('properties_file',
                                     metavar='properties_file',
                                     type=str,
                                     help='the properties file profile to use under '
                                          'application/resources/application.config.{profile}.yml. ' 
                                          'Example: raspberry-pi, generic-pc, generic-local-pc')

        self.args = self.arg_parser.parse_args()

    @property
    def properties_file(self):
        return self.args.properties_file

    @property
    def hardware_registry_name(self):
        return self.args.hardware_registry_name

    @property
    def sender_registry_name(self):
        return self.args.sender_registry_name
