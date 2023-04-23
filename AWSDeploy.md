# Deploy Backend to AWS EC2

EC2 instance will be used to deploy these services. Amazon ECS and Amazon RDS seems a better way to scale microservices architectures but that will be part of future improvements.

## Step by step Deployment
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
3. Create a `Security Group` to determine which ports will be opened and how all computers must interact between them:
    - EC2 Control Panel => Network & Security => Security Groups => Create Security Group
        - Name: Spring Microservices
        - Input Rules:
            - TCP, port 9000 (API Gateway), origin anywhere (0.0.0.0/0) (or whitelisted ip for use in production)
            - TCP, port 8761 (Eureka, not for production), origin anywhere (0.0.0.0/0) (or enterprise IP to monitor microservices state)
            - SSH, port 22, origin own IP or enterprise so no-one outside can access the group instances.
3. Create an EC2 instance on AWS (needed user account, or academy)
    - Launch EC2 instance.
        - Name: SpringBoot Microservices.
        - Amazon Linux (free tier).
        - T2 Micro instance (1GB Ram, 1 Core, free tier, might be slow)
        - Key pair: vockey.
        - Storage: 20GB.
4. Connect to the EC2 instance by ssh or by using browser AWS pannel.
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
7. Specify as `image` the tag of docker-hub created images. Linux doesn´t resolve the DNS `host.docker.internal` so `extra_hosts` is added to the file, so we are telling Docker to map the hostname host.docker.internal to the default gateway's IP address of the host:
    ``` 
    services:
        discovery-service:
            container_name: discovery-service
            build: ./DiscoveryServer
            ports:
                - "8761:8761"
            extra_hosts:
                - host.docker.internal:host-gateway
            image: guill392/discovery-service:latest
        api-gateway:
            container_name: api-gateway
            build: ./APIGateway
            ports:
                - "9000:9000"
            extra_hosts:
                - host.docker.internal:host-gateway
            depends_on:
                - discovery-service
            image: guill392/api-gateway:latest
        auth-service:
            container_name: auth-service
            build: ./AuthRegService
            extra_hosts:
                - host.docker.internal:host-gateway
            depends_on:
                - api-gateway
            image: guill392/auth-service:latest
        store-service:
            container_name: store-service
            build: ./Store
            extra_hosts:
                - host.docker.internal:host-gateway
            depends_on:
                - auth-service
            image: guill392/store-service:latest
    ```
8. Run containers, will pull images from docker-hub:
    ```
    sudo docker-compose up
    ```
