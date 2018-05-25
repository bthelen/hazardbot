#!/usr/bin/env bash

set -xe

cd hazardbot-source
./gradlew test assemble
