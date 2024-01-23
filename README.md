# native-multiple-databases

## 1. Introduction
This sample project show how to use multiple database with spring boot and get it running with native images, successfully.

### 1.1 Requirements
Uses Spring Boot 3.2.0 and GraalVM 21.
Uses PostgreSQL DB on localhost. Adopt database to your needs.

Spring Boot 3.2.0 was choosen on purpose, because
* 3.2.1 has an issue with OAuth: https://github.com/spring-projects/spring-security/issues/14362
* 3.2.2 has an issue with `Unpaged`: https://github.com/spring-projects/spring-data-commons/issues/3025

### 1.2 Usage
Compile with `mvn -Pnative native:compile`. Run by executing native platform binary from directory `target`.

After application has been started it will create an entry in table `mydata` of both databases by processing code in `DatabaseApplication` which is annotated with `@PostConstruct`.

## 2. Database setup
Two datasources are configured in `application.yaml`:
```yaml
graal1:
  datasource:
    jdbcUrl: jdbc:postgresql://localhost:5432/graal
    driverClassName: org.postgresql.Driver
    username: postgres
    password: postgres
```
```yaml
graal2:
  datasource:
    jdbcUrl: jdbc:postgresql://localhost:5432/graal2
    driverClassName: org.postgresql.Driver
    username: postgres
    password: postgres
```

Pay attention to parameter `@Qualifier("graalPMT")PersistenceManagedTypes persistenceManagedTypes` of `LocalContainerEntityManagerFactoryBean` in database configurations. This is required to prevent `Not a managed type` exception message, heavily documented on StackOverflow when running as a native app.

Spring documentation about it can be found here: https://docs.spring.io/spring-framework/reference/core/aot.html#aot.bestpractices.jpa