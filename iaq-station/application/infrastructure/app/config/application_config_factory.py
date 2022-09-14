import os
import logging
from application.infrastructure.app.config.application_config import AppConfig
from definitions import APPLICATION_RESOURCES_DIR

PROPERTIES_FILE_NAME_FORMAT = "application.config.{config_file}.yml"


def create_app_config(properties_file: str) -> AppConfig:
    properties_file_name = PROPERTIES_FILE_NAME_FORMAT.format(config_file=properties_file)
    properties_file_path = os.path.join(APPLICATION_RESOURCES_DIR, properties_file_name)
    logging.info('LOADED CONFIG FILE %s', properties_file_path)
    return AppConfig(properties_file_path)
