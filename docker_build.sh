#!/bin/bash

docker buildx create --name multiarch --use

docker buildx build \
  --platform linux/amd64,linux/arm64 \
  -t lcc1024/kafka_ui_lcc:1.0 \
  --push \
  .
