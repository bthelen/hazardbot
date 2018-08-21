#!/usr/bin/env bash

set -xe

readonly VERSION=$(date +%Y%m%d%H%M%S)

echo $VERSION > version-file/version
