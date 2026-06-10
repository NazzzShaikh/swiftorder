# SwiftOrder 🚀

A microservices demo project built with:
- Java 17 + Spring Boot 3.2
- Apache Kafka (event-driven communication)
- MySQL (database per service)
- Docker Compose

## Services
| Service | Port | Description |
|---|---|---|
| order-service | 8081 | Creates orders, fires Kafka events |
| notification-service | 8082 | Consumes Kafka events, saves notifications |

## Run Locally
```bash
docker-compose up -d
```