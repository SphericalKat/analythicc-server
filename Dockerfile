FROM openjdk:11 AS builder
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradlew /app/
COPY gradle /app/gradle
RUN ./gradlew build || return 0 // dummy task to cache dependencies
COPY . /app
RUN ./gradlew shadowJar

FROM openjdk:8-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/analythicc-uber-0.0.1-all.jar /app
EXPOSE 8080
CMD ["java", "-jar", "analythicc-uber-0.0.1-all.jar"]