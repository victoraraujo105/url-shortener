# Url Shortener API

A simple URL shortener API written in Java using Spring Boot.

## Requirements
- Docker
- Docker Compose

## How to run
```sh
docker-compose up
```

## Endpoints
* Random alias call:

```
PUT http://localhost:8080/create?url=http://www.vale.com.br

{
   "alias": "XYhakp",
   "url": "http://shortener/u/XYhakp",
   "statistics": {
       "time_taken": "10ms",
   }
}
```

* Custom alias call:
```
PUT http://shortener/create?url=http://www.vale.com.br&CUSTOM_ALIAS=vale

{
   "alias": "vale",
   "url": "http://shortener/u/vale",
   "statistics": {
       "time_taken": "12ms",
   }
}
```

* Redirect call:
```
GET http://shortener/u/vale
```

