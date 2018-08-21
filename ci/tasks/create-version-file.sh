#!/usr/bin/env bash

set -xe

cd hazardbot-source

readonly VERSION=$(date +%Y%m%d%H%M%S)

echo $VERSION > version
