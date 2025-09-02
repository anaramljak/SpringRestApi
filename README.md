**Local Environment Setup**

* Installed Java 17 and set it as the default JDK version.

* Added necessary Maven dependencies for Spring Boot, validation, and PostgreSQL.

* Installed and configured a PostgreSQL database.

* Configured database connection settings in the application.yml file (URL, username, and password).

**API Testing**

I have tested the following endpoints:

**POST - create a product**
**http://localhost:8081/api/products**

Request body example:
```json
{
    "code": "somecode10",
    "name": "name",
    "priceEur": 30.45,
    "isAvailable": true
}
```

**GET - view a list of products**
**http://localhost:8081/api/products**

**GET - view specific product**
**http://localhost:8081/api/products/somecode10**

