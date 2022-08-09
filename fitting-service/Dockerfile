FROM python:3.9.13-slim

ENV PYTHONUNBUFFERED 1

RUN mkdir /code

WORKDIR /code

COPY start.sh /start.sh
COPY requirements.txt /code/

RUN apt update && apt install -y libsm6 libxext6 ffmpeg libfontconfig1 libxrender1 libgl1-mesa-glx build-essential

RUN sudo apt-get install build-essential

RUN pip install --upgrade pip

RUN pip install -r requirements.txt

COPY . /code/

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ENTRYPOINT ["/wait-for-it.sh", "gateway-service:8000", "-t", "20", "--", "sh", "/start.sh"]

