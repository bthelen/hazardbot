#!/bin/bash

set -xe

pwd

cf api $PWS_API --skip-ssl-validation

set +x
cf login -u $PWS_USER -p $PWS_PWD -o "$PWS_ORG" -s "$PWS_SPACE"
set -x

cf apps

set +e
cf apps | grep "$APP_NAME-$PWS_APP_SUFFIX.$PWS_APP_DOMAIN" | grep green
if [ $? -eq 0 ]
then
  echo "green" > ./current-app-info/current-app.txt
  echo "blue" > ./current-app-info/next-app.txt
else
  echo "blue" > ./current-app-info/current-app.txt
  echo "green" > ./current-app-info/next-app.txt
fi
set -xe

echo "Current main app routes to app instance $(cat ./current-app-info/current-app.txt)"
echo "New version of app to be deployed to instance $(cat ./current-app-info/next-app.txt)"
