# Deploy Backend to Docker Containers

Due to portability, scalability and consistency is useful to use containers. There are some steps for doing this:

1. Go to a service folder (for example Discovery server): 
    ```cd DiscoveryServer```
2. Is needed a .jar file containing the application, to do that on Windows: 
    ```./mvnw package``` 
3. This will compile and execute tests, and generate a .jar file on `/target/discovery...jar`
    - To execute .jar file:
        ```java -jar .\target\discovery-...jar```
4. Create a `dockerfile` and add the following lines:
    ```
    FROM eclipse-temurin:17-jdk-alpine
    EXPOSE 8761
    VOLUME /tmp
    COPY target/discovery...jar app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]
    ```
    - FROM: docker image used to build the container image. This one is based on Alpine Linux.
    - EXPOSE: exposes port on the container, which allows external services to communicate with the app running inside the container.
    - VOLUME: creating a volume on the image on /tmp dir.
    - COPY: copy the .jar file to the container and change the name to `app.jar`.
    - ENTRYPOINT: command to run the app, every word on the array is an instruction separated by comma.
5. Create the image by running:
    ```
    docker build -t discovery .
    ```
    - docker build: creates an image by using a dockerfile.
    - -t discovery: labelling image with the name specified.
    - .: dockerfile is located in the same directory.
6. Run the image:
    ```
    docker run -p 8761:8761 discovery
    ```
## Docker Compose
`docker-compose.yml` allows to wake all the services with only one command. It is neccesary to have the `.jar` file created for every service. 
```
services:
    discovery-service:
    container_name: discovery-service
    image: guill392/discovery-service:latest
    build: ./DiscoveryServer
    ports:
        - "8761:8761"
    api-gateway:
    container_name: api-gateway
    image: guill392/api-gateway:latest
    build: ./APIGateway
    depends_on:
        - discovery-service
    ports:
        - "9000:9000"
    environment:
        - SPRING_APPLICATION_NAME=API-GATEWAY
        - EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka
    auth-service:
    container_name: auth-service
    image: guill392/auth-service:latest
    build: ./AuthRegService
    depends_on:
        - api-gateway
    environment:
        - SPRING_APPLICATION_NAME=AUTH-SERVER
        - EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka
    store-service:
    container_name: store-service
    image: guill392/store-service:latest
    build: ./Store
    depends_on:
        - auth-service
    environment:
        - SPRING_APPLICATION_NAME=STORE-SERVICE
        - EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka
```
- services: each container (service) to build is placed into this field.
- container-name: name assigned to service.
- image:  name of the Docker image that will be used for this service. If the image is not found locally or on Docker Hub, the image will be built locally accordingly to `build` param.
- build: the location of the `Dockerfile` that will be used to build the image for this service.
- ports: ports that will be exposed by the container. In this case, API Gateway and Eureka ports are exposed to access these services from outside the virtual network.
- environment: refers to env variables, in this case, to `application.properties` or `application.yaml` config vars.

Build and run all the services by running:
```
docker-compose up
```
To stop and remove containers, networks, volumes and images, run:
```
docker-compose down
```
## Troubleshooting
- When running on docker, localhost is not used internally between containers, so `docker-compose.yml` must be modified. 
    ```
    environment:
        - EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://host.docker.internal:8761/eureka
    ```
- Besides, due to potential DNS resolution issues in Docker, the property `eureka.instance.preferIpAddress=true` is added to all the Eureka clients on `application.properties`.
    ```
    eureka:
    client:
        service-url:
        defaultZone: http://localhost:8761/eureka/
    ```
    to
    ```
    eureka:
    instance:
        preferIpAddress: true
    ```