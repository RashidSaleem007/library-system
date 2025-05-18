1. Build the image
   docker build --build-arg FULL_JAR_FILE_NAME=your-app-name-0.0.1-SNAPSHOT -t your-app-image .

2. Run with desired profile
   docker run -e SPRING_PROFILE=dev -p 8080:8080 your-app-image

API Documentation:
Swagger URL:http://localhost:8080/swagger-ui/index.html

Assumptions:
1. Book copies are modeled as separate rows in the book table with unique IDs. There is no separate BookCopy entity.
2. All list endpoints (e.g. GET /books) return paginated responses using Spring’s Pageable.
3. Basic pagination assumed; no advanced filtering (e.g., by author or title substring) unless added later.
4. The application assumes an SQL-based database like H2 (in-memory for development/tests), PostgresSQL in production.
5. Profiles like dev and prod are supported using application-{profile}.yml
6. Controllers work exclusively with DTOs (never expose JPA entities directly).
7. Tests focus on service layer logic; controller layer is assumed to be separately tested or stubbed.
8. The application can be containerized and run with Docker; assumes default port 8080 and JAR-based Spring Boot app.