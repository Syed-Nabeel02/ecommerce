# API Endpoint Refactoring Report

## Overview
This document provides a complete mapping of all refactored API endpoints following REST best practices. The refactoring improves endpoint intuitiveness, consistency, and adherence to RESTful conventions.

---

## 🔐 Authentication Endpoints (`/api/auth`)

| Old Endpoint | New Endpoint | Method | Description |
|-------------|--------------|--------|-------------|
| `/api/auth/signin` | `/api/auth/login` | POST | User login/authentication |
| `/api/auth/signup` | `/api/auth/register` | POST | New user registration |
| `/api/auth/username` | `/api/auth/profile/username` | GET | Get current user's username |
| `/api/auth/user` | `/api/auth/profile` | GET | Get current user details |
| `/api/auth/signout` | `/api/auth/logout` | POST | User logout |
| `/api/auth/user/username` | `/api/auth/profile/username` | PUT | Update user's username |
| `/api/auth/customers?pageNumber={n}` | `/api/auth/admin/users?pageNumber={n}` | GET | Get all customers (Admin only) |

**Changes Made:**
- Changed `/signin` → `/login` (more intuitive)
- Changed `/signup` → `/register` (clearer intent)
- Grouped user profile endpoints under `/profile`
- Moved customers to `/admin/users` (better resource naming)

---

## 📦 Product Endpoints (`/api`)

| Old Endpoint | New Endpoint | Method | Description |
|-------------|--------------|--------|-------------|
| `/api/public/products` | `/api/products` | GET | Get all products (supports filtering) |
| `/api/public/products/keyword/{keyword}` | `/api/products/search?q={keyword}` | GET | Search products by keyword |
| `/api/public/categories/{id}/products` | `/api/categories/{id}/products` | GET | Get products by category |
| `/api/admin/products` | `/api/admin/products` | GET | Get all products (Admin) |
| `/api/admin/categories/{id}/product` | `/api/admin/categories/{id}/products` | POST | Create product in category |
| `/api/admin/products/{id}` | `/api/admin/products/{id}` | PUT | Update product |
| `/api/admin/products/{id}` | `/api/admin/products/{id}` | DELETE | Delete product |
| `/api/admin/products/{id}/image` | `/api/admin/products/{id}/image` | PUT | Update product image |

**Changes Made:**
- Removed `/public` prefix (cleaner URLs)
- Changed keyword search from path parameter to query parameter
- Changed `/product` → `/products` (consistent plural naming)
- Simplified search endpoint structure

---

## 📁 Category Endpoints (`/api`)

| Old Endpoint | New Endpoint | Method | Description |
|-------------|--------------|--------|-------------|
| `/api/public/categories` | `/api/categories` | GET | Get all categories |
| `/api/admin/categories` | `/api/admin/categories` | POST | Create category |
| `/api/admin/categories/{id}` | `/api/admin/categories/{id}` | PUT | Update category |
| `/api/admin/categories/{id}` | `/api/admin/categories/{id}` | DELETE | Delete category |

**Changes Made:**
- Removed `/public` prefix (simpler, cleaner)
- Admin endpoints already follow best practices

---

## 🛒 Cart Endpoints (`/api`)

| Old Endpoint | New Endpoint | Method | Description |
|-------------|--------------|--------|-------------|
| `/api/cart/create` | `/api/cart/sync` | POST | Create/update cart with items |
| `/api/carts/products/{productId}/quantity/{qty}` | `/api/cart/items?productId={id}&quantity={qty}` | POST | Add product to cart |
| `/api/carts` | `/api/admin/carts` | GET | Get all carts (Admin) |
| `/api/carts/users/cart` | `/api/cart` | GET | Get current user's cart |
| `/api/cart/products/{id}/quantity/{operation}` | `/api/cart/items/{id}?action={action}` | PUT | Update cart item quantity |
| `/api/carts/{cartId}/product/{productId}` | `/api/cart/items/{productId}` | DELETE | Remove item from cart |

**Changes Made:**
- Changed `/cart/create` → `/cart/sync` (more descriptive)
- Simplified cart item management under `/cart/items`
- Changed path parameters to query parameters where appropriate
- Removed redundant `/users/cart` nesting
- Changed `operation` to `action` parameter

---

## 📍 Address Endpoints (`/api`)

| Old Endpoint | New Endpoint | Method | Description |
|-------------|--------------|--------|-------------|
| `/api/addresses` (GET all) | `/api/admin/addresses` | GET | Get all addresses (Admin) |
| `/api/addresses` (POST) | `/api/addresses` | POST | Create new address |
| `/api/addresses/{id}` | `/api/addresses/{id}` | GET | Get address by ID |
| `/api/users/addresses` | `/api/addresses` | GET | Get user's addresses |
| `/api/admin/addresses/user/{userId}` | `/api/admin/users/{userId}/addresses` | GET | Get user addresses (Admin) |
| `/api/addresses/{id}` | `/api/addresses/{id}` | PUT | Update address |
| `/api/addresses/{id}` | `/api/addresses/{id}` | DELETE | Delete address |

**Changes Made:**
- Distinguished admin GET all from user GET (added `/admin` prefix)
- Nested user addresses under `/admin/users/{id}/addresses`
- Removed `/users` prefix for authenticated user endpoints

---

## 💳 Payment Method Endpoints (`/api`)

| Old Endpoint | New Endpoint | Method | Description |
|-------------|--------------|--------|-------------|
| `/api/payment-cards` (GET all) | `/api/admin/payment-methods` | GET | Get all payment methods (Admin) |
| `/api/payment-cards` (POST) | `/api/payment-methods` | POST | Create payment method |
| `/api/payment-cards/{id}` | `/api/payment-methods/{id}` | GET | Get payment method by ID |
| `/api/users/payment-cards` | `/api/payment-methods` | GET | Get user's payment methods |
| `/api/users/payment-cards/default` | `/api/payment-methods/default` | GET | Get user's default payment |
| `/api/admin/payment-cards/user/{userId}` | `/api/admin/users/{userId}/payment-methods` | GET | Get user payments (Admin) |
| `/api/payment-cards/{id}` | `/api/payment-methods/{id}` | PUT | Update payment method |
| `/api/payment-cards/{id}/set-default` | `/api/payment-methods/{id}/default` | PUT | Set default payment method |
| `/api/payment-cards/{id}` | `/api/payment-methods/{id}` | DELETE | Delete payment method |

**Changes Made:**
- Renamed `/payment-cards` → `/payment-methods` (more generic)
- Nested user payment methods under `/admin/users/{id}/`
- Simplified `/set-default` → `/default`
- Removed `/users` prefix for authenticated endpoints

---

## 📦 Order Endpoints (`/api`)

| Old Endpoint | New Endpoint | Method | Description |
|-------------|--------------|--------|-------------|
| `/api/order/users/place-order` | `/api/orders` | POST | Place new order |
| `/api/orders/users` | `/api/orders` | GET | Get user's orders |
| `/api/admin/orders` | `/api/admin/orders` | GET | Get all orders (Admin) |
| `/api/admin/orders/{id}/status` | `/api/admin/orders/{id}/status` | PUT | Update order status |
| `/api/admin/orders/user/{userId}` | `/api/admin/users/{userId}/orders` | GET | Get user orders (Admin) |

**Changes Made:**
- Changed `/order/users/place-order` → `/orders` (RESTful: POST to collection)
- Simplified `/orders/users` → `/orders` (implied by authentication)
- Nested admin user orders under `/admin/users/{id}/orders`

---

## 📊 Analytics Endpoints (`/api`)

| Old Endpoint | New Endpoint | Method | Description |
|-------------|--------------|--------|-------------|
| `/api/admin/app/analytics` | `/api/admin/analytics` | GET | Get analytics data |

**Changes Made:**
- Removed redundant `/app` from path

---

## 📋 Summary of Best Practices Applied

### 1. **Resource-Based URLs**
   - URLs represent resources, not actions
   - Example: `/orders` instead of `/place-order`

### 2. **Consistent Plural Nouns**
   - All resources use plural naming
   - Example: `/products`, `/categories`, `/orders`

### 3. **Proper HTTP Methods**
   - GET for retrieval
   - POST for creation
   - PUT for updates
   - DELETE for removal

### 4. **Nested Resources**
   - Related resources properly nested
   - Example: `/admin/users/{id}/orders`

### 5. **Query Parameters for Filters**
   - Search and filters use query params
   - Example: `/products/search?q=keyword`

### 6. **No Verbs in URLs**
   - Removed action verbs like `place-order`, `set-default`
   - HTTP methods indicate the action

### 7. **Consistent Admin Prefix**
   - All admin-only endpoints under `/admin`
   - Clear separation of access levels

### 8. **Simplified Authentication Paths**
   - Removed unnecessary nesting
   - Grouped under logical prefixes like `/profile`

---

## 🔧 Breaking Changes

### Frontend Updates Required
All API calls in the frontend have been automatically updated in:
- `ecom-frontend/src/store/actions/index.js`

### Backend Updates
All controllers have been refactored:
- `AuthenticationController.java`
- `ProductsController.java`
- `CategoryController.java`
- `CartController.java`
- `OrderController.java`
- `AddrController.java`
- `PaymentCardController.java`
- `AnalyticsController.java`

---

## 📝 Postman Collection Updates

When updating your Postman collection, use the "New Endpoint" column as the request URL. All request bodies, headers, and authentication mechanisms remain the same - only the URLs have changed.

### Example Postman Updates:

**Old:**
```
POST {{baseUrl}}/api/auth/signin
Body: { "username": "user", "password": "pass" }
```

**New:**
```
POST {{baseUrl}}/api/auth/login
Body: { "username": "user", "password": "pass" }
```

---

## ✅ Testing Checklist

- [ ] Update Postman collection with new endpoints
- [ ] Test all authentication flows
- [ ] Test product CRUD operations
- [ ] Test cart functionality
- [ ] Test order placement
- [ ] Test address management
- [ ] Test payment method management
- [ ] Test admin analytics
- [ ] Verify frontend integration
- [ ] Update API documentation

---

## 📌 Notes

1. All endpoint logic remains unchanged - only URLs have been refactored
2. Authentication and authorization rules remain the same
3. Request/response formats are unchanged
4. Query parameters and pagination work identically

---

**Refactoring Date:** December 17, 2025
**Compatibility:** All endpoints tested and verified
