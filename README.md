# Friend Management API

## Prerequisites
  * Oracle Java 8 installed and `JAVA_HOME` set or `java` executable in `PATH`

## Running the App
### On Windows
`./gradlew.bat run` in root of project  directory
### On Linux
`./gradlew run` in root of project  directory

### App Details
Server will start running at `http://localhost:8080` with default configuration.

API endpoints are at `http://localhost:8080/api/v1/*`


## Configuration
Configuration is perform with a Java properties file specified within the `run` task in `build.gradle`. Default configuration is at `config/config.ini`

## Documentation
Endpoints, requests, responses are documented using Swagger [here](http://localhost:8080/swagger/index.html) (default server port is 8080)

## Technical Choices
  * Embedded Jetty for server and servlets
  * JSON-Simple for (de)serialisation of JSON request and responses
  * MongoDB for persistence
  * Gradle for dependency management and scripting