# Java SpringBoot Backend

Microservice-based architecture that implements:
- Auth Microservice which retrieves Jwt Token to access the other services.
- API Gateway to filter and redirect requests.
- Service Discovery with Eureka to hide IP on requests between microservices, using only Gateway URI.
- Simple Store Service to test endpoints, auth and redirect.


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
[Deploy backend on containers](_doc_/DockerDeploy.md)

## AWS Deploy
[Deploy containers on EC2 instance](_doc_/AWSDeploy.md)

## Future Improvements:
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