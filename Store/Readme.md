# Simple Store Microservice Example

This microservice implements a simple store wich includes cart and products with relation 'Many to Many' with these entities by using and intermediate table CartProducts.


## Java version: 17
## Spring Boot: 3.0.5


### Eureka Discovery config (application.properties)
``` 
server.port=7000
spring.application.name=STORE-SERVICE
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
``` 


### Modules
- `CartController.java` Endpoints to create/erase cart and add/erase products from cart.
    | Endpoint | Method | Param | Body | Response |
    | --- | --- | --- | --- | --- |
    | /cart | GET | --- | --- | 200, 500 |
    | /cart | POST | --- | {"products": []} | 201, 500 |
    | /cart/{cartId}/products/{productId} | PUT | --- | --- | 200, 500 |
    | /cart/{cartId}/products/{productId} | DELETE | --- | --- | 200, 500 |
    | /cart/{cartId} | DELETE | --- | --- | 200, 500 |
- `ProductController.java` Endpoints to create/erase products.
    | Endpoint | Method | Param | Body | Response |
    | --- | --- | --- | --- | --- |
    | /products | GET | --- | --- | 200, 500 |
    | /products | POST | --- | {"name": "XX", "price": 0, "stock": 0} | 201, 500 |
    | /products/{productId} | DELETE | --- | --- | 200, 500 |
- `CartRepository.java` `ProductRepository.java` extends Jpa to query on DB.
- `Cart.java`
    | Column | Type |
    | --- | --- |
    | products | List of Products |
- `Product.java` entity that includes functions to increase/decrease stock
    | Column | Type |
    | --- | --- |
    | name | String |
    | price | double |
    | stock | int |
- `CreateCart.java` `CreateProduct.java` intermediate class to control variable exchange.
- `CartService.java` implements methods to create, update, delete cart and update products on cart.
- `ProductService.java` implements methods to create, update and delete products.


### Future Improvements:
- Cart using hashset of products.