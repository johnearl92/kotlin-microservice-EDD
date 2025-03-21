```
                   +----------------------+
                   |      CDN (Cloudflare)|
                   +----------+-----------+
                              |
       +----------------------+----------------------+
       |                  API Gateway (Kong, Nginx) |
       +----------------------+----------------------+
                              |
        ------------------------------------------------------
        |                        |                        |
  +------------+          +------------+          +------------+
  | Order MS   |          | Payment MS |          | Delivery MS|
  | (Ktor, CQRS) |        | (Ktor)     |         | (Ktor)     |
  | PostgreSQL  |         | PostgreSQL |         | MongoDB    |
  +------------+          +------------+         +------------+
        |                        |                        |
        |                        |                        |
        |        Kafka (Event Bus) (OrderPlaced, PaymentProcessed)        
        |                        |                        |
  +------------+          +------------+          +------------+
  | Inventory  |          | Notification |        | Analytics  |
  | Service    |          | Service      |        | Service    |
  | (Ktor)     |          | (Ktor)       |        | (ELK, Prometheus) |
  | PostgreSQL |          | Kafka        |        | MongoDB, Grafana  |
  +------------+          +------------+          +------------+

        ------------------------------------------------------
                      Kubernetes (K8s Cluster)
              Service Registry & Discovery (Consul, Eureka)
              Load Balancer (Nginx, Kubernetes Ingress)
              Identity Provider (Keycloak, OAuth2.0)

📌 Key Features:
✅ Ktor Microservices – Each core service runs independently.
✅ Kafka – Event-driven communication (OrderPlaced, PaymentConfirmed).
✅ CQRS – Order service uses PostgreSQL for commands, MongoDB for queries.
✅ API Gateway – Central entry point for clients.
✅ Load Balancer – Ensures high availability.
✅ Kubernetes – Manages deployments and scaling.
✅ Identity Provider – Secure authentication with OAuth2.
✅ Observability – Logs via ELK, monitoring via Prometheus & Grafana.
✅ CDN – Faster asset delivery.
```
