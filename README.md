# Hostfully Java test 

This application was generated using Spring Initializr [Spring Initializr](https://start.spring.io).

## Development

To start your application locally, run:

```
./mvnw
```

## Database

Once application is running go to http://localhost:8080/h2-console and complete the follow information:
```
Saved Settings: Generic H2 (Embedded)
Setting Name: Generic H2 (Embedded)
  
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
User Name: username
Password: password
```

### Data

You can save some demo or initial data running a SQL script. e.g:
```sql
INSERT INTO BOOKING (ID, DATE_FROM, DATE_TO, GUEST, STATE)
VALUES
(1, '2023-11-01', '2023-11-05', 'guest', 'BOOKED'),
(2, '2023-11-08', '2023-11-11', 'guest', 'BOOKED');

INSERT INTO BLOCK (ID, DATE_FROM, DATE_TO, MANAGER)
VALUES
(1, '2023-11-14', '2023-11-16', 'manager');
```


## API

### Block

#### CREATE (POST)
```shell
curl --location 'http://localhost:8080/api/blocks' \
--header 'Content-Type: application/json' \
--data '{
    "dateFrom": "2023-10-15",
    "dateTo": "2023-10-16",
    "manager": "manager"
}'
```


#### UPDATE (PUT & PATCH)
PUT: It's a full update of an existing booking.
```shell
curl --location --request PUT 'http://localhost:8080/api/blocks/1' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "dateFrom": "2023-10-15",
    "dateTo": "2023-10-16",
    "manager": "manager2"
}
'
```

PATCH: It's a partial updates given fields of an existing booking, field will ignore if it is null.
```shell
curl --location --request PATCH 'http://localhost:8080/api/blocks/1' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "manager": "manager2"
}
'
```

#### GET
To get one:
```shell
curl --location 'http://localhost:8080/api/blocks/1'
```

To get all:
```shell
curl --location 'http://localhost:8080/api/blocks'
```


### Booking

#### CREATE (POST)
```shell
curl --location 'http://localhost:8080/api/bookings' \
--header 'Content-Type: application/json' \
--data '{
    "dateFrom": "2023-11-09",
    "dateTo": "2023-11-10",
    "guest": "guest"
}'
```
By default Booking state is BOOKED.


#### UPDATE (PUT & PATCH)
PUT: It's a full update of an existing booking.
```shell
curl --location --request PUT 'http://localhost:8080/api/bookings/1' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "dateFrom": "2023-11-09",
    "dateTo": "2023-11-10",
    "guest": "guest2"
}
'
```

PATCH: It's a partial updates given fields of an existing booking, field will ignore if it is null.
```shell
curl --location --request PATCH 'http://localhost:8080/api/bookings/1' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "guest": "guest2"
}
'
```


#### CANCEL or REBOOK
To Cancel or Rebook you can use PUT or PATCH with state CANCELED or BOOKED.

To cancel with PUT:
```shell
curl --location --request PUT 'http://localhost:8080/api/bookings/1' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "dateFrom": "2023-11-09",
    "dateTo": "2023-11-10",
    "guest": "guest",
    "state": "CANCELED"
}
'
```

To cancel with PATCH:
```shell
curl --location --request PATCH 'http://localhost:8080/api/bookings/1' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "state": "CANCELED"
}
'
```


To rebook with PUT:
```shell
curl --location --request PUT 'http://localhost:8080/api/bookings/1' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "dateFrom": "2023-11-09",
    "dateTo": "2023-11-10",
    "guest": "guest",
    "state": "REBOOK"
}
'
```

To rebook with PATCH:
```shell
curl --location --request PATCH 'http://localhost:8080/api/bookings/1' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "state": "REBOOK"
}
'
```


#### GET
To get one:
```shell
curl --location 'http://localhost:8080/api/bookings/1'
```

To get all:
```shell
curl --location 'http://localhost:8080/api/bookings'
```
