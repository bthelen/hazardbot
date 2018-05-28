#!/bin/bash

set -xe

cat ./current-app-info/current-app.txt

sed "s/$APPNAME-$PWS_APP_SUFFIX/$APPNAME-$PWS_APP_SUFFIX-$(cat ./current-app-info/next-app.txt)/" ./build-artifacts/manifest.yml > ./build-artifacts/manifest-bg.yml

cat ./build-artifacts/manifest-bg.yml
