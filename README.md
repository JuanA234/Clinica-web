# 🏥 Sistema de Gestión Clínica

Este es un proyecto desarrollado con **Spring Boot** para gestionar una clínica médica. El sistema permite registrar, consultar, actualizar y eliminar información relacionada con doctores, pacientes, salas de consulta, y citas médicas.

## 🚀 Tecnologías utilizadas

- Java 17 
- Spring Boot 3.4.4
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- Hibernate 6
- MySQL / PostgreSQL (ajustable en `application.properties`)
- Lombok
- JUnit y Mockito (para pruebas)
- Maven

## 🧩 Módulos principales

- **Doctor**: CRUD de doctores y control de disponibilidad.
- **Patient**: Registro y gestión de pacientes.
- **ConsultRoom**: Administración de salas de consulta.
- **Appointment**: Gestión de citas médicas, validación de disponibilidad de doctor y sala.

## ⚙️ Configuración

### 1. Clona el repositorio

```bash
git clone https://github.com/tu_usuario/clinica-springboot.git
cd clinica-springboot
```

### 2. Configura la base de datos

Edita `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/clinica
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
jwt.secretKey = tu_clave_jwt_segura
jwt.expiration = 3600000
```

### 3. Construcción y ejecución

**Desde terminal:**
```bash
mvn clean install
mvn spring-boot:run
```

**Desde IntelliJ IDEA:**
Usa el botón Run en la clase `ClinicaApplication.java`.

## 🧪 Pruebas

Ejecuta las pruebas unitarias con:

```bash
mvn test
```

Las pruebas están implementadas para los servicios principales utilizando JUnit 5 y Mockito.

## 🗂️ Estructura del proyecto

```
src
 └── main
     ├── java/com/clinica
     │    ├── controller
     │    ├── service
     │    ├── repository
     │    ├── dto
     │    ├── mapper
     │    └── model
     └── resources
          └── application.properties
```

## 📬 Endpoints REST

### Doctores
- `GET /api/doctors`
- `GET /api/doctors/{id}`
- `POST /api/doctors`
- `PUT /api/doctors/{id}`
- `DELETE /api/doctors/{id}`

### Pacientes
- `GET /api/patients`
- `GET /api/patients/{id}`
- `POST /api/patients`
- `PUT /api/patients/{id}`
- `DELETE /api/patients/{id}`

### Salas de Consulta
- `GET /api/consult-rooms`
- `POST /api/consult-rooms`
- `PUT /api/consult-rooms/{id}`
- `DELETE /api/consult-rooms/{id}`

### Citas Médicas
- `GET /api/appointments`
- `GET /api/appointments/{id}`
- `POST /api/appointments`
- `PUT /api/appointments/{id}`
- `DELETE /api/appointments/{id}`

## 📌 Características destacadas

- Manejo de errores y validaciones con DTOs.
- Mapeo entre entidades y DTOs usando MapStruct.
- Gestión de disponibilidad automática de doctores y consultorios.
- Arquitectura limpia basada en servicios y principios SOLID.

## 🔒 Seguridad

## 📈 Futuras mejoras

- Interfaz de usuario en React o Angular.
- Sistema de autenticación con JWT.
- Reportes y estadísticas de citas y disponibilidad.
- Agenda visual por día y doctor.
- Control de historial clínico del paciente.

## 📷 Capturas (opcional)

## 🧾 Documentación Swagger

Al iniciar la aplicación, puedes acceder a la documentación de la API en:

```
http://localhost:8080/swagger-ui/index.html
```

Asegúrate de tener agregadas las dependencias de Swagger OpenAPI si lo estás usando.

## 👨‍💻 Autores

Desarrollado por Juan Andrés Avendaño Luján y Juan Sebastian Sarmiento Mendoza  
Estudiantes de Ingeniería de Sistemas  
📧 juanandreslujan30@gmail.com  
📧  juanse130904@outlook.com    
📅 Proyecto académico - 2025
