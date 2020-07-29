Spring Cloud Stream Sample
==================================================

This is a Spring Cloud Stream processor sample.

## Requirements

To run this sample, you will need to have installed:

* Java 11 or Above

* Docker and docker-compose (or one rabbitMQ instance)


## Running the application

The following instructions assume that you are running RabbitMQ as a Docker image.

Go to the application root
* `docker-compose up -d`

* `./mvnw clean package`

* `java -jar target/demo-0.0.1-SNAPSHOT.jar`

In this example we have one Web Controller that receive HTTP calls to send one message into one queue depend on the request path

If the path is /produce/list controller send the Item to listItems queue, so the Item is only consume by printItem function

```
curl -H 'Content-Type: application/json' -d '{"name": "tomato","price": 10}' -X GET http://localhost:8080/produce/list
curl -H 'Content-Type: application/json' -d '{"name": "potato","price": 15}' -X GET http://localhost:8080/produce/list
```

Output :

```
Item tomato(10)
Item potato(15)
```

If the path is /produce/buy controller send the Item to buyItems queue, so the Item is consume by printItem and getPrice function.
 getPrice will send only price to addPrices queue consume by calculTotal function

```
curl -H 'Content-Type: application/json' -d '{"name": "tomato","price": 10}' -X GET http://localhost:8080/produce/buy
curl -H 'Content-Type: application/json' -d '{"name": "potato","price": 15}' -X GET http://localhost:8080/produce/buy
```

Output :

```
Item tomato(10)
Total : 10
Item potato(15)
Total : 25
```


