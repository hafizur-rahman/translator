FROM ubuntu:16.04

RUN apt-get update \
  && apt-get install -y python3 python3-pip curl \
  && apt-get clean \
  && rm -rf /var/lib/apt/lists/*

COPY . /opt/front
WORKDIR /opt/front
RUN pip3 install -r requirements.txt

ENV LANG C.UTF-8

CMD ["python3", "app.py"]
