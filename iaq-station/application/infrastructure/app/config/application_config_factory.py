from application.infrastructure.app.config.application_config import AppConfig

PROPERTIES_FILE_PATH_FORMAT = "application/resources/application.config.{config_file}.yml"


def create_app_config(properties_file: str) -> AppConfig:
    properties_file_path = PROPERTIES_FILE_PATH_FORMAT.format(config_file=properties_file)
    print('LOADED CONFIG FILE ', properties_file_path)
    return AppConfig(properties_file_path)
