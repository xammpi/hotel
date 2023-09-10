# Sample Applicaiton for Hotel

## Table of Contents

- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [API Endpoints](#api-endpoints)
- [Swagger Endpoints](#api-endpoints)
- [View Endpoints](#view-endpoints)
- [Technologies Used](#technologies-used)
## Getting Started

To get started with this project, follow these steps:

1. Clone the repository.
2. Install Java and Maven.
3. Install Docker
4. Build the application using 'mvn clean package' (deafult profile is docker).
5. Run the application using 'docker-compose up -d --build'

## Prerequisites

- Java 17 or higher
- Maven
- MySQL:8.0 database

## Swagger Endpoints

Base URL: http://localhost:8081/swagger

## API Endpoints

Base URL: http://localhost:8081

Reservations

1. GET /api/reservations: Get a list of all reservations.
2. GET /api/reservations/search: Get a list of all reservations by pagination.
3. GET /api/reservations/{id}: Get an reservation by ID.
4. POST /api/reservations: Create a new reservation.
5. PUT /api/reservations/{id}: Update an existing reservation.
6. DELETE /api/reservations/{id}: Delete an reservation by ID.

Guests

1. GET /api/guests: Get a list of all geusts.
2. GET /api/guests/search: Get a list of all geusts by pagination.
3. GET /api/guests/{id}: Get an guest by ID.
4. POST /api/guests: Create a new guest.
5. PUT /api/guests/{id}: Update an existing guest.
6. DELETE /api/guests/{id}: Delete an guest by ID.

Rooms

1. GET /api/rooms: Get a list of all rooms.
2. GET /api/rooms/search: Get a list of all rooms by pagination.
3. GET /api/rooms/{id}: Get an room by ID.
4. POST /api/rooms: Create a new room.
5. PUT /api/rooms/{id}: Update an existing room.
6. DELETE /api/rooms/{id}: Delete an room by ID.

## View Endpoints

Base URL: http://localhost:8080

1. Reservations: /reservations
2. Guests: /guest
3. Rooms: /rooms

## Technologies Used

- **Spring Boot**
- **Spring Boot Web**
- **Spring Data JPA**
- **Spring Boot Validation**
- **Swagger**
- **MySQL**
- **Maven**
- **Thymeleaf**
- **Docker**