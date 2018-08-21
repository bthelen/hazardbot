#!/usr/bin/env bash

set -xe

pushd hazardbot-source
readonly SHA_TO_TAG=$(git rev-parse HEAD)
popd

pushd hazardbot-source-head
echo "git tag -a v1.2 $SHA_TO_TAG"
echo "test"
popd
