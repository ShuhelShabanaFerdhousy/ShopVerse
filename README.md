# 🛒 ShopVerse – Microservices E-Commerce Backend

A learning-first, production-inspired microservices architecture built using Spring Boot and Spring Cloud, designed to simulate real-world distributed systems.

---

## 🚀 Tech Stack

* Java 17
* Spring Boot
* Spring Cloud
* Spring Data JPA
* Apache Kafka
* PostgreSQL 
* REST APIs
* Maven

---

## 🧩 Architecture

ShopVerse is built using a distributed microservices architecture with independent services communicating via both synchronous and asynchronous patterns.

* **API Gateway** → Single entry point for all client requests
* **Discovery Server** → Service registration and discovery
* **Product Service** → Manages products, stock, pricing
* **Cart Service** → Handles user shopping carts
* **Order Service** → Handles order creation and lifecycle
* **Payment Service** → Processes payments (COD + Online simulation)
* **User Service** → Manages user data

---

## 🔄 Service Communication
* **Synchronous Communication**
* Feign Client
* WebClient

* **Asynchronous Communication**
* Apache Kafka

---

## 📁 Project Structure

```
shopverse/
└── api-gateway/
└── common/
└── discovery-server/
└── services/
    ├── product-service/
    ├── cart-service/
    ├── order-service/
    ├── payment-service/
    └── user-service/
```

---

## 🎯 Learning Goals

* Understand real-world microservices architecture
* Implement sync + async service communication
* Learn API Gateway + Service Discovery patterns
* Build event-driven systems using Kafka
* Practice production-grade backend structuring

---

## 🚧 Status

🟢 Core microservices implemented
🟢 Synchronous communication (Feign/WebClient) completed
🟢 Asynchronous communication (Kafka) implemented
🟡 Spring Security (JWT-based authentication) next phase

---

## 👨‍💻 Author

Built as a hands-on backend engineering project focusing on real-world distributed system design and microservices architecture.
