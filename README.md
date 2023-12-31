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

### Docker Compose
The application can also be run with docker compose without any configuration.
```bash
# Ran from the root folder
docker compose up -d
```

## Workflows
There are currently 3 workflows that will get run.

### CI
This workflow is called CI for continuous integration. \
This workflow will run all the tests as well as make sure that the application can package properly. \
This workflow is triggered on any push to origin repository.

### Snapshot
This workflow is called Snapshot for deploying a snapshot package to GitHub's packages. \
This workflow will run all the tests, package the application, and upload to GitHub's package as a snapshot. \
This workflow is triggered on any push to origin repository.

### Release
This workflow is called Release for deploying a release package to GitHub's packages. \
This workflow will run the maven release plugin lifecycles and upload to GitHub's package as well as bumping version. \
This workflow is triggered only on pushed to main branch on origin repository.

## Releases
This project makes use of maven's release plugin to automatically tag and publish a proper package to GitHub's packages.

### Local
To release the app from your local repository you will need to setup two variables.
```env
setx CICD_USERNAME=<username>
setx CICD_PASSWORD=<password>
```
Then the following can be run to perform the release.
```bash
./mvnw -s settings-local.xml -B release:clean release:prepare release:perform
```

### JIB
In order to containerize the application the jib-maven-plugin is used.
```bash
mvn compile jib:build
```
or
```bash
mvn compile jib:dockerBuild
```
This will publish a compliant docker image of the application up to dockerhub.

## Future Improvements
This application is quite minimal and can be used more as a template in building out similar systems. But nonetheless these are areas that need improvement:
- Error handling
- Test coverage
- Production/development changes
- Code coverage
- Resource server improvements/separation
- Container/images built into CI/CD
- Integration tests
  - Test Containers (DAO/Repository)
  - WebMVC (Controllers)
  - Full App Context (E2E flows)
  - Etcetera

## Project Info
Started November 27th 2023

### Developers
- Jake Willmsen
