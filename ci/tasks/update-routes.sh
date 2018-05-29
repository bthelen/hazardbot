#!/bin/bash

set -xe

pwd
env

cf api $PWS_API --skip-ssl-validation

cf login -u $PWS_USER -p $PWS_PWD -o "$PWS_ORG" -s "$PWS_SPACE"

cf apps

cf routes

export PWS_DOMAIN_NAME=$PWS_APP_DOMAIN
export MAIN_ROUTE_HOSTNAME=$APP_NAME-$PWS_APP_SUFFIX

export NEXT_APP_COLOR=$(cat ./current-app-info/next-app.txt)
export NEXT_APP_HOSTNAME=$APP_NAME-$PWS_APP_SUFFIX-$NEXT_APP_COLOR

export CURRENT_APP_COLOR=$(cat ./current-app-info/current-app.txt)
export CURRENT_APP_HOSTNAME=$APP_NAME-$PWS_APP_SUFFIX-$CURRENT_APP_COLOR

echo "Mapping main app route to point to $NEXT_APP_HOSTNAME instance"
cf map-route $NEXT_APP_HOSTNAME $PWS_DOMAIN_NAME --hostname $MAIN_ROUTE_HOSTNAME

cf routes

set +e
echo "Removing previous main app route that pointed to $CURRENT_APP_HOSTNAME instance"
cf unmap-route $CURRENT_APP_HOSTNAME $PWS_DOMAIN_NAME --hostname $MAIN_ROUTE_HOSTNAME
echo "Unmapping the default route on the new app, so it only responds to the default route"
cf unmap-route $NEXT_APP_HOSTNAME $PWS_DOMAIN_NAME --hostname $NEXT_APP_HOSTNAME
echo "Cleanup by deleting old $CURRENT_APP_HOSTNAME deployment"
cf delete -f $CURRENT_APP_HOSTNAME
set -e

echo "Routes updated"

cf routes
