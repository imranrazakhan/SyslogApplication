# Syslog Example with Apache Camel and Spring Boot

This example shows how to work with a simple Apache Camel application for Syslog using Spring Boot.

The example generates messages using timer trigger, writes them to standard output.

## How to run

You can run this example using

    mvn spring-boot:run

## To get health check

To show a summary of spring boot health check

```
curl -XGET -s http://localhost:8080/actuator/health
```

## To get info about the routes

To show a summary of all the routes

```
curl -XGET -s http://localhost:8080/actuator/camelroutes
```

To show detailed information for a specific route

```
curl -XGET -s http://localhost:8080/actuator/camelroutes/{id}/detail
```
