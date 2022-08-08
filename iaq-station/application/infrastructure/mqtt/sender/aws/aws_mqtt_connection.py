from awscrt import mqtt
from awsiot import mqtt_connection_builder
from concurrent.futures import Future
from application.infrastructure.mqtt.config.mqtt_config import MqttConfig
from application.utils.singleton.thread_safe_singleton import ThreadSafeSingleton


class AwsMqttConnection(metaclass=ThreadSafeSingleton):
    """
    A not so great way of making this class a Singleton (this is not thread safe).
    AWS only accepts a single mqtt connection per client-id, so we need a way to guarantee this.
    Another option is to have a pool of mqtt connections (with just one in this case) from which
    we can borrow a single connection
    """
    def __init__(self, mqtt_config: MqttConfig):
        self.mqtt_config = mqtt_config
        self.mqtt_connection = mqtt_connection_builder.mtls_from_path(
            endpoint=self.mqtt_config.host,
            port=self.mqtt_config.port,
            cert_filepath=self.mqtt_config.cert_filepath,
            pri_key_filepath=self.mqtt_config.private_key_filepath,
            ca_filepath=self.mqtt_config.ca_filepath,
            on_connection_interrupted=self.on_connection_interrupted,
            on_connection_resumed=self.on_connection_resumed,
            client_id=self.mqtt_config.station_id,
            clean_session=False,
            keep_alive_secs=mqtt_config.keepalive,
            http_proxy_options=None)

        print("Connecting to {} with client ID '{}'...".format(
            self.mqtt_config.host, self.mqtt_config.station_id))

        connect_future: Future = self.mqtt_connection.connect()
        connect_future.result()

    def get_mqtt_connection(self):
        return self.mqtt_connection

    # Callback when connection is accidentally lost.
    def on_connection_interrupted(self, connection, error, **kwargs):
        print("Connection interrupted. error: {}".format(error))

    # Callback when an interrupted connection is re-established.
    def on_connection_resumed(self, connection, return_code, session_present, **kwargs):
        print("Connection resumed. return_code: {} session_present: {}".format(return_code, session_present))

        if return_code == mqtt.ConnectReturnCode.ACCEPTED and not session_present:
            print("Session did not persist. Resubscribing to existing topics...")
            resubscribe_future, _ = connection.resubscribe_existing_topics()

            # Cannot synchronously wait for resubscribe result because we're on the connection's event-loop thread,
            # evaluate result with a callback instead.
            # resubscribe_future.add_done_callback(on_resubscribe_complete)
