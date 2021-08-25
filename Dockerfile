FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/byteworks-food-app.jar  byteworks-food-app.jar
ENTRYPOINT ["java", "-jar", "byteworks-food-app.jar"]