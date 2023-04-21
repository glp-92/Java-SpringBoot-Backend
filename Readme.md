# Java SpringBoot Backend

Microservice-based architecture that implements:
- Auth Microservice which retrieves Jwt Token to access the other services.
- API Gateway to filter and redirect requests.
- Service Discovery with Eureka to hide IP on requests between microservices, using only Gateway URI.
- Simple Store Service to tests the endpoints, auth and redirect.


## Java version: 17
## Spring Boot: 3.0.5


## API
- Auth Service Endpoints
| Endpoint | Method | Param | Body | Response | Functionality |
| --- | --- | --- | --- | --- | --- |
| /auth/register | POST | --- | {"username": "xx", "email": "xx@...", "password": "XX"} | 201, 500 | User Register |
| /auth/login | POST | --- | {"username": "xx", "password": "XX"} | JwtToken(STRING) | User Login which retrieves token |
| /auth/validate (test) | GET | "token": JwtToken | --- | 200, 400 | Test token validation (not for production) |
- Store API Endpoints
| Endpoint | Method | Param | Body | Response | Functionality |
| --- | --- | --- | --- | --- | --- |
| /cart | GET | --- | --- | 200, 500 | Get all carts |
| /cart | POST | --- | {"products": []} | 201, 500 | Create a cart and optional add products |
| /cart/{cartId}/products/{productId} | PUT | --- | --- | 200, 500 | Add a product on existing cart |
| /cart/{cartId}/products/{productId} | DELETE | --- | --- | 200, 500 | Delete a product on existing cart |
| /cart/{cartId} | DELETE | --- | --- | 200, 500 | Delete a cart |
| /products | GET | --- | --- | 200, 500 | Get all products |
| /products | POST | --- | {"name": "XX", "price": 0, "stock": 0} | 201, 500 | Create a product |
| /products/{productId} | DELETE | --- | --- | 200, 500 | Delete a product |


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