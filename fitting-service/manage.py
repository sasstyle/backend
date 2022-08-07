#!/usr/bin/env python
"""Django's command-line utility for administrative tasks."""
from dotenv import load_dotenv
import os
import sys

import django.core.management.commands.runserver as runserver
from django.core.management.commands.runserver import Command as runserver
import random
import py_eureka_client.eureka_client as eureka_client

load_dotenv()

PORT = random.randint(1024, 65535)

def main():
    """Run administrative tasks."""
    os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'config.settings')
    try:
        from django.core.management import execute_from_command_line
    except ImportError as exc:
        raise ImportError(
            "Couldn't import Django. Are you sure it's installed and "
            "available on your PYTHONPATH environment variable? Did you "
            "forget to activate a virtual environment?"
        ) from exc
    execute_from_command_line(sys.argv)

def get_eureka_server():
    profiles = os.environ.get('PROFILES')

    if profiles == 'LOCAL': 
        return "http://localhost:8761/eureka" 
    else:
        return "http://config-service:8761/eureka"


if __name__ == '__main__':
    runserver.default_port = PORT

    eureka_client.init(eureka_server=get_eureka_server(),
                    app_name="fitting-service",
                    eureka_context="/fitting-service",
                    instance_host="localhost",
                    instance_port=PORT)

    main()
