# Auth Service

Spring Security Auth Service using Jwt Token to maintain session.


## Java version: 17
## Spring Boot: 3.0.5


### Eureka Discovery config (application.properties)
``` 
server.port=8080
spring.application.name=AUTH-SERVER
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
``` 


### Modules
- `AuthConfig.java` Implements beans UserDetailsService, SecurityFilterChain, BcriptEncoder, AuthenticationProvider and AuthenticationManager
- `AuthController.java` Endpoints for Login/register/token.
    | Endpoint | Method | Param | Body | Response |
    | --- | --- | --- | --- | --- |
    | /auth/register | POST | --- | {"username": "xx", "email": "xx@...", "password": "XX"} | 201, 500 |
    | /auth/login | POST | --- | {"username": "xx", "password": "XX"} | JwtToken(STRING) |
    | /auth/validate (test) | GET | "token": JwtToken | --- | 200, 400 |
- `RoleRepository.java` `UserRepository.java` extends Jpa to query on DB.
- `User.java`
    | Column | Type |
    | --- | --- |
    | username | String |
    | email | String |
    | password | String |
    | roles | set Roles |
- `Role.java` `Rolename.java` name of roles of enumerated Rolename.
- `AuthRequest.java` `CreateUserRequest.java` intermediate class to control variable exchange.
- `AuthService.java` implements create user, generate token, validate token on auth.
- `JwtService.java` implements token builder, parse token.
- `UserDetailsImpl.java` implements Spring class that manages user attributes like username, password, email...
- `UserDetailsServiceImpl.java` maps an input user data to userDetails if exists, using Jpa repository to find it.


### Future Improvements:
- Role implementation.
- Logout.
- Destroy Jwt when init service
- Dockerize with secret and another params secured.
- SQL Database instead H2.
