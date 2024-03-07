# Auth Engine Api

## Description

This is a simple API that allows you to authenticate users. It was developed using Java and Spring Boot, and can be built and run with Gradle.

## Pre-requisites

- Java 17
- Gradle 8.2
- Latest version of Postgres
- Docker (optional)

## Installation

1. Clone the repository: `git clone https://github.com/SXPJB/auth-engine-api`
2. Open the project in your favorite IDE (IntelliJ IDEA is recommended)
3. Run boostrap script: `./bootstrap.sh` in case of Linux or `docker-compose up` in case of Windows
4. Download the dependencies with Gradle: `./gradlew build`
5. Run the application: `./gradlew run`
6. Create .env file
7. Enjoy!

## .env file
Remember to replace the values with your own configuration
```
    DB_HOST
    BD_NAME
    DB_USERNAME
    DB_PASSWORD
    ADMIN_USERNAME
    ADMIN_PASSWORD

    MAIL_HOST
    MAIL_PORT
    MAIL_USERNAME
    MAIL_PASSWORD

    SERVER_PORT
    CONTEXT_PATH=/api
    DOMAIN_NAME

    JWT_SECRET
    JWT_EXPIRATION
```

## Project Structure

The project follows the standard structure of a Spring Boot project with Gradle:

- `src/main/java`: This is where all the Java source code files are located.
- `src/main/resources`: This is where resource files, such as application configurations, are located.
- `src/test`: This is where all the test files are located.

## How to Use

Once the application is running, you can interact with it through its API. Here are some examples of how to do it:

- Register a new user: `POST /api/v1/users`
- Confirm user: `POST /api/v1/confirm-user/{username}/{confirmationCode}`
- Authenticate a user: `POST /api/v1/auth`

Please refer to the API documentation for more details on how to interact with it.
