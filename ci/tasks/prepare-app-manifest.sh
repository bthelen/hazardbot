#!/bin/bash

set -xe

cat ./current-app-info/current-app.txt

pushd build-artifacts
tar zxvf $APP_NAME-*.tgz

sed "s/$APP_NAME-$PWS_APP_SUFFIX/$APPNAME-$PWS_APP_SUFFIX-$(cat ./current-app-info/next-app.txt)/" ./manifests/manifest.yml > ./manifests/manifest-bg.yml
popd

cat ./build-artifacts/manifests/manifest-bg.yml
