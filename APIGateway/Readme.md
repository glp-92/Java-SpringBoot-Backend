# Gateway Service

API Gateway Service using Spring Cloud Gateway to redirect requests to services.


## Java version: 17
## Spring Boot: 3.0.5


### Eureka Discovery config (application.properties)
``` 
server.port=9000
spring.application.name=API-GATEWAY
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
``` 


### Modules
- `AuthenticationFilter.java` Filter that checks for valid Bearer Jwt Token to grant access to secured endpoints. If endpoint is not secured, allows access (/register, /login, ...)
- `RouteValidator.java` Contains a list of non-secured endpoints and a Predicate (bool) that checks if the endpoint of the request is contained on non-secured endpoints.
- `JwtUtil.java` Contais functions and variables to validate bearer Jwt token.


### Gateway Config
```
   gateway:
     routes:
       - id: AUTH-SERVER
         uri: lb://AUTH-SERVER
         predicates:
           - Path=/auth/**
       - id: STORE-SERVICE
         uri: lb://STORE-SERVICE
         predicates:
           - Path=/**
         filters:
           - AuthenticationFilter
```
- Uri is referenced to Eureka Discovery Server, so IP of the other services is hidden.
- Predicates are rules used for routing:
    | Rule | Meaning | 
    | --- | --- | 
    | Path | Allows redirect to requests that match declared path |
    | Header | Checks some header value to allow redirect |
    | Method | Checks method of the request (GET, POST...) to allow redirect |
    | Query | Query params must appear to allow redirect |
    | Cookie | Checks if some cookie is present to allow redirect |
- Filters to manipulate the http request or response or rewrite request path. Global filters are commonly used for loggin. Here AbstractGatewayFilterFactory is used to match only some requests, and check Jwt Token validation to continue the chain.


### Future Improvements:
- Role and user extract on Jwt.
- Dockerize with config params secured.
- Check predicates and filters to improve security.