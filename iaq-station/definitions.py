import os

# The Project Root file
ROOT_DIR = os.path.dirname(os.path.abspath(__file__))
CERT_DIR = os.path.join(ROOT_DIR, 'certs')
APPLICATION_DIR = os.path.join(ROOT_DIR, 'application')
APPLICATION_RESOURCES_DIR = os.path.join(APPLICATION_DIR, 'resources')
IAQ_REPO_DIR = os.path.join(ROOT_DIR, 'iaq_repo')
