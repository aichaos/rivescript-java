#!/bin/bash

set -e
set +x

# Do not deploy archives when building pull request
if [ "$TRAVIS_BRANCH" != "master" ] || [ "$TRAVIS_PULL_REQUEST" == "true" ]; then
  exit 0
fi

# Deploy jar artifacts to Sonatype OSSRH

./gradlew -Psigning.keyId="$SIGNING_KEY" -Psigning.password="$SIGNING_PASSWORD" -Psigning.secretKeyRingFile="${TRAVIS_BUILD_DIR}/secring.gpg" uploadArchives
