# This app allows performing operations with elevator system
# Demo: https://youtu.be/8uPlcoHNBPA

## Tech stack:
* Angular: 15.2.8
* Spring Boot: 3.0.4
* Docker

## How it works?
* You can choose between 1-16 elevators
* Maximum capacity for one elevator is 500 kg
* Maximum number of persons inside is 7
* You can change that in ElevatorConstants.java

## Elevators algorithm
* At the beginning elevators are initialized
* When elevator is called backend receives information about from which floor and to which floor it has to go
* Person's weight is randomly generated in range 50-100 kg
* If it comes to choosing elevator there are few steps:
* 1: checking if there is any free elevator (people + 1 <= 7 && capacity + personWeight <= 500)
* 1.1: if no user receives information that every elevator is full END
* 2: checking if there are any unused elevators
* 2.1: if yes then the closest one is chosen END
* 3: if no then the closest one from currently used elevators is chosen END
* Elevators always complete their last addded transport, but if there are any persons inside with the same destination floor as current transport they finish their journey as well
# Endpoints:

### 1
```localhost:8080/api/v1/elevators```
* method: POST
* This endpoint initializes elevators by number of elevators sent in body

### 2
```localhost:8080/api/v1/elevators```
* method: GET
* This endpoint makes one step and returns elevators

### 3
```localhost:8080/api/v1/elevators```
* method: PUT
* This endpoint calls elevator by params from and to

# How to run this project?

## in directory with docker-compose file run this command:
```
docker-compose up
```

### frontend and backend has their readme files with examples on how to build&run docker images

# Thoughts about this project
### This project was done in a few hours that's why code style and algorithm for sure needs more work on it
### My thoughts to improve this project:
* Use websockets to call elevators
* Take under consideration elevator's current direction and apply it to algorithm to increase performance