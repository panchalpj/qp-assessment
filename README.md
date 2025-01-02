# Grocery and Order Management Sample API's 

This application provides RESTful APIs for managing grocery items and user orders. The system supports CRUD operations for groceries, placing orders, and fetching order details for users.

---

## **Features**

### **Admin Features**
- Add, update, delete, and fetch grocery items.
- Retrieve a paginated list of grocery items.

### **User Features**
- Place an order with multiple grocery items.
- Fetch all orders placed by a specific user.

---

## **Technologies Used**
- **Java**: Backend language.
- **Spring Boot**: Framework for creating REST APIs.
- **Hibernate/JPA**: ORM for database operations.
- **MySQL**: Database for storing grocery items, orders, and users.
- **Lombok**: For reducing boilerplate code.
- **Maven/Gradle**: Dependency management and build tool.

---

## **API Endpoints**

### **Admin Endpoints**
| Method | URL                          | Description                           |
|--------|-------------------------------|---------------------------------------|
| GET    | `/admin/test`                | Returns a welcome message.           |
| POST   | `/admin/grocery`             | Adds a new grocery item.             |
| GET    | `/admin/grocery`             | Retrieves all grocery items.         |
| PUT    | `/admin/grocery/{id}`        | Updates a grocery item by ID.        |
| POST   | `/admin/grocery-items`       | Fetches a paginated list of items.   |
| DELETE | `/admin/grocery/{id}`        | Deletes a grocery item by ID.        |

### **User Endpoints**
| Method | URL                          | Description                           |
|--------|-------------------------------|---------------------------------------|
| POST   | `/user/order`                | Places an order for a user.          |
| GET    | `/user/find-order/{id}`      | Fetches all orders for a user by ID. |

---

## **How to Run**
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```
2. Configure the database connection in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/questionpro
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
3. Build and run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Access the API at `http://localhost:8080`.

---

## **Request/Response Examples**

### **1. Add a Grocery Item**
**Request**:  
`POST /admin/grocery`
```json
{
  "name": "Apple",
  "price": 2.5,
  "inventory": 100
}
```

**Response**:
```json
{
  "id": 1,
  "name": "Apple",
  "price": 2.5,
  "inventory": 100
}
```

---

### **2. Place an Order**
**Request**:  
`POST /user/order`
```json
{
  "userId": 123,
  "items": {
    "1": 2,
    "2": 5
  }
}
```

**Response**:
```json
{
  "id": 456,
  "userEmail": "example@example.com",
  "orderedItemsDTOS": [
    {
      "name": "Apple",
      "price": 2.5
    },
    {
      "name": "Orange",
      "price": 3.0
    }
  ],
  "totalPrice": 20.0,
  "modifiedTime": "2024-12-21T12:34:56"
}
```

---

## **Future Enhancements**
- Add authentication and authorization for admin and user operations.
- Implement global exception handling.
- Include Swagger/OpenAPI documentation.
- Enhance validation for inputs using `@Valid`.

---

## **Contributors**
- **Your Name**: Pankaj Panchal
- **Your Team**: Pankaj Panchal

---

## **License**
This project is licensed under the MIT License.


# Setup For Spring Boot Application with Docker

This project is a Spring Boot application that has been containerized using Docker for easy deployment and scaling.

## Prerequisites

Before you start, make sure you have the following installed:

- [Docker](https://www.docker.com/get-started) (for containerization)
- Java 17 or above (for local development and building the application)
- Maven or Gradle (for building the application, depending on your setup)

## Project Setup

1. **Clone the Repository**

   Clone the repository to your local machine using the following command:

   ```bash
   git clone https://github.com/your-repository.git
   cd your-repository
   ```

2. **Build the Application**

   If you haven't already built the JAR file, you can build it using Maven or Gradle.

   For Maven:
   ```bash
   mvn clean package
   ```

   For Gradle:
   ```bash
   ./gradlew build
   ```

3. **Dockerize the Application**

   The `Dockerfile` is already configured for containerizing the Spring Boot application. Now, you can build the Docker image.

### Docker Commands

4. **Build the Docker Image**

   To build the Docker image for the application, run the following command:

   ```bash
   docker build -t my-spring-boot-app .
   ```

   This will create a Docker image named `my-spring-boot-app`.

5. **Run the Docker Container**

   Once the image is built, you can run the application in a Docker container with the following command:

   ```bash
   docker run -p 9080:9080 my-spring-boot-app
   ```

   This command will map the container's port `9080` to your localhost's port `9080`, making the application accessible on `http://localhost:9080`.

6. **Access the Application**

   After the container is running, you can access your application via the following URL in your web browser or through an API client:

   ```
   http://localhost:9080
   ```

## Stopping the Docker Container

To stop the running Docker container, use the following command:

```bash
docker stop <container_id>
```

You can find the `container_id` by running:

```bash
docker ps
```

## Additional Configuration

You can modify the memory settings and Java options for the container by adjusting the `JAVA_OPTS` environment variable in the `Dockerfile`. For example:

```Dockerfile
ENV JAVA_OPTS="-Xms4G -Xmx7G -XX:+UseG1GC"
```

This can be useful if you need to adjust the heap size or garbage collection settings for your application.

## Troubleshooting

- **Docker Build Issues**: If you encounter issues while building the Docker image, ensure Docker is properly installed and running on your machine.
- **Port Conflicts**: If port `9080` is already in use, you can map the container to another available port on your machine by modifying the `docker run` command:

  ```bash
  docker run -p 9090:9090 my-spring-boot-app
  ```

  This will map container port `9090` to your local port `9090`.
