# Introduction
This application provides price infomration to client and downstream systems as 
submitted by different vendors for traded instruments.

## High level design and Context
This application has 3 main high level component.

### 1. Price Processor

![](docs/PriceProcessor.png)

### 2. Price Cleaner

![](docs/PriceCleaner.png)

### 3. Price Rest API

![](docs/PriceRestAPI.png)

## Challenges

## Domain model

![](docs/PriceDataModel.png)

## Class Diagram

![](docs/PriceClassDiagram.png)

## Sequence Diagram

![](docs/PriceSequenceDiagram.png)

## Interfaces

### 1. Channels

### 2. Rest API

Camel’s REST DSL to expose a RESTful API that performs CRUD operations
on a database.

It generates orders for books referenced in database at a regular pace.
Orders are processed asynchronously by another Camel route. Books
available in database as well as the status of the generated orders can
be retrieved via the REST API.
### 3. Swagger API

It relies on Swagger to expose the API documentation of the REST
service.

## Running the Project / Demo

### Pre-requisite

* maven 3.3.x
* java 1.8

### Build

You can build this application using:

```bash
    mvn package
```

### Run

You can run this application with Maven using:

```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Alternatively, you can also run this application using the executable JAR:

```bash
$ java -jar -Dspring.profiles.active=dev target/camel-example-spring-boot-rest-jpa-${project.version}.jar
```
This app has embedded tomcat server. In order to run this website execute below maven command
mvn spring-boot:run

It will start the embedded tomcat server on default port 8080
This uses an embedded in-memory HSQLDB database. You can use the default
Spring Boot profile in case you have a MySQL server available for you to
test.

When the Camel application runs, you should see the following messages
being logged to the console, e.g.:

....
2016-09-02 09:54:29.702  INFO 27253 --- [mer://new-order] generate-order : Inserted new order 1
2016-09-02 09:54:31.597  INFO 27253 --- [mer://new-order] generate-order : Inserted new order 2
2016-09-02 09:54:33.596  INFO 27253 --- [mer://new-order] generate-order : Inserted new order 3
2016-09-02 09:54:34.637  INFO 27253 --- [rts.camel.Order] process-order  : Processed order #id 1 with 7 copies of the «Camel in Action» book
2016-09-02 09:54:34.641  INFO 27253 --- [rts.camel.Order] process-order  : Processed order #id 2 with 4 copies of the «Camel in Action» book
2016-09-02 09:54:34.645  INFO 27253 --- [rts.camel.Order] process-order  : Processed order #id 3 with 1 copies of the «ActiveMQ in Action» book
2016-09-02 09:54:35.597  INFO 27253 --- [mer://new-order] generate-order : Inserted new order 4
2016-09-02 09:54:37.601  INFO 27253 --- [mer://new-order] generate-order : Inserted new order 5
2016-09-02 09:54:39.605  INFO 27253 --- [mer://new-order] generate-order : Inserted new order 6
2016-09-02 09:54:39.668  INFO 27253 --- [rts.camel.Order] process-order  : Processed order #id 4 with 7 copies of the «Camel in Action» book
2016-09-02 09:54:39.671  INFO 27253 --- [rts.camel.Order] process-order  : Processed order #id 5 with 1 copies of the «ActiveMQ in Action» book
2016-09-02 09:54:39.674  INFO 27253 --- [rts.camel.Order] process-order  : Processed order #id 6 with 4 copies of the «Camel in Action» book
....

# How to Use
You can then access the REST API directly from your Web browser, e.g.:

* http://localhost:8080/camel-rest-jpa/books
* http://localhost:8080/camel-rest-jpa/books/order/1

The Camel application can be stopped pressing ctrl+c in the shell.

### Swagger API

The example provides API documentation of the service using Swagger
using the _context-path_ `+camel-rest-jpa/api-doc+`. You can access the
API documentation from your Web browser at
http://localhost:8080/camel-rest-jpa/api-doc.


### Technologies used
- Spring Core
- Spring MVC
- RestTemplate
- Spring Boot
- Maven
- Html/thymeleaf
- Mockito
- Junit
- Embedded Tomcat Server
- MockMvc
- TDD etc...
