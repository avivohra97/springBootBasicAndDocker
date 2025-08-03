# springBootBasicAndDocker

To get started 
```java
@SpringBootApplication
@ConfigurationPropertiesScan("com.example.config")
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

	}

}
```

Then we need controllers and service.
service is where the entire computation logic lies. Example: you need to get data from external api.

### basic flow:
- start with creating MOdel, which would mark template of object.
- Then create interface of service.
- Next, implement the service class, where all the CRUD ops can be placed.

### Http Calls to external
Use UNIREST

```java
HttpResponse<TimeAPIModel> response = Unirest.get(url).connectTimeout(60)
                    .queryString("timeZone", timeAPIConfig.getContinent()+"/"+timeZone)
                    .asObject(TimeAPIModel.class);
```

# Spring Boot & Git Troubleshooting Session

This document summarizes the key topics and solutions we covered during our session. The goal is to provide a quick reference for common issues encountered while developing with Spring Boot.

## 1. Handling External API Connection Errors

**Problem:** Receiving a `ConnectTimeoutException` when your Spring Boot application tries to connect to an external API (e.g., `timeapi.io`).

**Solution:** This is a network issue, not a code problem. The two primary fixes are:

* **Check the API status:** Ensure the external service is online and the URL is correct.

* **Increase the timeout:** Use `Unirest.setTimeouts()` to give the connection more time to establish.

## 2. Resolving `NoSuchMethodError` with Lombok

**Problem:** A `java.lang.NoSuchMethodError` occurred, indicating a constructor mismatch. This was caused by the `UserEntityMapper` attempting to call a constructor that didn't exist due to a mix of manual and Lombok-generated code.

**Solution:** Simplify the `userModel` class by relying solely on **Lombok annotations**.

* Use `@Data`, `@NoArgsConstructor`, and `@AllArgsConstructor`.

* Remove all manually written constructors, getters, and setters.
  This guarantees that all necessary boilerplate code, including the four-argument constructor, is correctly generated.

## 3. Implementing Duplicate Record Handling

**Problem:** Your `POST` endpoint needed to prevent duplicate user records from being created.

**Solution:**

* Refactored the `postUser` method to properly check for an existing user.

* Delegated the duplicate check to a new method in the **service layer** (`userExistsByUsername`). This keeps the controller clean and focused on request/response handling.

* Returned a `ResponseEntity` with a **`409 Conflict`** status and a descriptive message when a duplicate is found. This is a RESTful best practice for this type of error.

## 4. Fixing Git "Unrelated Histories" Error

**Problem:** Receiving `fatal: refusing to merge unrelated histories` when trying to push local code to a new remote repository. This happens because the local and remote repositories have separate, independent histories.

**Solution:**

1.  **Pull with the special flag:** Merge the remote's history (the `README.md` file) into your local repository.

    ```
    git pull origin master --allow-unrelated-histories

    ```

2.  **Push your combined history:** Push the newly merged history back to the remote.

    ```
    git push origin master

    ```



# Spring Boot Development Summary

This document serves as a reference for the key Spring Boot concepts and workflows we explored today. It covers important annotations, the architectural pattern we've been following, and the process for integrating with a MySQL database using Docker.

---

## Key Spring Boot Annotations and Their Usage

Here are some of the fundamental annotations that help you configure and structure a Spring Boot application:

### `@Service`
Marks a class as a service component in the Spring application context. It's used to hold your application's business logic, acting as an intermediary between the controllers and the data access layer.

**Reference from our code:**
```java
@Service
public class UserServiceImplDB implements UserService {
    // ... all business logic for user management resides here
}

```
### `@Configuration`

Indicates that a class declares one or more `@Bean` methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime. This is a powerful way to configure your application.

**Example:**

```java
@Configuration
public class AppConfig {
    @Bean
    public MyCustomBean myCustomBean() {
        return new MyCustomBean();
    }
}
```

### `@Qualifier`

Used to resolve a conflict when multiple beans of the same type exist. It works with `@Autowired` to specify exactly which bean should be injected.

**Example:**

```java
@Autowired
@Qualifier("secondaryService")
private MyService myService;
```

### `@EnableConfigurationProperties`

Enables support for `@ConfigurationProperties` classes. It's often used on an `@Configuration` class to enable the binding of properties from a configuration file (like `application.properties`) to a Java object.

**Example:**

```java
@Configuration
@EnableConfigurationProperties(DatabaseProperties.class)
public class AppConfig {
    // ...
}
```

### `@ConstructorBinding`

Used to indicate that a specific constructor should be used for binding configuration properties to a class. This is important when you want to use immutable classes with `final` fields for your configuration.

**Example:**

```java
@ConfigurationProperties(prefix = "myapp")
public record MyProperties(String apiKey, int timeout) {}
```

### `@JsonIgnoreProperties`

A Jackson annotation used to ignore properties during serialization and deserialization. It is useful for excluding fields that you don't want to expose in your JSON output.

**Example:**

```java
@Data
@JsonIgnoreProperties({"password", "secretToken"})
public class userModel {
    private String firstName;
    private String password;
    // ...
}
```

-----

## Understanding the Spring Boot Application Architecture

A well-designed Spring Boot application follows the **Separation of Concerns** principle. We discussed how to achieve this with three key components:

### The Controller Layer

This is the entry point for all incoming HTTP requests. Its sole responsibility is to handle the request, delegate business logic to the service layer, and return the appropriate HTTP response. It should not contain any business logic itself.

### The Service Layer

This layer contains all the business logic for your application. A service class takes the data from the controller, processes it (e.g., checks for duplicates, formats data, performs calculations), and then interacts with the data layer.

**Reference from our code:**
The `UserServiceImplDB` class is a perfect example. It contains the logic for posting a new user, checking for duplicates, and handling data persistence.

### The Mapper Layer

Mappers are responsible for converting data between different representations. For instance, a `UserEntityMapper` (as seen in our stack trace) would map data retrieved from a database entity to a `userModel` object that is used by the rest of the application. This decouples your business logic from your data storage implementation.

-----

## Database Integration Workflow (MySQL with Docker)

This is the standard workflow to integrate your Spring Boot application with a MySQL database running in a Docker container.

### Step 1: Add the MySQL Connector Dependency

First, add the MySQL JDBC driver to your `pom.xml` to allow your application to connect to the database.

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.30</version>
</dependency>
```

### Step 2: Create a `docker-compose.yml` file

Create a `docker-compose.yml` file in your project's root directory. This file will define the services needed to run your database.

```yaml
version: '3.8'

services:
  mysqldb:
    image: mysql:8.0
    restart: always
    environment:
      - MYSQL_ROOT_PASSWORD=root
      - MYSQL_DATABASE=mydatabase
    ports:
      - "3306:3306"
```

### Step 3: Configure `application.properties`

In your `src/main/resources/application.properties` file, configure your Spring Boot application to connect to the MySQL container.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydatabase
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Step 4: Run the Database and Application

1.  Open a terminal in the directory containing `docker-compose.yml` and start the database:
    ```bash
    docker-compose up -d
    ```
2.  Start your Spring Boot application from IntelliJ or the command line. It will automatically connect to the running MySQL container.

<!-- end list -->

If ever stuck in installation invalidate caches and restart
then `mvnw clean install`
