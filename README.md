# E-commerce UPCH
Download POSTMAN collection [here](https://www.google.com)

## Table of contents
1. [Description](#description)
2. [Admin action](#admin-actions)
3. [User and Admin actions](#user-and-admin-actions)
4. [Authentication](#authentication)

## Description
Our project bases on an ecommerce, where people can buy any product they want; within the platform, these users can look for their products, whether by title/description or category.
Admins can access to their product management.

## Important points before to continue
If you want to get token as admin, use the following credentials:  
Email: `admin@mail.com`  
Password: `123`  

**NOTE:**
- 🔐 User has to be Admin
- 🔒 Authentication is needed
- 🔓 Authentication is not needed

### Admin actions:
- 🔐 Create and modify products  
   `PUT /api/product` and `POST /api/product/{id}`
- 🔐 Change status for orders  
   `PUT /api/order`
- 🔐 Create and modify categories  
   `PUT /api/category` and `POST /api/category/{id}`

### User and Admin actions:

- 🔓 Search product  
   `POST /api/product` or `GET /api/product?keyword={something}`  
Note: To create user before you need to get the list category and productStatus
- 🔓 Get product's info in detail  
   `GET /api/product/{id}`
- 🔒 Add product to current order by creating an order item  
   `POST /api/order-item`
- 🔒 Modify and delete order item  
   `[PUT, DELETE] /api/order-item/{id}`
- 🔒 Create, get, modify and delete orders  
   `[GET, PUT, DELETE] /api/order/{id}` and `POST /api/order`
- 🔒 Create, get, modify and delete own addresses  
   `[GET, PUT, DELETE] /api/address/{id}` and `POST /api/address`
- 🔒 Create, get, modify and delete users  
   `[GET, PUT, DELETE /api/user` and `POST /api/address`
- 🔓 List products by category  
   `GET /api/category/{name}`
- 🔓 List categories by product  
   `GET /api/category/{productId}`
- 🔓 List categories  
   `GET /api/product-category/product/category/{name}`
- 🔓 Login  
   `POST /api/auth/token`
- 🔒 Upload image  
   `POST /api/file`

### Authentication
To authenticate we need get the access token, then we have to add it in the request header:
```yaml
   Authorization: Bearer {accessToken}'
```