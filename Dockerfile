FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/biblioteca-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8180

ENTRYPOINT ["java", "-jar", "app.jar"]


