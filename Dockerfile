FROM gradle:8.6.0-jdk21 as builder

WORKDIR /app

COPY . .

FROM builder as test

RUN gradle test

FROM builder as build

RUN gradle build


FROM openjdk:21-jdk as runtime

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
