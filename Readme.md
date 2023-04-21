# Java SpringBoot Backend

Microservice-based architecture that implements:
- Auth Microservice which retrieves Jwt Token to access the other services.
- API Gateway to filter and redirect requests.
- Service Discovery with Eureka to hide IP on requests between microservices, using only Gateway URI.
- Simple Store Service to tests the endpoints, auth and redirect.


## Java version: 17
## Spring Boot: 3.0.5


## API
By using the API Gateway URI: `http://localhost:9000`
- Auth Service Endpoints
    | Endpoint | Method | Param | Body | Response |
    | --- | --- | --- | --- | --- |
    | /auth/register | POST | --- | {"username": "xx", "email": "xx@...", "password": "XX"} | 201, 500 |
    | /auth/login | POST | --- | {"username": "xx", "password": "XX"} | JwtToken(STRING) |
    | /auth/validate (test) | GET | "token": JwtToken | --- | 200, 400 |
- Store API Endpoints
    | Endpoint | Method | Param | Body | Response |
    | --- | --- | --- | --- | --- |
    | /cart | GET | --- | --- | 200, 500 |
    | /cart | POST | --- | {"products": []} | 201, 500 |
    | /cart/{cartId}/products/{productId} | PUT | --- | --- | 200, 500 | 
    | /cart/{cartId}/products/{productId} | DELETE | --- | --- | 200, 500 | 
    | /cart/{cartId} | DELETE | --- | --- | 200, 500 |
    | /products | GET | --- | --- | 200, 500 |
    | /products | POST | --- | {"name": "XX", "price": 0, "stock": 0} | 201, 500 |
    | /products/{productId} | DELETE | --- | --- | 200, 500 |

## Docker
To build a container for every service you can do the following steps:
- Go to a service folder (for example Discovery server): 
    ```cd DiscoveryServer```
- Is needed a .jar file containing the application, to do that on Windows: 
    ```./mvnw package``` 
- This will compile and execute tests, and generate a .jar file on `/target/discovery...jar`
- To execute .jar file:
    ```java -jar .\target\discovery-...jar```
- Create a `dockerfile` and add the following lines:
    ```
    FROM eclipse-temurin:17-jdk-alpine
    VOLUME /tmp
    COPY target/discovery...jar app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]
    ```
    - FROM: docker image used to build the container image. This one is based on Alpine Linux.
    - VOLUME: creating a volume on the image on /tmp dir.
    - COPY: copy the .jar file to the container and change the name to `app.jar`.
    - ENTRYPOINT: command to run the app, every word on the array is an instruction separated by comma.
- Create the image by running:
    ```
    docker build -t myorg/myapp .
    ```
    - docker build: creates an image by using a dockerfile.
    - -t myorg/myapp: labelling image with the name specified.
    - .: dockerfile is located in the same directory.

### Future Improvements:
- All
    - Dockerize services with config params secured.
- Auth Service
    - Role implementation.
    - Logout.
    - Destroy Jwt when init service
    - SQL Database instead H2.
- Store Service
    - SQL Database instead H2.
    - Cart using hashset of products.
- API Gateway
    - Role and user extract on Jwt.
    - Check predicates and filters to improve security.