# Grocery and Order Management API

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
- **Your Name**: Developer
- **Your Team**: Backend Development Team

---

## **License**
This project is licensed under the MIT License.

