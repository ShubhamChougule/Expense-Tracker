FROM amazoncorretto:21

WORKDIR /app

COPY /target/et-expense-reports-service-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]