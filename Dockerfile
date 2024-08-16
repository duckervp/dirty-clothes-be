FROM eclipse-temurin:21-jre-alpine
COPY target/shop-0.0.1-SNAPSHOT.jar shop-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/shop-0.0.1-SNAPSHOT.jar"]