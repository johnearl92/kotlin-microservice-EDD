#!/bin/bash

docker build -t ktor-order-service:latest .
helm install order-service ./helm
