import json


class MqttDataDto:

    def __init__(self, station_id, value, timestamp):
        self.station_id = station_id
        self.value = value
        self.timestamp = timestamp

    def to_json(self):
        return {
            'stationId': self.station_id,
            'value': self.value,
            'timestamp': self.timestamp
        }

    def to_string(self):
        return json.dumps(self.to_json())
