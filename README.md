# Mechanic Workshop Management System

A REST API for managing a car repair shop (oficina mecânica), built with **Java and Spring Boot**. This project is an evolution of an earlier version built with plain Java + JDBC, rewritten to use Spring Boot, Spring Data JPA, and a proper layered architecture.

## Tech Stack

- **Java 21**
- **Spring Boot** (Spring Web, Spring Data JPA)
- **MySQL** as the relational database
- **MapStruct** for entity ↔ DTO mapping
- **Lombok** for boilerplate reduction (getters/setters/constructors)
- **Maven** as the build tool

## Project Structure

The project follows a standard layered architecture:

- `entity` — JPA entities (`Client`, `Car`, `OrderService`, `ServiceHistory`, `ListOfServiceHistory`, `ServiceOrderStatus`)
- `repository` — Spring Data JPA repositories for database access
- `service` — business logic and validation rules
- `mapper` — MapStruct interfaces that convert between entities and DTOs
- `dto` — request/response objects exposed by the API
- `controller` — REST controllers exposing the HTTP endpoints

## Domain Model & Business Rules

The workshop workflow is modeled as follows:

1. An employee registers a **Client**.
2. A **Car** is registered and linked to that client.
3. One or more **Order Services** (service orders) are created for a car. Each order starts with status `WAITING`.
4. When work begins on an order, the employee marks it as **started**, providing a start date — status becomes `INITIATED`.
5. When the work is done, the employee marks it as **completed**, providing a completion date — status becomes `COMPLETED`. A price must be set for the order.
6. An order can also be **cancelled**. If no start/end date was set, the current date is used automatically — status becomes `CANCELED`.
7. Once **all** order services for a car are either `COMPLETED` or `CANCELED`, the employee can close out the repair. This generates a **Service History** record — a consolidated invoice-like summary containing the client's data, the car's data, a description and price for each order service, and the total amount.

### Order Service Status

- `WAITING` — created, not started yet
- `INITIATED` — work has started
- `COMPLETED` — work finished
- `CANCELED` — order cancelled

## Features

- Client registration, lookup, update and deletion
- Car registration linked to a client, lookup, update and deletion
- Listing all cars belonging to a specific client
- Creation of service orders for a car
- Full service order lifecycle: start, complete, cancel, set price, update
- Prevents deleting a client/car that still has pending service orders
- Generation of a consolidated service history ("invoice") once all service orders for a car are finished
- Lookup of service history by client CPF

## How to Run

### Prerequisites

- Java 21+
- Maven
- MySQL running (local or Docker)

### Database

The database and tables are created automatically on startup (`spring.jpa.hibernate.ddl-auto=update` + `createDatabaseIfNotExist=true`), so no manual script needs to be run.

### Configuration

Database connection settings are in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/oficinaMecanica?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false
    username: root
    password: ${DB_PASSWORD}
```

Set the `DB_PASSWORD` environment variable with your local MySQL password before running the app.

### Running the app

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Main Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/clients` | Register a new client |
| GET | `/clients` | List all clients |
| GET | `/clients/{id}` | Get a client by id |
| GET | `/clients/{id}/cars` | List all cars of a client |
| PUT | `/clients/{id}` | Update a client |
| DELETE | `/clients/{id}` | Delete a client |
| POST | `/cars` | Register a new car |
| GET | `/cars` | List all cars |
| GET | `/cars/{id}` | Get a car by id |
| PUT | `/cars/{id}` | Update a car |
| DELETE | `/cars/{id}` | Delete a car |
| POST | `/orders` | Create a service order for a car |
| GET | `/orders` | List all service orders |
| GET | `/orders/{id}` | Get a service order by id |
| GET | `/orders/{id}/car` | List all service orders of a car |
| PATCH | `/orders/{id}/started` | Start a service order |
| PATCH | `/orders/{id}/completed` | Complete a service order |
| PATCH | `/orders/{id}/cancelled` | Cancel a service order |
| PATCH | `/orders/{id}/price` | Set the price of a service order |
| PUT | `/orders/{id}` | Update a service order |
| POST | `/historys/{idCar}` | Close out the repair and generate the service history |
| GET | `/historys` | List all service histories |
| GET | `/historys/{cpf}/client` | List service histories by client CPF |

## Roadmap / Future Improvements

- Bean Validation (`@Valid` + `@NotBlank`/`@NotNull`) on request DTOs
- Global exception handling with `@ControllerAdvice`
- Automated tests (unit and integration)
- Authentication/authorization
- Frontend / UI dashboard
