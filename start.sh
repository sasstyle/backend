#/bin/bash

echo [+] Creating backend docker container

mkdir -p ./mysql/data
sudo docker-compose up -d

echo [+] Done!
