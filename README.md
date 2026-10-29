# Mechanic Workshop Management System

A REST API for managing a car repair shop (oficina mecânica), built with **Java and Spring Boot**. This project is an evolution of an earlier version built with plain Java + JDBC, rewritten to use Spring Boot, Spring Data JPA, and a layered architecture.

## Tech Stack

- **Java 21**
- **Spring Boot 4** (Spring Web MVC, Spring Data JPA)
- **Bean Validation** (Jakarta Validation) for request validation
- **MySQL** as the relational database
- **MapStruct** for entity ↔ DTO mapping
- **Lombok** to reduce boilerplate (getters/setters/constructors)
- **Maven** as the build tool

## Project Structure

The project follows a layered architecture:

- `controller`: REST controllers that expose the HTTP endpoints
- `service`: business logic and business rules, with transaction control (`@Transactional`)
- `repository`: Spring Data JPA repositories for database access
- `entity`: JPA entities (`Client`, `Car`, `OrderService`, `ServiceHistory`, `ListOfServiceHistory`, `ServiceOrderStatus`)
- `dto`: request/response objects exposed by the API, with validation annotations
- `mapper`: MapStruct interfaces that convert between entities and DTOs
- `exception`: custom exceptions for each kind of error
- `handler`: global exception handler that turns exceptions into standardized error responses
- `config`: web configuration (pagination)

## Domain Model & Business Rules

The workshop workflow is modeled as follows:

1. An employee registers a **Client**.
2. A **Car** is registered and linked to that client.
3. One or more **Order Services** (service orders) are created for a car. Each order starts with status `WAITING`.
4. When work begins, the order is marked as **started**. The start date is set automatically, and the status becomes `INITIATED`.
5. When the work is done, the order is marked as **completed**. The completion date is set automatically, and the status becomes `COMPLETED`. Only started orders can be completed.
6. An order can be **cancelled** unless it is already completed. The cancellation date is set automatically, and the status becomes `CANCELED`.
7. A **price** can be set for each order (zero or positive).
8. Once **all** orders of a car are `COMPLETED` or `CANCELED`, and every one has a price, the repair can be closed out. This generates a **Service History** record: a consolidated, invoice-like summary with the client's data, the car's data, the description and price of each order, and the total amount. Each order is included in only one history.

### Order Service Status

- `WAITING`: created, not started yet
- `INITIATED`: work has started
- `COMPLETED`: work finished
- `CANCELED`: order cancelled

### Other rules

- CPF, telephone and license plate must be unique.
- CPF must contain exactly 11 digits.
- A client cannot be deleted while they still have cars registered.
- A car cannot be deleted while it has pending orders (`WAITING` or `INITIATED`).

## Features

- Client registration, lookup, update and deletion
- Car registration linked to a client, lookup, update and deletion
- Listing all cars of a client
- Creation of service orders for a car
- Full service order lifecycle: start, complete, cancel, set price, update
- Generation of a consolidated service history ("invoice") once all orders of a car are finished
- Lookup of service history by client CPF
- **Pagination** on every listing endpoint (`Pageable`)
- **Request validation** with Bean Validation (`@Valid`, `@NotBlank`, `@NotNull`, `@Pattern`, `@PositiveOrZero`)
- **Global exception handling** with `@RestControllerAdvice` and standardized error responses (`ProblemDetail`, RFC 9457)
- **Transaction management**: services are read-only by default, and write operations use `@Transactional`

## Pagination

Every listing endpoint returns a page and accepts the query parameters:

| Parameter | Description | Example |
| --- | --- | --- |
| `page` | Page number (starts at 1) | `page=1` |
| `size` | Items per page (default and maximum: 10) | `size=5` |
| `sort` | Field and direction | `sort=name,asc` |

Example: `GET /clients?page=1&size=5&sort=name,asc`

## Error Handling

All errors follow the same format (`ProblemDetail`, RFC 9457), with a `timestamp`:

| Status | When |
| --- | --- |
| `400 Bad Request` | Invalid fields, malformed JSON or invalid CPF in the URL |
| `404 Not Found` | Resource not found by id |
| `409 Conflict` | Duplicate data (CPF, telephone, plate), deletion blocked by associations, or database integrity violation |
| `422 Unprocessable Content` | Business rule violated (for example, cancelling a completed order) |
| `500 Internal Server Error` | Unexpected error (details are logged, not exposed) |

Example of a validation error:

```json
{
  "type": "about:blank",
  "title": "Campos inválidos",
  "status": 400,
  "detail": "Um ou mais campos estão inválidos.",
  "instance": "/clients",
  "erros": [
    { "field": "cpf", "message": "CPF deve conter 11 números." }
  ],
  "timestamp": "2026-10-29T14:30:00Z"
}
```

## How to Run

### Prerequisites

- Java 21+
- Maven
- MySQL running (local or Docker)

### Database

The database and tables are created automatically on startup (`ddl-auto: update` + `createDatabaseIfNotExist=true`), so you don't need to run any script.

### Configuration

Database connection settings are in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/oficinaMecanica?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false
    username: root
    password: ${DB_PASSWORD}
```

Before running the app, set the `DB_PASSWORD` environment variable to your local MySQL password.

### Running the app

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Main Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/clients` | Register a new client |
| GET | `/clients` | List all clients (paginated) |
| GET | `/clients/{id}` | Get a client by id |
| GET | `/clients/{id}/cars` | List all cars of a client (paginated) |
| PUT | `/clients/{id}` | Update a client |
| DELETE | `/clients/{id}` | Delete a client |
| POST | `/cars` | Register a new car |
| GET | `/cars` | List all cars (paginated) |
| GET | `/cars/{id}` | Get a car by id |
| PUT | `/cars/{id}` | Update a car |
| DELETE | `/cars/{id}` | Delete a car |
| POST | `/orders` | Create a service order for a car |
| GET | `/orders` | List all service orders (paginated) |
| GET | `/orders/{id}` | Get a service order by id |
| GET | `/orders/{id}/car` | List all service orders of a car (paginated) |
| PATCH | `/orders/{id}/started` | Start a service order |
| PATCH | `/orders/{id}/completed` | Complete a service order |
| PATCH | `/orders/{id}/cancelled` | Cancel a service order |
| PATCH | `/orders/{id}/price` | Set the price of a service order (body: `{"price": 150.00}`) |
| PUT | `/orders/{id}` | Update a service order |
| POST | `/histories/{idCar}` | Close out the repair and generate the service history |
| GET | `/histories` | List all service histories (paginated) |
| GET | `/histories/{cpf}/client` | List service histories by client CPF (paginated) |

## Roadmap / Future Improvements

- Automated tests (unit and integration)
- API documentation with Swagger / OpenAPI
- Authentication and authorization (Spring Security)
- Docker Compose to run the app and the database together
- Frontend / UI dashboard
