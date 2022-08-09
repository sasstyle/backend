#!/usr/bin/env python
"""Django's command-line utility for administrative tasks."""
import os
import sys

import django.core.management.commands.runserver as runserver
from django.core.management.commands.runserver import Command as runserver
import py_eureka_client.eureka_client as eureka_client

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

if __name__ == '__main__':
    port = 9002
    eureka_client.init(eureka_server="http://discovery-service:8761/eureka",
                   app_name="fitting-service",
                   eureka_context="/fitting-service"
                   instance_port=port)

    main()
