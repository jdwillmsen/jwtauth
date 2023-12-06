# JWT Auth
[![Maven CI](https://github.com/jdwillmsen/jwtauth/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/jdwillmsen/jwtauth/actions/workflows/ci.yml) \
A JSON web token (JWT) based authentication and authorization backend. This backend service provides a locked down api 
for managing and controlling users and roles. It is locked down by use of Spring Security to ensure that all requests
have proper authentication and authorization. This service also makes use of spring actuator to provide some baseline 
system management endpoints, such as health check and logging. 

## Running the app
### Prerequisite
- Maven 3.9.x+ installed
- Java 21+ installed
- Docker installed

### Database
In order to run the app locally you will need to have a database to point to.

Run the following to have a standalone database that contains all the data.
```bash
docker run \
  -p 5432:5432 \
  -d jdwillmsen/jwtauth-postgres:latest
```
### Java
In order to run the application you must package/jar the application.
```bash
# Without maven installed
./mvnw clean package &&
java -jar <path-to-jar>
```
or
```bash
# With maven installed
mvn clean package &&
java -jar target/jwtauth-0.0.1-SNAPSHOT.jar
```

## Releases
This project makes use of maven's release plugin to automatically tag and publish a proper package to GitHub's packages.

### Local
To release the app from your local repository you will need to setup two variables:
```env
setx CICD_USERNAME=<username>
setx CICD_PASSWORD=<password>
```
Then the following can be run to perform the release:
```bash
./mvnw -s settings-local.xml -B release:clean release:prepare release:perform
```

## Project Info
Started November 27th 2023

### Developers
- Jake Willmsen
