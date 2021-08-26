# ByteWorks-Food-App
This is a multivendor food ordering platform.


### Technologies
- Java
- Maven
- Springboot
- Spring Security
- Swagger
- PostgreSQL
- Docker
- Redis

### Requirements

You need the following to build and run the application:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3.8.1](https://maven.apache.org) (Optional as code already contains maven wrapper)
- [PostgreSQL](https://www.postgresql.org/download)
- [Redis](https://redis.io/download)

You could just run it on docker. Hence you would require to have install [docker](https://www.docker.com/products/docker-desktop)

### How to run (with docker)
#### step 1 - clone project with from [here](https://github.com/firsthus/ByteWorks-Food-App/)

```
    git clone https://github.com/firsthus/ByteWorks-Food-App.git
```


#### step 2 - move into the project directory
```
    cd ByteWorks-Food-App/
```

#### step 3 - Generate the .jar file
```
    mvn clean install
```

#### step 4 - run the project on docker
```
    docker-compose up
```


___
I have used the JWT for security but for the ease of use and to test the functionality of the food app,
I have intentionally made all endpoints open (not secured).

>###### Full Swagger documentation can be found [here](http://localhost:8080/swagger-ui/#/) 



