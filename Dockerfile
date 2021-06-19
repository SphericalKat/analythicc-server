FROM openjdk:11 AS builder
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradlew /app/
COPY gradle /app/gradle
RUN ./gradlew build || return 0 // dummy task to cache dependencies
COPY . /app
RUN ./gradlew shadowJar

FROM adoptopenjdk:11-jre
WORKDIR /app
COPY --from=builder /app/build/libs/analythicc-uber.jar /app
EXPOSE 8080
CMD ["java", "-jar", "analythicc-uber.jar"]