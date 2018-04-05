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

## Design Considerations of System
1. Separation of concerns between Actions and request/reponse mechanisms
  * JSON parsing and response properties setting encapsulated in FriendManagementServlet
  * Action routing and extraction of action arguments in ActionHandler
  * Action logic encapsulated in XAction classes e.g. (AddFriendAction) 
2. Ablility to switch out persistence implementations for easy testing
  * Instantiation of persistence handler through reflection
  * HashMapUserPersistence for testing
3. Easy basic configuration of route to Action objects
  * Added PropertiesHelper to parse config for routes and Actions enum



## JSON response proposals
1. Create friend connection
  * If any email address doesn't exist as a user
    * return `{"notUser" : [email1, email2]}`
2. Retrieve friend list
  * If requested email address doesn't exist as a user
    * return `{"notUser" : [email]}`
3. Get common friends
  * If either email adress doesn't exist as a user
    * return `{"notUser" : [email1, email2]}`
  * If the two email addresses are the same
    * return `{"sameUser": true, "friends":[...]}`
4. Subscribe to updates
  * If target doesnt exist as a user
    * return `{"notUser" : [target_email]}`
  * If requestor doesn't exist as a user
    * return `{"notUser" : [requestor_email]}`
5. Block updates
  * If target doesnt exist as a user
    * return `{"notUser" : [target_email]}`
  * If requestor doesn't exist as a user
    * return `{"notUser" : [requestor_email]}`
  * If requestor is already friends with target but blocks target
    * return `{"isFriend": true}`
6. Retrieve recipients
  * If @ mentioned email is not a user
    * return `{"notUser" : [email1, email2, ...]}`
  * If recipient is already friends with sender but blocks sender
    * return `{"recipient": recipientEmail, "isFriend": true}`
  * If recipient is already subscribed to sender but blocks sender
    * return `{"recipient": recipientEmail, "isSubscribed": true}`

Current implementation does not check whether user exists unless it checks the friend list, blocked list or list of subscribers.
