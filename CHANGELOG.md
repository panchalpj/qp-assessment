## Guiding Principles

- Changelogs are for humans, not machines.
- There should be an entry for every single version.
- The same types of changes should be grouped.
- Versions and sections should be linkable.
- The latest version comes first.
- The release date of each version is displayed.
- Mention whether you follow Semantic Versioning.

## Types of changes

- **Added** for new features.
- **Changed** for changes in existing functionality.
- **Deprecated** for soon-to-be removed features.
- **Removed** for now removed features.
- **Fixed** for any bug fixes.
- **Security** in case of vulnerabilities.
- [Refer](https://keepachangelog.com/en/1.1.0/)

## [0.0.2] - 2024-12-21
### Added
- `AdminController` to manage grocery items, including CRUD operations and pagination.
    - Added endpoints:
        - `POST /admin/grocery` to add grocery items.
        - `GET /admin/grocery` to fetch all grocery items.
        - `PUT /admin/grocery/{id}` to update a grocery item.
        - `POST /admin/grocery-items` to get paginated grocery items.
        - `DELETE /admin/grocery/{id}` to delete a grocery item.
- `UserController` to manage user orders.
    - Added endpoints:
        - `POST /user/order` to place a user order.
        - `GET /user/find-order/{id}` to retrieve orders by user ID.
- DTOs for structured response handling: `GroceryItemDTO`, `OrderDTO`, and `OrderRequest`.

## [0.0.1-RELEASE]
### Added
- Initial project setup with basic structure.



# Changelog

## AdminController

### New Features
- **Controller Setup**:
    - Added `@RestController` with the base URL `/admin` to manage grocery item operations.
    - Included `@CrossOrigin` annotation to allow cross-origin requests from all origins.

### Endpoints Added
- **`POST /admin/grocery`**:
    - Adds a new grocery item using `AdminService.addGroceryItem`.
- **`GET /admin/grocery`**:
    - Retrieves all grocery items using `AdminService.getAllGroceryItems`.
- **`PUT /admin/grocery/{id}`**:
    - Updates a grocery item by ID using `AdminService.updateGroceryItem`.
- **`POST /admin/grocery-items`**:
    - Fetches paginated grocery items using `AdminService.getPaginatedGroceryItems`.
- **`DELETE /admin/grocery/{id}`**:
    - Deletes a grocery item by ID using `AdminService.deleteGroceryItem`.

### Utility Endpoint
- **`GET /admin/test`**:
    - Returns a welcome message: "Welcome To Report Service v2!!!".

### Logging
- Added a `Logger` instance for future logging needs.

---

## UserController

### New Features
- **Controller Setup**:
    - Added `@RestController` with the base URL `/user` to handle user-related operations.

### Endpoints Added
- **`POST /user/order`**:
    - Places an order using `UserService.placeOrder`.
- **`GET /user/find-order/{id}`**:
    - Fetches all orders for a user by their user ID using `UserService.getOrdersByUser`.

---

## General Observations
- **Structure**:
    - Controllers are clearly organized, separating admin and user responsibilities.
- **Dependency Injection**:
    - Services (`AdminService`, `UserService`) are injected via constructor-based DI for maintainability.
- **Standard HTTP Responses**:
    - Utilized `ResponseEntity` to return appropriate HTTP responses (e.g., `200 OK`, `204 No Content`).
- **DTO Usage**:
    - `GroceryItemDTO` and `OrderDTO` abstract database entities from API consumers.
- **Pagination Support**:
    - Introduced paginated fetching for grocery items via `/admin/grocery-items`.

---

## Recommendations
- **Error Handling**:
    - Add global exception handling using `@ControllerAdvice` for better error management.
- **Logging**:
    - Leverage the existing `Logger` instance for debugging and monitoring.
- **Validation**:
    - Use `@Valid` annotations for validating request bodies in DTOs.
- **API Documentation**:
    - Add API documentation using Swagger/OpenAPI for better usability and testing.


