# Game of three


## Project overview

### Architecture and technologies used

- Java 8
- Spring Boot
- Maven

### Build and run

The easiest way to build and run the application is, with Maven installed and cofigured, go to the target directory of the respective project using command prompt and type 
the following command:

```shellscript
java -jar gameofthree-0.0.1-SNAPSHOT.jar
```
```shellscript
java -jar gameofthree-0.0.1-SNAPSHOT.jar --spring.config.name=application_s2
```
> Ps: The first command is for service one and the second is for service two

After this go to URL Address or you can use any API Development Environment such as Postman and start the game:

(Example for service one)
```
 http://localhost:8080/api/gameofthree/start/true
 ```
 > Ps: You can see more details in the API documentation

### Documentation

Swagger 2 was chosen for the API documentation can be access by:

```url service one
http://localhost:8080/api/gameofthree/swagger-ui.html
```
> Ps: url for service one

```url service two
http://localhost:9000/api/gameofthree/swagger-ui.htm
```
> Ps: url for service two

### Project Instructions

Read [Game of Three - Scoober Coding Challenge JAVA.pdf](Game%20of%20Three%20-%20Scoober%20Coding%20Challenge%20JAVA.pdf)

