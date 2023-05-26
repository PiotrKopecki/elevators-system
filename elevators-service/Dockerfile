FROM openjdk:17-alpine as builder
WORKDIR /app
COPY . .
RUN ./gradlew build --stacktrace

FROM openjdk:17-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/build/libs/elevators-service-0.0.1-SNAPSHOT.jar .
CMD java -jar elevators-service-0.0.1-SNAPSHOT.jar