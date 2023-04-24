# Deploy Backend to AWS EC2

EC2 instance will be used to deploy these services. Amazon ECS and Amazon RDS seems a better way to scale microservices architectures, but that will be part of future improvements.

It is higly recommended instead of installing dependencies directly on the instance. To learn more about deploying a backend on Docker containers, visit [deploy Backend on Docker containers](DockerDeploy.md)

## Step by step AWS EC2 Deployment
1. Register on Docker Hub:
    To host docker images, register on [Docker Hub](https://hub.docker.com/)
    Login into the account with bash with credentials:
    ```
    docker login
    ```
2. Push images to Docker Hub:
    It is possible to push all the images on one line by using docker-compose. On main local folder execute:
    ```
    docker-compose push
    ```
    Ensure they are uploaded by visit: https://hub.docker.com/repositories/username
3. On AWS, create a `Security Group` to determine which ports will be opened and how all computers will interact:
    - EC2 Control Panel => Network & Security => Security Groups => Create Security Group
        - Name: Spring Microservices
        - Input Rules:
            - TCP, port 9000 (API Gateway), origin anywhere (0.0.0.0/0) (or whitelisted ip for use in production)
            - TCP, port 8761 (Eureka, not for production), origin anywhere (0.0.0.0/0) (or enterprise IP to monitor microservices state)
            - SSH, port 22, origin own IP or enterprise so no-one outside can access the group instances.
3. Create an EC2 instance on AWS (user account or academy needed)
    - Launch EC2 instance.
        - Name: SpringBoot Microservices.
        - Amazon Linux (free tier).
        - T2 Micro instance (1GB Ram, 1 Core, free tier, might be slow)
        - Key pair: vockey.
        - Storage: 20GB.
4. Connect to the EC2 instance by using ssh or browser AWS pannel.
5. Update and install `docker` and `docker-compose`:
    ```
    sudo yum update -y
    sudo yum install docker -y
    sudo systemctl enable docker
    sudo systemctl start docker
    sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/bin/docker-compose
    sudo chmod +x /usr/bin/docker-compose
    ```
6. Create `docker-compose.yml` file
    ```
    nano docker-compose.yml
    ```
7. Specify as `image` the tag of docker-hub images. Linux doesn´t resolve DNS `host.docker.internal` so `extra_hosts` is added, telling Docker to map the hostname `host.docker.internal` to default gateway's IP host address:
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
            - EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://host.docker.internal:8761/eureka
        extra_hosts:
            - "host.docker.internal:host-gateway"
    auth-service:
        container_name: auth-service
        image: guill392/auth-service:latest
        build: ./AuthRegService
        depends_on:
            - api-gateway
        environment:
            - SPRING_APPLICATION_NAME=AUTH-SERVER
            - EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://host.docker.internal:8761/eureka
        extra_hosts:
            - "host.docker.internal:host-gateway"
    store-service:
        container_name: store-service
        image: guill392/store-service:latest
        build: ./Store
        depends_on:
            - auth-service
        environment:
            - SPRING_APPLICATION_NAME=STORE-SERVICE
            - EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://host.docker.internal:8761/eureka
        extra_hosts:
            - "host.docker.internal:host-gateway"
    ```
8. Pull images from docker-hub and run containers:
    ```
    sudo docker-compose up
    ```
8. To stop services and delete images, network and volumes:
    ```
    sudo docker-compose down
    ```