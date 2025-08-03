# spring boot

to get started all you need is 

```java
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
``` 


but this will throw exception as no controller attached to it.
No action or interaction defined.
Then Create rest controller.
```java
@RestController
public class UserController {
    @GetMapping("/getUser")
    public String getUser(){
        return "avi";
    }
}

```

issue seen
`0:00","status":406,"error":"Not Acceptable","trace":"org.springframework.web.HttpMediaTypeNotAcceptableException: No acceptable representation`
resolution: mvn clean install


Role of @service is to register class in dependency injection framework
it handles logic like calling external service or handling logic
@Service, so Spring will create and inject the bean successfully.

#### now to get an external http call, have unirest dependency
- First, set up a model for api, can use JsonIgnoreProperties annotation.
- Setup interface for time service
- Then implement the service interface, and make sure to add @Service annotation to it.
- Implement the http call.
- To bind with existing controller class, this service needs to be integrated with User service.
- Create TimeService as an instance variable and part of constructor as well, this way spring knows that it needs to provide a bean for this service as well.
- Now use this timeService to use, by making a call to its implemented methods and you should be good to go.
- The response after post call is done, and get call is done will be
```json
{
    "firstName": "kavi",
    "lastName": "gupta",
    "memberShipId": 3548,
    "createTime": "2025-08-03T11:20:40.3675959"
}
```

#### Setup config

- add @ConfigurationPropertiesScan("com.example.config") to main class.
- Now create a timeapiConfig class.
- this too will have annotations like @ConfigurationProperties(prefix = "api").
- Add @ConstructorBinding only if all the instance variable are final, else skip it. This has to bee added to constructor itself.
- ALso have an empty constructor, In your case, when you removed the @ConstructorBinding annotation and kept the setters, you were telling Spring to use the default setter-based injection. The empty constructor was the crucial missing piece for this default mechanism to work. By adding it, you enabled Spring to create the object and then populate it via your setters, successfully resolving the dependency injection failure.
- 

### change port number
In the yaml file add below
```yaml
server:
    port: 9000
```

## docker
`    docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 -d mysql:latest`
00c7be4a74ab812d5dab8d1aae6ea840eade83949cbf68a8a7990f8a33caa1fb

- `docker ps -> 00c7be4a74ab`

CREATE TABLE user_entity (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(30) NOT NULL,
last_name VARCHAR(30) NOT NULL,
membership_id INTEGER,
creation_time VARCHAR(100) NOT NULL
)
    