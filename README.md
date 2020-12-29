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
Camel’s REST DSL to expose a RESTful API that performs CRUD operations
on a database.

It generates orders for books referenced in database at a regular pace.
Orders are processed asynchronously by another Camel route. Books
available in database as well as the status of the generated orders can
be retrieved via the REST API.
![](docs/PriceRestAPI.png)

## Challenges

## Domain model

![](docs/PriceDataModel.png)

## Class Diagram

![](docs/PriceClassDiagram.png)

## Sequence Diagram

![](docs/PriceSequenceDiagram.png)

## Interfaces

### 1. Message Endpoints
This app exposes following endpoints to interface it with external services.

1. Input Endpoint: It is used by different vendors to send price information. It can be configured through the `priceservice.queue.input.uri` property. E.g. jms:queue:input
2. Dead Endpoint:  It is used to redirect all errors for further consideration and analysis. It can be configured through the `priceservice.queue.dead.uripriceservice.topic.downstream.uri` property. E.g. jms:queue:deadletters
3. Recursive Endpoint: It is used to execute regular task. It can be configured through the `priceservice.quartz.delete.uri` property. E.g. quartz://priceServiceGroup/deleteInvalidPrice?cron=0+0+0+*+*+?
4. Output Endpoint: It is used to distribute prices to interested downstream systems. It can be configured through the `priceservice.queue.dead.uri` property. E.g. jms:topic:downstream

### 2. Rest API
The service exposes following rest api to allow clients to publish and retrieve data from the store.

1. Creat Price: To publish new price for specific vendor and instrument.
    http://localhost:8080/mizuho-price-service/prices/create
2. Get price by Vendor : To get all prices from a particular vendor.
    http://localhost:8080/mizuho-price-service/prices/vendor/:vendor
3. Get price by Vendor : To get all prices for a single instrument from various vendors. 
    http://localhost:8080/mizuho-price-service/prices/instrument/:instrument

### 3. Swagger API
The app provides API documentation of the service using Swagger using the _context-path_ `+mizuho-price-service/api-doc+`. 
You can access the API documentation from your Web browser at
    http://localhost:8080/mizuho-price-service/api-doc

## Running the Project / Demo

### Pre-requisite

* maven 3.3.x
* java 1.8+

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
 java -jar -Dspring.profiles.active=dev target/price-service-0.0.1-SNAPSHOT.jar
```
It will start the embedded tomcat server on default port 8080. This uses an embedded in-memory H2 database. You can use the default
Spring Boot profile in case you have a MySQL server available for you to test.

When the Camel application runs, you should see the following messages being logged to the console, e.g.:

....
2020-12-29 23:09:18.714  INFO 14561 --- [main] o.a.activemq.broker.TransportConnector   : Connector vm://embedded started
2020-12-29 23:09:18.750  INFO 14561 --- [main] o.a.c.i.e.InternalRouteStartupManager    : Route: vendor-price started and consuming from: jms://queue:input
2020-12-29 23:09:18.751  INFO 14561 --- [main] o.a.c.i.e.InternalRouteStartupManager    : Route: doc-api started and consuming from: servlet:/api-doc
2020-12-29 23:09:18.751  INFO 14561 --- [main] o.a.c.i.e.InternalRouteStartupManager    : Route: prices-vendor-api started and consuming from: servlet:/prices/vendor/%7Bid%7D
2020-12-29 23:09:18.752  INFO 14561 --- [main] o.a.c.i.e.InternalRouteStartupManager    : Route: prices-instrument-api started and consuming from: servlet:/prices/instrument/%7Bid%7D
2020-12-29 23:09:18.752  INFO 14561 --- [main] o.a.c.i.e.InternalRouteStartupManager    : Route: prices-create-api started and consuming from: servlet:/prices/create
2020-12-29 23:09:18.756  INFO 14561 --- [main] o.a.c.impl.engine.AbstractCamelContext   : Total 7 routes, of which 7 are started
2020-12-29 23:09:18.756  INFO 14561 --- [main] o.a.c.impl.engine.AbstractCamelContext   : Apache Camel 3.8.0-SNAPSHOT (MizuhoPriceService) started in 437ms
2020-12-29 23:09:18.758  INFO 14561 --- [main] o.a.c.component.quartz.QuartzComponent   : Starting scheduler.
2020-12-29 23:09:18.758  INFO 14561 --- [main] org.quartz.core.QuartzScheduler          : Scheduler DefaultQuartzScheduler-MizuhoPriceService_$_NON_CLUSTERED started.
2020-12-29 23:09:18.760  INFO 14561 --- [main] c.mizuho.price.PriceServiceApplication   : Started PriceServiceApplication in 5.207 seconds (JVM running for 5.667)
....

The Camel application can be stopped pressing ctrl+c in the shell.

# How to Use
1] Client Functionality
 a. To pubish price : POST request http://localhost:8080/mizuho-price-service/prices/create with 
 Input :
 ```json
    {
	"vendor" : "Bloomberg",
	"instrument" : "DE000JPM85H5", 
	"bid" :  80.63, 
	"ask" : 66.27, 
	"created" : "2020-12-29T12:11:22z"
    }
 ```
 Output: 201 Created
  ```json
   { "message": "Successfully created price" }
 ```
 b. To get price by vendor : GET request http://localhost:8080/mizuho-price-service/prices/vendor/Bloomberg
 Output : 200 OK
 ```json
    [
        {
            "id": 1,
            "vendor": "Bloomberg",
            "instrument": "DE000JPM85H5",
            "bid": 80.63,
            "ask": 66.27,
            "created": "2020-12-29T12:11:22Z"
        }
    ]
 ```    
 c. To get price by instrument : GET request http://localhost:8080/mizuho-price-service/prices/instrument/DE000JPM85H5/
 Output : 200 OK
 ```json
    [
        {
            "id": 1,
            "vendor": "Bloomberg",
            "instrument": "DE000JPM85H5",
            "bid": 80.63,
            "ask": 66.27,
            "created": "2020-12-29T12:11:22Z"
        }
    ]
 ```  
2] Vendor Functionality
 a. To send prices
3] Dowstream Functionality 
 a. To receive price
4] Swagger API : GET request http://localhost:8080/mizuho-price-service/api-doc
 Output : 200 OK
 ```json
{
  "swagger" : "2.0",
  "info" : {
    "version" : "1.0",
    "title" : "Mizuho Price Service API"
  },
  "host" : "localhost:8080",
  "basePath" : "/mizuho-price-service",
  "tags" : [ {
    "name" : "prices",
    "description" : "Prices REST service"
  } ],
  "schemes" : [ "http" ],
  "paths" : {
    "/prices/create" : {
      "post" : {
        "tags" : [ "prices" ],
        "summary" : "Creates a new price",
        "operationId" : "verb3",
        "consumes" : [ "application/json" ],
        "produces" : [ "application/json" ],
        "parameters" : [ {
          "in" : "body",
          "name" : "body",
          "required" : true,
          "schema" : {
            "$ref" : "#/definitions/Price"
          }
        } ],
        "responses" : {
          "200" : { }
        }
      }
    },
    "/prices/instrument/{id}" : {
      "get" : {
        "tags" : [ "prices" ],
        "summary" : "Prices by instrument id",
        "operationId" : "verb2",
        "parameters" : [ {
          "name" : "id",
          "in" : "path",
          "required" : true,
          "type" : "string"
        } ],
        "responses" : {
          "200" : { }
        }
      }
    },
    "/prices/vendor/{id}" : {
      "get" : {
        "tags" : [ "prices" ],
        "summary" : "Prices by vendor id",
        "operationId" : "verb1",
        "parameters" : [ {
          "name" : "id",
          "in" : "path",
          "required" : true,
          "type" : "string"
        } ],
        "responses" : {
          "200" : { }
        }
      }
    }
  },
  "definitions" : {
    "Price" : {
      "type" : "object",
      "required" : [ "created", "instrument", "vendor" ],
      "properties" : {
        "id" : {
          "type" : "integer",
          "format" : "int64"
        },
        "vendor" : {
          "type" : "string"
        },
        "instrument" : {
          "type" : "string"
        },
        "bid" : {
          "type" : "number"
        },
        "ask" : {
          "type" : "number"
        },
        "created" : {
          "type" : "string",
          "format" : "date-time"
        }
      }
    }
  }
}
 ```  


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
