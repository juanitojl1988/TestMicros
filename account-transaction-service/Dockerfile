# → 1) BUILD STAGE
FROM jelastic/maven:3.9.5-openjdk-21 AS build
WORKDIR /workspace/app

# cache Maven deps
COPY pom.xml .
RUN mvn dependency:go-offline -B

# package the app
COPY src ./src
RUN mvn clean package -DskipTests -B

# → 2) RUNTIME STAGE
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# grab the JAR
COPY --from=build /workspace/app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
