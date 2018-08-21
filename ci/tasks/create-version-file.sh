#!/usr/bin/env bash

set -xe

mkdir version-file

readonly VERSION=$(date +%Y%m%d%H%M%S)

echo $VERSION > version-file/version
