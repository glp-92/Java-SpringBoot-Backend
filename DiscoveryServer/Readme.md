# Discovery Service

Eureka is a component of Spring Boot that allows the implementation of a server for service registration and discovery in microservices-based applications. It provides a registry of services and their locations, which enables clients to discover and interact with them. By using Eureka, developers can build scalable and distributed microservices architectures, where services can be dynamically discovered and routed based on their availability and load.

## Java version: 17
## Spring Boot: 3.0.5

### Modules
- `DiscoveryServerApplication.java` is needed to add header @EnableEurekaServer to run the service.

### Discovery Config
```
eureka:
  client:
    fetch-registry: false
    register-with-eureka: false
server:
  port: 8761
```