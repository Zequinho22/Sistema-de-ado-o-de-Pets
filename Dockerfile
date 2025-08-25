FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . .
RUN ./mvnw -q -DskipTests package || mvn -q -DskipTests package
EXPOSE 8080
CMD ["java","-jar","target/adote-simples-0.0.1-SNAPSHOT.jar"]
