
# Task Management API

The **Task Management API** provides a structured interface for creating, managing, and tracking tasks efficiently. 
It is built using **Spring Boot**, adheres to REST principles, and integrates with **H2** for persistence. 

---

## Features

- Create, update, and delete tasks.
- Assign priorities and due dates to tasks.
- Track task completion status.
- RESTful endpoints with standardized request/response structures.

---

## Technology Stack

- **Backend:** Java, Spring Boot, Spring Data JPA
- **Database:** H2
- **Build Tool:** Maven
- **Testing:** JUnit, Postman (for API testing)

---

## API Endpoints

### Task Endpoints

- **POST** `/tasks`  
  Create a new task.  
  **Request Body Example:**
  ```json
  {
    "title": "Complete project",
    "description": "Finish the pending project module",
    "priority": "HIGH",
    "dueDate": "2025-09-05",
    "status": "PENDING",
    "categoryId": 1
  }
  ```

- **GET** `/tasks`  
  Retrieve all tasks.

- **GET** `/tasks/{id}`  
  Retrieve a task by ID.

- **PUT** `/tasks/{id}`  
  Update an existing task.

- **DELETE** `/tasks/{id}`  
  Delete a task by ID.

---



## Database Schema

### Task Table
| Column       | Type        | Description                       |
|--------------|------------|-----------------------------------|
| id           | BIGINT (PK)| Unique identifier for each task.  |
| title        | VARCHAR    | Title of the task.                |
| description  | TEXT       | Detailed description of the task. |
| priority     | ENUM       | Priority: LOW, MEDIUM, HIGH.      |
| dueDate      | DATE       | Task due date.                    |
| status       | ENUM       | Status: PENDING, COMPLETED.       |

---

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/taskmanagement-api.git
   cd taskmanagement-api
   ```

2. Configure `application.properties`
   ```properties
   spring.application.name=TaskManagerApplication
   spring.datasource.url=jdbc:h2:mem:taskmanager_db
   spring.datasource.username = root
   spring.datasource.password = root
   spring.datasource.driverClassName=org.h2.Driver
   spring.jpa.hibernate.ddl-auto=create-drop
   spring.h2.console.enabled=true
   ```

3. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

---

## Testing

- Use **Postman** or **cURL** to test API endpoints.  

---


## Contact

For queries or support, please contact:  
**Developer:** Liyons  
**Email:** liyonsvarghese@gmail.com

