# Bookstore Inventory Management System

The Bookstore Inventory Management System is a web-based REST API designed to help bookstore owners manage their inventory efficiently. It enables users to add, update, search, and delete books while also managing associated authors and genres. Built with Spring Boot and Maven, the application utilizes a PostgreSQL database (deployed via Docker Compose) to store and retrieve data. With JWT-based authentication, the system ensures that only authorized personnel can perform critical operations, making it a secure and scalable solution for modern bookstore management.

## Prerequisites

- **JDK 23**  
  Ensure that JDK 23 is installed and set as your default Java version.
- **Maven 3.6+** (or use the provided Maven Wrapper)
- **Docker & Docker Compose**  
  Used to run the PostgreSQL database container.

## How to Run the Application

### 1. Start the Database

Make sure you're in the project root directory where the `docker-compose.yml` file is located. Then, run the following command:

```bash
docker-compose up --build
```

The db runs on port 5432.

### 2. Build and run the application
In terminal use the provided Maven wrapper

```bash
./mvnw spring-boot:run
```
This will build and run the Spring Boot application. By default, the app will start on port 8080.

### 3. Access the application
Once the app is running you can access it on: 
```bash
http://localhost:8080
```
