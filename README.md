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

## Modus Operandi

Rather than following a strict "develop by atomic features" mandate, 
I used it more like a guideline, developing in an agile-like way.

I started by defining the API, as comprehensively as I could. 
Then I developed some basic features (e.g. getting a customer
with or without aggregated devices). Then I added more features,
and improved the API as needed. When I had a workable example
(or an MVP, in agile terms),
I did some end-to-end testing, and used the feedback to further
refine the API and the code. 

Even this README was written by starting with basic build/run 
instructions, then I added this more philosophical section.
