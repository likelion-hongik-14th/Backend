# 📋 w5Homework 프로젝트 전체 API 기능명세서

## 📂 목차
1. [👥 회원 (Member) API 명세](#1-회원-member-api-명세)
2. [🎁 상품 (Product) API 명세](#2-상품-product-api-명세)
3. [🛒 장바구니 (Cart) API 명세](#3-장바구니-cart-api-명세)
4. [➕ 장바구니 아이템 (Cart Item) API 명세](#4-장바구니-아이템-cart-item-api-명세)
5. [🏡 주소 관리 (Address) API 명세](#5-주소-관리-address-api-명세)
6. [🛍️ 주문 관리 (Order) API 명세](#6-주문-관리-order-api-명세)
7. [💡 도메인별 실제 JSON 데이터 예시 (Payload Examples)](#-도메인별-실제-json-데이터-예시-payload-examples)

---

## 1. 👥 회원 (Member) API 명세

| 기능명 | Method | URL | Request Parameter / Body 스펙 | Response (HTTP Status) / 결과 스펙 |
| :--- | :---: | :--- | :--- | :--- |
| **회원 가입** | `POST` | `/v1/members` | **[JSON Body]**<br>- `name` (String)<br>- `email` (String)<br>- `password` (String) | **201 Created**<br>가입 완료된 회원 정보 반환<br>(회원 ID 포함, 비밀번호 제외) |
| **회원 단건 조회** | `GET` | `/v1/members/{id}` | **[Path Variable]**<br>- `id` (Long) : 회원 Primary ID | **200 OK**<br>해당 ID에 매칭되는 회원 정보 반환 |

---

## 2. 🎁 상품 (Product) API 명세

| 기능명 | Method | URL | Request Parameter / Body 스펙 | Response (HTTP Status) / 결과 스펙 |
| :--- | :---: | :--- | :--- | :--- |
| **상품 등록** | `POST` | `/v1/products` | **[JSON Body]**<br>- `name` (String)<br>- `description` (String)<br>- `price` (Long)<br>- `stock` (Long) | **201 Created**<br>등록 완료된 상품 정보 반환<br>(상품 ID 포함) |
| **상품 단건 조회** | `GET` | `/v1/products/{id}` | **[Path Variable]**<br>- `id` (Long) : 상품 Primary ID | **200 OK**<br>해당 ID에 매칭되는 상품 상세 정보 반환 |
| **상품 전체 목록 조회** | `GET` | `/v1/products` | *없음* | **200 OK**<br>등록된 전체 상품 목록 리스트 반환 |

---

## 3. 🛒 장바구니 (Cart) API 명세

| 기능명 | Method | URL | Request Parameter / Body 스펙 | Response (HTTP Status) / 결과 스펙 |
| :--- | :---: | :--- | :--- | :--- |
| **장바구니 조회** | `GET` | `/v1/carts/{memberId}` | **[Path Variable]**<br>- `memberId` (Long) : 회원 ID | **200 OK**<br>회원의 장바구니 내역 반환<br>(아이템 목록, 총가격, 총수량 포함) |

---

## 4. ➕ 장바구니 아이템 (Cart Item) API 명세

| 기능명 | Method | URL | Request Parameter / Body 스펙 | Response (HTTP Status) / 결과 스펙 |
| :--- | :---: | :--- | :--- | :--- |
| **장바구니 담기** | `POST` | `/v1/carts/items` | **[JSON Body]**<br>- `memberId` (Long)<br>- `productId` (Long)<br>- `count` (Long) : 담을 수량 | **201 Created**<br>장바구니에 추가된 아이템 상세 정보 반환<br>(장바구니 ID 및 매핑 정보 포함) |

---

## 5. 🏡 주소 관리 (Address) API 명세

| 기능명 | Method | URL | Request Parameter / Body 스펙 | Response (HTTP Status) / 결과 스펙 |
| :--- | :---: | :--- | :--- | :--- |
| **배송지 등록** | `POST` | `/v1/addresses` | **[JSON Body]**<br>- `memberId` (Long)<br>- `addressName` (String)<br>- `zipCode` (String)<br>- `cityAddress` (String)<br>- `phoneNumber` (String) | **200 OK**<br>생성 완료된 배송지 데이터 반환<br>(배송지 ID 포함) |
| **배송지 목록 조회** | `GET` | `/v1/addresses` | **[Query Parameter]**<br>- `memberId` (Long, 필수) | **200 OK**<br>해당 회원이 소유한 모든 배송지 목록 반환 |
| **배송지 수정** | `PUT` | `/v1/addresses/{addressId}` | **[Path Variable]**<br>- `addressId` (Long)<br>**[JSON Body]**<br>- `addressName` (String)<br>- `zipCode` (String)<br>- `cityAddress` (String)<br>- `phoneNumber` (String) | **200 OK**<br>수정이 반영된 배송지 데이터 반환 |
| **배송지 삭제** | `DELETE` | `/v1/addresses/{addressId}` | **[Path Variable]**<br>- `addressId` (Long) | **200 OK**<br>삭제가 완료된 배송지 데이터 반환 |

---

## 6. 🛍️ 주문 관리 (Order) API 명세

| 기능명 | Method | URL | Request Parameter / Body 스펙 | Response (HTTP Status) / 결과 스펙 |
| :--- | :---: | :--- | :--- | :--- |
| **장바구니 일괄 주문** | `POST` | `/v1/orders/cart` | **[JSON Body]**<br>- `memberId` (Long)<br>- `addressId` (Long) | **201 Created**<br>주문 완료 데이터 반환<br>(주문 ID, 상태, 주문 상품 리스트 포함) |
| **상품 바로 주문** | `POST` | `/v1/orders/direct` | **[JSON Body]**<br>- `memberId` (Long)<br>- `productId` (Long)<br>- `count` (Long)<br>- `addressId` (Long) | **201 Created**<br>주문 완료 데이터 반환 |
| **주문 단건 조회** | `GET` | `/v1/orders/{id}` | **[Path Variable]**<br>- `id` (Long) | **200 OK**<br>해당 ID에 매칭되는 주문 상세 내역 반환 |
| **주문 취소** | `PUT` | `/v1/orders/{id}/cancle` | **[Path Variable]**<br>- `id` (Long) | **200 OK**<br>반환 바디 데이터 없음 (Empty Response)<br>*(재고량 원상 복구)* |

---
---

## 💡 도메인별 실제 JSON 데이터 예시 (Payload Examples)

### 👥 1. Member (회원) API 예시
* **회원 가입 (`POST /v1/members`) Request**
```json
{
  "name": "홍길동",
  "email": "gildong@gmail.com",
  "password": "securepassword123"
}
```
* **회원 가입 Response (201 Created)**
```json
{
  "id": 3,
  "name": "홍길동",
  "email": "gildong@gmail.com"
}
```

---

### 🎁 2. Product (상품) API 예시
* **상품 등록 (`POST /v1/products`) Request**
```json
{
  "name": "맥북 프로 16인치",
  "description": "M3 Max 칩 탑재 고성능 개발 서버용 맥북",
  "price": 4200000,
  "stock": 50
}
```
* **상품 등록 Response (201 Created)**
```json
{
  "id": 5,
  "name": "맥북 프로 16인치",
  "description": "M3 Max 칩 탑재 고성능 개발 서버용 맥북",
  "price": 4200000,
  "stock": 50
}
```

---

### 🛒 3. Cart & CartItem (장바구니) API 예시
* **장바구니 담기 (`POST /v1/carts/items`) Request**
```json
{
  "memberId": 3,
  "productId": 5,
  "count": 2
}
```
* **장바구니 담기 Response (201 Created)**
```json
{
  "id": 1,
  "productId": 5,
  "cartId": 1,
  "count": 2
}
```
* **장바구니 조회 (`GET /v1/carts/3`) Response (200 OK)**
```json
{
  "id": 1,
  "memberId": 3,
  "cartItems": [
    {
      "id": 1,
      "productId": 5,
      "cartId": 1,
      "count": 2
    }
  ],
  "totalPrice": 8400000,
  "totalQuantity": 2
}
```

---

### 🏡 4. Address (주소) API 예시
* **배송지 등록 (`POST /v1/addresses`) Request**
```json
{
  "memberId": 3,
  "addressName": "우리집",
  "zipCode": "12345",
  "cityAddress": "서울특별시 마포구 백범로 35",
  "phoneNumber": "010-1234-5678"
}
```
* **배송지 등록 Response (200 OK)**
```json
{
  "id": 1,
  "addressName": "우리집",
  "zipCode": "12345",
  "cityAddress": "서울특별시 마포구 백범로 35",
  "phoneNumber": "010-1234-5678"
}
```

---

### 🛍️ 5. Order (주문) API 예시
* **장바구니 일괄 주문 (`POST /v1/orders/cart`) Request**
```json
{
  "memberId": 3,
  "addressId": 1
}
```
* **장바구니 일괄 주문 Response (201 Created)**
```json
{
  "orderId": 12,
  "status": "ORDERED",
  "orderDate": "2026-05-21T14:00:00",
  "orderItems": [
    {
      "productName": "맥북 프로 16인치",
      "count": 2,
      "orderPrice": 8400000
    }
  ]
}
```
