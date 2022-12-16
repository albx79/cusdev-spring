# Telepass Customers and Devices

A simple REST API to manage customers and their devices.

## Building and Running

### With docker-compose

If you have docker-compose, then 

    docker-compose build
    docker-compose up

will do everything you need. The REST server is on port 8080.

This will also start a test container that creates a customer
and associates a device to it.

### With Maven

Otherwise, if you have Maven and Java 17 installed, you can compile
and run with 

    mvn spring-boot:run

which will also listen on port 8080.

## The API

See the [OpenAPI spec](./src/main/resources/api.yaml).
