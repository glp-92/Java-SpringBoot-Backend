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
`docker-compose.yml` allows to wake all the services with only one command, in this moment, it is neccesary to have the `.jar` file created. To do this, run:
```
docker-compose up
```
To stop and remove containers, networks, volumes and images, run:
```
docker-compose down
```
## Troubleshooting
When running on docker, localhost is not used internally between containers, so `application.yaml` or `application.properties` must be modified. Besides, due to potential DNS resolution issues in Docker, the property `eureka.instance.preferIpAddress=true` is added to all the Eureka clients.

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
  client:
    service-url:
      defaultZone: http://host.docker.internal:8761/eureka
```