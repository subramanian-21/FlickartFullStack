# Flickart - Backend

## Description

The backend of the eCommerce application is built using **Java Servlets** and is responsible for managing user authentication, product listings, user cart details, and product reviews. It interacts with a **MySQL** database and uses **JWT** for secure authentication. All responses are sent as JSON using **Gson**.

## API Endpoints

### User

- **POST** `/api/user/signup`: Register a new user.
- **POST** `/api/user/login`: Login and obtain a JWT token.
- **POST** `/api/user/refresh`: Post refresh Token and get new access and refresh tokens.

### Products

- **GET** `/api/user/product/getAll`: Fetch all products.
- **GET** `/api/user/products/:id`: Fetch a single product by ID.

### Cart

- **POST** `/api/user/cart/add`: Add a product to the user's cart.
- **PUT** `/api/user/cart/update`: Update the quantity of a product in the cart.
- **DELETE** `/api/cart/`: Remove a product from the cart.

### Reviews

- **POST** `/api/user/product/review/`: Fetch reviews for a product.

### Admin

- **POST** `/api/admin/login`: Login and obtain a JWT token.
- **POST** `/api/admin/products/add`: Add a product.
- **POST** `/api/admin/products/add`: Add a product.
- **PUT** `/api/admin/products/:productId`: Update a product.
- **GET** `/api/admin/product/getAll`: Fetch all products.
- **GET** `/api/admin/products/:id`: Fetch a single product by ID.

## Database Schema

### User Table

Here’s a cleaned-up and aligned version of your `Users` table schema:

### Users Table Schema

| Field        | Type         | Null | Key | Extra                  |
|--------------|--------------|------|-----|------------------------|
| userId       | varchar(30)  | NO   | PRI |                        |
| userName     | varchar(100) | YES  |     |                        |
| email        | varchar(100) | YES  | UNI |                        |
| password     | varchar(100) | YES  |     |                        |
| createdAt    | timestamp    | YES  |     | current_timestamp()    |
| profilePhoto | varchar(255) | YES  |     | Default avatar URL     |

### Product Table

Here’s the cleaned-up and aligned version of your `Products` table schema:

### Products Table Schema

| Field              | Type          | Null | Key | Extra                  |
|--------------------|---------------|------|-----|------------------------|
| productId          | varchar(30)   | NO   | PRI |                        |
| productName        | varchar(100)  | YES  |     |                        |
| productDescription | text          | YES  |     |                        |
| price              | decimal(10,2) | YES  |     |                        |
| stock              | int(11)       | YES  |     |                        |
| createdAt          | timestamp     | YES  |     | current_timestamp()    |
| image              | varchar(255)  | YES  |     |                        |
| category           | varchar(50)   | YES  |     |                        |
| brand              | varchar(50)   | YES  |     |                        |
| discount           | decimal(5,3)  | YES  |     |                        |
| warranty           | varchar(50)   | YES  |     |                        |
| rating             | decimal(3,2)  | YES  |     |                        |
| ratingCount        | int(11)       | YES  |     |                        |

### Products Images Schema

Here’s the cleaned-up and aligned version of your `Images` table schema:

### Product Images Table Schema

| Field     | Type         | Null | Key | Extra          |
|-----------|--------------|------|-----|----------------|
| imageId   | int(11)      | NO   | PRI | auto_increment |
| productId | varchar(50)  | NO   | MUL |                |
| imageUrl  | varchar(255) | NO   |     |                |


### Cart Table Schema

| Field       | Type          | Null | Key | Extra                         |
|-------------|---------------|------|-----|-------------------------------|
| cartId      | varchar(30)   | NO   | PRI |                               |
| userId      | varchar(30)   | NO   | MUL |                               |
| totalAmount | decimal(10,2) | YES  |     |                               |
| createdAt   | timestamp     | YES  |     |                               |
| updatedAt   | timestamp     | YES  |     | on update current_timestamp() |

### Cart Items Table Schema

| Field       | Type          | Null | Key | Extra          |
|-------------|---------------|------|-----|----------------|
| cartItemId  | int(11)       | NO   | PRI | auto_increment |
| cartId      | varchar(30)   | NO   | MUL |                |
| productId   | varchar(30)   | NO   | MUL |                |
| quantity    | int(11)       | NO   |     |                |
| price       | decimal(10,2) | NO   |     |                |


### Review Table

| Field      | Type          | Null | Key |
|------------|---------------|------|-----|
| reviewId   | varchar(50)   | NO   | PRI |
| rating     | decimal(3,2)  | YES  |     |
| comment    | varchar(255)  | YES  |     |
| userId     | varchar(50)   | YES  | MUL |
| productId  | varchar(50)   | YES  | MUL |
| timestamp  | timestamp     | YES  |     |
| userName   | varchar(50)   | YES  |     |

## JWT Authentication

For API routes that require authentication, a valid JWT must be sent in the `Authorization` with `Bearer <ACCESS_TOKEN>` header:

