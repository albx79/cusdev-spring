#!/usr/bin/env sh

http --ignore-stdin post http://webapp:8080/customers name=alberto surname=colombo taxCode=asdfgh123x123x
http --ignore-stdin post http://webapp:8080/devices status=ACTIVE customer:=1
http --ignore-stdin put http://webapp:8080/customers/1/address address='via della spiga'
http --ignore-stdin get http://webapp:8080/customers/1?aggregateDevices=true
