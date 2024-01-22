# aot-database-issue
Database issue with spring boot 3.2.0 and native image: not a managed type

## 1. Introduction
This example spring boot project demonstrates differences when building native images using default
JPA or custom datasources.

### 1.1 Requirements
Uses Spring Boot 3.2.0 and GraalVM 21.
Uses PostgreSQL DB on localhost.

### 1.2 Usage
Run either with JVM by executing `mvn spring-boot:run` or as native image by compiling it with `mvn -Pnative native:compile` and executing generated plattform binary.

After application has been started it will create an entry in table `mydata` by processing code in `DatabaseApplication` which is annotated with `@PostConstruct`.

**Note:** Please run with default JPA first, to make sure schema `graal` is being created.

## 2. Database setup
### 2.1 Default spring JPA
To run example with default JPA, follow these steps:
* Comment out code from class `com.example.database.hikari.GraalDbConfig`:

```java
/*
@EnableJpaRepositories(
        entityManagerFactoryRef = "graalEMF",
        basePackages = { "com.example.database.entity" }
)
@Configuration
 */
public class GraalDbConfig {
    /*
            ... 8< ...
            ... >8 ...
     */
}
```
* Use corresponding datasource definition in `application.yaml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/graal
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
  jpa.hibernate.ddl-auto: update
```
* Build and run application

`mvn clean spring-boot:run` or `mvn clean -Pnative native:compile` and run executable from `target`

Both ways to run application will be successfull.

### 2.2 Custom datasource
To run example with custom datasource, follow these steps:
* Uncomment any code from class `com.example.database.hikari.GraalDbConfig`
* Use corresponding datasource definition in `application.yaml`

```yaml
graal:
  datasource:
    jdbcUrl: jdbc:postgresql://localhost:5432/graal
    driverClassName: org.postgresql.Driver
    username: postgres
    password: postgres
```

* Build and run application

`mvn clean spring-boot:run` or `mvn clean -Pnative native:compile` and run executable from `target`

`mvn clean spring-boot:run` will run successfully. However, executing the native binary will fail with

```
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'databaseApplication': Unsatisfied dependency expressed through field 'repository': Error creating bean with name 'myDataRepository': Not a managed type: class com.example.database.entity.MyData
        at org.springframework.beans.factory.aot.AutowiredFieldValueResolver.resolveValue(AutowiredFieldValueResolver.java:194) ~[na:na]
        at org.springframework.beans.factory.aot.AutowiredFieldValueResolver.resolveObject(AutowiredFieldValueResolver.java:154) ~[na:na]
        at org.springframework.beans.factory.aot.AutowiredFieldValueResolver.resolve(AutowiredFieldValueResolver.java:143) ~[na:na]
        at com.example.database.DatabaseApplication__Autowiring.apply(DatabaseApplication__Autowiring.java:17) ~[na:na]
        ...
```

