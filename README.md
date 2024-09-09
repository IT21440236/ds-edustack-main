


# Learning Management System (LMS) Microservices Project

This project is a comprehensive **Learning Management System (LMS)** developed using **Java Spring Boot** and **Angular** with a microservices architecture. The system is designed to manage learners, courses, payments, and notifications, with each service running independently to ensure scalability and flexibility. 

## Features
- **Learner Management**: Track learners' progress through various courses.
- **Course Management**: Manage courses, including multiple enrollments and tracking completion.
- **Payment Service**: Secure and efficient payment processing.
- **Notification Service**: Automated notifications for various events like course enrollment, payment confirmation, and more.
- **Microservices Communication**: Services communicate through **Feign Client** for synchronous calls and **RabbitMQ** for asynchronous messaging.
- **Docker**: Containerized services for seamless deployment and scalability.

## Tech Stack
### Backend
- **Java Spring Boot**: Framework for building microservices.
- **Feign Client**: For service-to-service communication.
- **RabbitMQ**: Message broker for asynchronous communication.
- **Docker**: For containerizing the microservices.
- **MySQL/PostgreSQL**: Database for storing user, course, and payment data.
- **JWT**: Authentication and authorization.

### Frontend
The frontend is built using **Angular** and connects with the backend services. You can find the frontend repository here:
- [Frontend GitHub Repository](https://github.com/IT21307058/ds_frontend/tree/develop)

## How to Run

1. **Clone the repository**:
   ```bash
   git clone <backend-repo-url>
   cd <backend-repo-folder>
   ```

2. **Build and Run the Services**:
   - Use **Docker** to build and run the microservices.
   - Ensure that **RabbitMQ** and **MySQL/PostgreSQL** are running and configured properly.

3. **Clone the Frontend**:
   - Clone and run the frontend repository from [here](https://github.com/IT21307058/ds_frontend/tree/develop).

