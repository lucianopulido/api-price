# api-price

Servicio REST que expone el precio aplicable de un producto según la fecha de aplicación, producto y marca.

---

## 🚀 Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.4.5**
- **H2 Database**
- **Maven**
- **Lombok**
- **Jacoco**
- **SLF4J**
- **JUnit 5 + Mockito**

---

## 🏠 Arquitectura

Basada en **Arquitectura Hexagonal (Ports and Adapters)** respetando principios de **Clean Code** y **Domain-Driven Design (DDD)**.

```
com.inditex.apiprice
│
├── application
│   └── usecase (PriceUseCaseImpl)
│
├── domain
│   ├── model (Price)
│   ├── port (in/PriceUseCase, out/PriceRepositoryPort)
│   └──exception (PriceNotFoundException)
│
├── infrastructure
│   ├── dto.response (PriceResponse)
│   ├── adapter
│   │   ├── in.controller (PriceController)
│   │   └── out.database (PriceDatabaseAdapter) + repository (PriceJpaRepository)
│   ├── entity (PriceEntity)
│   ├── exception (GlobalExceptionHandler)
│   ├── mapper (PriceMapper)
│   └── util (MessageError)

```

**Fundamento**: Separa responsabilidades claramente, facilita tests unitarios, cambios de infraestructura sin impactar el dominio.

---

## 🔖 Diseño de la Solución

- **DTOs separados del dominio**: Evita acoplar APIs externas con la lógica de negocio.
- **Mappers independientes**: Transforma entidades de persistencia en modelos de dominio y viceversa.
- **@ControllerAdvice para errores**: Manejo global y consistente de excepciones.
- **Uso de Variables de Entorno**: Facilita configuración en ambientes de desarrollo, testeo y producción.
- **Indexación de Base de Datos**: Mejora la eficiencia en consultas de alto volumen.

---

## 💡 Detalles del Endpoint

**GET /api/v1/prices**

| Parámetro | Tipo | Descripción |
|------------|------|--------------|
| productId  | Long | ID del producto |
| brandId    | Long | ID de la marca |
| applicationDate | String (ISO-8601) | Fecha de aplicación |

**Ejemplo de Request:**
```http
GET http://localhost:8080/api/v1/prices?productId=35455&brandId=1&applicationDate=2020-06-14T16:00:00
```

**Ejemplo de Response:**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45
}
```

---

## 📈 Testing

- **Unit tests**: `PriceUseCaseImplTest` valida la lógica de selección de precios.
- **Integration tests**: `PriceControllerIntegrationTest` valida funcionamiento real de la API.
- **Errores testeados**: 404 (no encontrado), 400 (parámetros faltantes o inválidos).

**Fundamento**: Se asegura robustez del sistema tanto a nivel de lógica como de infraestructura.

---

## 📊 Cobertura de Código (Jacoco)

**Generación de Reporte:**
```bash
mvn clean verify
```
**Ruta del reporte:**
```
target/site/jacoco/index.html
```

Cobertura alta en capa de dominio y aplicación.

---

## 📂 Variables de Entorno

| Variable | Valor Ejemplo |
|----------|--------------|
| DATASOURCE_URL | jdbc:h2:mem:inditexdb;DB_CLOSE_DELAY=-1 |
| DATASOURCE_USERNAME | admin        |
| DATASOURCE_PASSWORD | admin123     |
| DATASOURCE_DRIVER_CLASS_NAME | org.h2.Driver|
| JPA_DATABASE_PLATFORM | org.hibernate.dialect.H2Dialect     |
| HIBERNATE_DDL_AUTO | update     |
| SHOW_SQL | true     |
| H2_CONSOLE_ENABLED | true     |

**Fundamento**:  Externalizar toda la configuración relevante para permitir flexibilidad en distintos entornos (local, CI/CD, preproducción, producción). Además, mejora la seguridad y evita hardcodear configuraciones sensibles.
Estas configuraciones están pensadas exclusivamente para el entorno de desarrollo (dev) debido a su simplicidad y facilidad de acceso.

---

##  Decisiones y Criterios Adoptados

- **Arquitectura Hexagonal**: Asegura desacoplamiento y fácil extensibilidad.
- **Clean Code + SOLID**: Mejora legibilidad, mantenibilidad y testabilidad.
- **Manejo de errores estructurado**: Respuestas consistentes y claras para clientes.
- **Uso de tipos fuertes**: Evita errores en manejo de datos (LocalDateTime, BigDecimal).
- **Logging estructurado**: Facilita auditoría y troubleshooting.

---

## 📄 Documentación Swagger (OpenAPI)
Se incorporó Swagger UI usando springdoc-openapi 2.8.6 para documentar automáticamente los endpoints REST.
Esto permite visualizar y probar la API desde http://localhost:8080/swagger-ui.html.


📎 Acceso general al proyecto
Todo el desarrollo se encuentra disponible en la siguiente rama y Pull Request:

🔗 Repositorio: https://github.com/lucianopulido/api-price

🔀 Rama de desarrollo: feature/hexagonal-architecture

📥 Pull Request hacia develop: https://github.com/lucianopulido/api-price/pull/1

Este enlace contiene toda la implementación, incluyendo instrucciones, arquitectura y ejemplos de uso.