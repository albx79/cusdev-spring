#!/usr/bin/env sh

curl --fail --silent http://localhost:8080/actuator/health 2>&1 | grep UP || exit 1
