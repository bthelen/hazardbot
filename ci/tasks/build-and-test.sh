#!/usr/bin/env bash

set -xe

cd hazardbot-source
./gradlew test assemble

readonly VERSION=$(date +%Y%m%d%H%M%S)

tar zcvf hazardbot-$VERSION.tgz build/libs/*.jar manifests/manifest.yml

cp hazardbot-$VERSION.tgz ../hazardbot-build-artifacts/
