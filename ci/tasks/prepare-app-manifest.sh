#!/bin/bash

set -xe

cat ./current-app-info/current-app.txt

pushd build-artifacts
tar zxvf $APP_NAME-*.tgz

sed "s/$APP_NAME-$PWS_APP_SUFFIX/$APPNAME-$PWS_APP_SUFFIX-$(cat ./current-app-info/next-app.txt)/" ./manifests/manifest.yml > ./manifests/manifest-bg.yml
cp ./manifests/manifest-bg.yml ../app-manifest-output/
cp ./build/libs/$APP_NAME-*.jar ../app-manifest-output/
popd

cat ./app-manifest-output/manifest-bg.yml
