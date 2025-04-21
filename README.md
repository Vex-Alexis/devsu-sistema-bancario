# 🏦 Devsu Taller Práctico

Proyecto desarrollado como parte del taller práctico, se implementa un sistema utilizando microservicios en Java y Spring Boot, con PostgreSQL como base de datos. 
Se incluyen servicios para gestionar clientes, cuentas y movimientos, siguiendo principios de arquitectura limpia, microservicios desacoplados y buenas prácticas.

Para simplificar la ejecución y despliegue, se utilizó Docker junto con docker-compose, permitiendo levantar toda la infraestructura (servicios + base de datos) con un solo comando.



<br> <!-- Salto de línea -->
## Contenido

- [Funcionalidades Principales](#-funcionalidades-principales)
- [Tecnologías Utilizadas](#%EF%B8%8F-tecnologías-utilizadas)
- [Clonar y levantar el proyecto](#%EF%B8%8F-clonar-y-levantar-el-proyecto)
- [Endpoints](#-endpoints)
- [Ejemplos de Request Body](#-ejemplos-de-request-body)
- [Generación de Reporte de Cuentas](#-endpoint-generación-de-reporte-de-cuentas)
- [Comunicación entre microservicios](#%EF%B8%8F-comunicación-entre-microservicios)
- [Manejo de Excepciones](#-manejo-de-excepciones)
- [Pruebas](#-pruebas)
- [Arquitectura](#%EF%B8%8F-arquitectura)


<br> <!-- Salto de línea -->
## 🧩 Funcionalidades Principales

- CRUD completo para la entidad **Cliente**.
- CRUD (excepto eliminación) para **Cuentas** y **Movimientos**.
- Registro de movimientos con impacto en el saldo de la cuenta: creación, consulta y reversiones.
- Generación de reporte de estado de cuenta por cliente y rango de fechas.
- Validaciones de negocio (saldo insuficiente, cuentas inactivas, etc).
- Comunicación sincrónica entre microservicios mediante HTTP.
- Contenedores Docker para fácil despliegue.
- Manejo global de excepciones con respuestas JSON.
- Perfiles configurados para `local` y `docker`.
- Scripts para poblar y levantar la base de datos PostgreSQL.
- Pruebas unitarias y de integración (Karate).

<br> <!-- Salto de línea -->
## ⚙️ Tecnologías utilizadas

- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Web + JPA**
- **PostgreSQL**
- **Docker & Docker Compose**
- **JUnit 5**
- **Arquitectura Limpia (Clean Architecture)**


<br> <!-- Salto de línea -->
## 🛠️ Clonar y levantar el proyecto

<br> <!-- Salto de línea -->
1. Clona el repositorio:
```bash
git clone https://github.com/Vex-Alexis/devsu-sistema-bancario.git
```
<br> <!-- Salto de línea -->
2. Navega al directorio del proyecto:
```bash
cd devsu-sistema-bancario/docker-compose
```
3. Levanta los servicios:
```bash
docker-compose up --build
```
4. Servicios expuestos:


| Servicio                | Puerto
|-------------------------|------
| clientes-service        | `8080`
| cuentas-service         | `8081`
| PostresSQL              | `5432`

> Asegúrate de que no estén siendo usados por otros procesos.



<br> <!-- Salto de línea -->
## 🚀 Endpoints

Esta sección describe todos los endpoints REST expuestos por los microservicios **clientes-service** y **cuentas-service**, accesibles una vez los servicios estén desplegados con Docker. También puedes utilizar la colección de Postman incluida.

<br> <!-- Salto de línea -->
### 📌 Los puertos por defecto son:

- clientes-service: http://localhost:8080
- cuentas-service: http://localhost:8081

<br> <!-- Salto de línea -->
### 🌐 Rutas base por servicio

| Servicio    | Base URL                           | Descripción
|-------------|------------------------------------|-------------------------------------------
| Clientes    | http://localhost:8080/clientes     | CRUD y consulta de clientes
| Cuentas     | http://localhost:8081/cuentas      | CRUD y consulta de cuentas
| Movimientos | http://localhost:8081/movimientos  | Registro y consulta de movimientos


<br> <!-- Salto de línea -->
### 📦 Colección de Postman
Puedes usar la colección de Postman para probar rápidamente todos los endpoints disponibles.

Ruta dentro del repositorio:

.devsu.postman_collection.json -> [📄 Devsu Postman Collection](./devsu.postman_collection.json)

> Asegúrate de tener los servicios levantados (clientes-service en 8080, cuentas-service en 8081) antes de hacer peticiones.


<br> <!-- Salto de línea -->
### 👤 Clientes (`/clientes`)

| Método | Endpoint                                     | Descripción                             |
|--------|----------------------------------------------|-----------------------------------------|
| POST   | `/clientes`                                  | Crear un nuevo cliente                               |
| GET    | `/clientes`                                  | Listar todos los clientes                            |
| GET    | `/clientes/{id}`                             | Consultar cliente por ID                             |
| GET    | `/clientes/identificacion/{identificacion}`  | Consultar cliente por número identificación          |
| PUT    | `/clientes/{id}`                             | Actualizar datos de un cliente                       |
| DELETE | `/clientes/{id}`                             | Eliminar cliente por cuentaId                        |
>  No se permite eliminar un cliente si tiene cuentas asociadas

<br> <!-- Salto de línea -->
### 🏦 Cuentas (`/cuentas`)

| Método | Endpoint                                    | Descripción                             |
|--------|---------------------------------------------|-----------------------------------------|
| GET    | `/cuentas`                                  | Listar todas las cuentas                |
| GET    | `/cuentas/{id}`                             | Consultar cuenta por ID                 |
| GET    | `/cuentas/numero-cuenta/{numero-cuenta}`    | Consultar cuenta por número de cuenta   |
| POST   | `/cuentas`                                  | Crear nueva cuenta                      |
| PUT    | `/cuentas/{numero-cuenta}`                  | Actualizar datos de una cuenta          |
| GET    | `/cuentas/reportes?identificacionCliente={identificacion}&desde={fecha1}&hasta={fecha2}`                        | Reporte de estado de cuenta por cliente y rango de fechas |
>  El reporte de estado de cuenta incluye el saldo actual de cada cuenta asociada y el detalle de movimientos en el rango de fechas solicitado.


<br> <!-- Salto de línea -->
### 💸 Movimientos (`/movimientos`)

| Método | Endpoint                                    | Descripción                             |
|--------|---------------------------------------------|-----------------------------------------|
| POST   | `/movimientos`                              | Crear movimiento       |
| POST   | `/movimientos/{id}/revertir`                | Revertir un movimiento             |
| GET    | `/movimientos`                              | Consultar todos los movimientos        |
| GET    | `/movimientos/{id}`                         | Consultar movimiento por Id             |
| GET    | `/movimientos/cuenta/{id}`                  | Consultar movimientos por cuentaId        |
> Al registrar un movimiento, si el saldo es insuficiente, se lanza una excepción con una respuesta controlada.

> En lugar de eliminar movimientos físicamente, se cambia el tipoMovimiento a "REVERTIDO: original_tipo" y se crea un nuevo movimiento con el tipoMovimiento "REVERSION" para mantener trazabilidad y llevar el registro de las transacciones realizadas.

<br> <!-- Salto de línea -->
## 🧾 Ejemplos de Request Body
#### 📍 POST /clientes
Crea un nuevo cliente.
```json
{
  "nombre": "Santiago Pérez",
  "genero": "Masculino",
  "edad": 35,        
  "identificacion": "1230000123",
  "direccion": "Calle 123 #45-67",
  "telefono": "3101234567",
  "contraseña": "pass123",
  "estado": true
}
```
---
#### 📍 PUT /clientes/{id}
Actualiza los datos de un cliente existente.
```json
{
  "nombre": "Santiago Pérez Update",
  "genero": "Masculino",
  "edad": 35,
  "identificacion": "1000123123",
  "direccion": "Carrera 10 #45-89",
  "telefono": "3102351212",
  "contraseña": "claveActualizada",
  "estado": true
}
```
---
#### 📍 POST /cuentas
Crea una nueva cuenta asociada a un cliente.
```json
{
  "numeroCuenta": "600700800",
  "tipoCuenta": "Ahorros",
  "estado": true,
  "clienteId": 1
}
```
---
#### 📍 PUT /cuentas/{numeroCuenta}
Actualiza los datos de una cuenta existente.
```json
{
  "tipoCuenta": "Ahorros Update",
  "estado": false
}
```
---
#### 📍 POST /movimientos
Crea un nuevo movimiento.
> El valor positivo representa un ingreso, y el valor negativo un egreso.
##### Ejemplo de Depósito:
```json
{
  "tipoMovimiento": "Deposito 200.0000",
  "valor": 2000000,
  "cuentaId": 1
}
```
##### Ejemplo de Retiro:
```json
{
  "tipoMovimiento": "Retiro 200.0000",
  "valor": -2000000,
  "cuentaId": 1
}
```
---
#### 📍 POST /movimientos/{id}/revertir
Revierte un movimiento previamente realizado.
> No requiere un cuerpo en la solicitud (body vacío).
---

<br> <!-- Salto de línea -->
## 📈 Endpoint: Generación de Reporte de Cuentas
#### 📍 POST /cuentas/reportes
Obtiene un reporte consolidado de los movimientos de todas las cuentas asociadas a un cliente, dentro de un rango de fechas.
Ejemplo de URL:
```
http://localhost:8081/cuentas/reportes?identificacionCliente=1234567890&desde=2024-01-01&hasta=2025-04-30
```
#### 📌 Parámetros (Query Params):
| Nombre                   | Tipo      | Obligatorio     | Descripción
|--------------------------|-----------|-----------------|--------------------------------------------------|
| `identificacionCliente`  | `string`  | ✅ Si          | Número de identificación del cliente.
| `desde`                  | `date`    | ✅ Si          | Fecha inicial del período en formato `YYYY-MM-DD`.
| `hasta`                  | `date`    | ✅ Si          | Fecha final  del período en formato `YYYY-MM-DD`.

#### Ejemplo de Response Body
```json
{
  "cliente": {
    "clienteId": 1,
    "nombre": "Juan Pérez",
    "identificacion": "1234567890",
    "direccion": "Cra 12 #34-56"
  },
  "cuentas": [
    {
      "cuentaId": 5,
      "numeroCuenta": "5006007005",
      "tipoCuenta": "Ahorros",
      "saldoActual": 0.0,
      "estado": true,
      "movimientos": [
        {
          "movimientoId": 10,
          "fecha": "2024-08-08",
          "tipoMovimiento": "Deposito de 5.000",
          "valor": 5000,
          "saldo": 5000,
          "cuentaId": 5
        },
        {
          "movimientoId": 11,
          "fecha": "2024-08-08",
          "tipoMovimiento": "Retiro de 5.000",
          "valor": -5000,
          "saldo": 0.0,
          "cuentaId": 5
        }
      ]
    },
    ..
  ]
}
```

#### 📘 Explicación de la Estructura
- `cliente`: Información básica del cliente consultado.
- `cuentas`: Lista de todas las cuentas activas del cliente dentro del rango de fechas.
  - `saldoActual`: Monto actual en la cuenta, considerando todos los movimientos aplicados.
  - `movimientos`: Lista de movimientos realizadas en el rango de fechas.
    - `tipoMovimiento`: Describe la naturaleza del movimiento
    - Valores positivos representan ingresos.
    - Valores negativos representan egresos.
    - `saldo`: Es el saldo resultante después de aplicar ese movimiento.

---
<br> <!-- Salto de línea -->
## 🛰️ Comunicación entre microservicios
Este sistema está compuesto por dos microservicios principales:
- `clientes-service`: Encargado de la gestión de la información de los clientes.
- `cuentas-service`: Encargado de la administración de cuentas, movimientos y generación de reportes.

En este diseño, era natural que algún microservicio necesitaran acceder a información mantenida por otros. Por ejemplo, al generar un reporte del estado de cuenta, se requiere no solo la información financiera, sino también los datos del cliente correspondiente. Por eso, el ms `cuentas-service` necesita consultar al ms `clientes-service`.

Para lograr esto, se implemento una comunicación síncrona vía HTTP utilizando RestTemplate, ya que la consulta es directa y requiere una respuesta inmediata.



---
<br> <!-- Salto de línea -->
## 🚨 Manejo de Excepciones

La aplicación implementa un sistema centralizado y controlado para el manejo de errores.

### Enfoque Adoptado
Cada microservicio define sus propias excepciones personalizadas dentro de la capa de dominio. Estas excepciones son lanzadas desde los casos de uso (use cases) ubicados en la capa de aplicación. 
Posteriormente, un manejador global de excepciones captura y procesa estas excepciones, devolviendo respuestas HTTP claras, estructuradas y con información relevante para el cliente.

<br> <!-- Salto de línea -->
### Ejemplo de Respuesta de Error
Cuando ocurre una excepción, el cliente recibe una respuesta estructurada como esta:

```json
{
    "status": 400,
    "success": false,
    "message": "Saldo no disponible para realizar el movimiento.",
    "timestamp": "2025-04-20 19:13:35"
}
```
<br> <!-- Salto de línea -->
### Excepciones Personalizadas y sus Códigos


| Excepción                     | Códgio | Descripción
|-------------------------------|--------|----------------------------------------------------
| DuplicatedClientException     | 409	 | Ya existe un cliente con la misma identificación.
| DuplicateAccountException     | 409	 | Ya existe una cuenta con ese número.
| InactiveAccountException      | 400    | Se intentó operar sobre una cuenta inactiva.
| InsufficientBalanceException  | 400    | Saldo insuficiente para realizar el movimiento
| NotFoundException             | 404    | El recurso solicitado no fue encontrado.

---
<br> <!-- Salto de línea -->
## 🧪 Pruebas
En este proyecto se implementan pruebas unitarias e integración, en este caso se implementarón en el microservicio `clientes-service`. Las pruebas están en la carpeta `src/test/java`.

<br> <!-- Salto de línea -->
### ✅ Pruebas Unitarias
Se ha implementado una prueba unitaria para validar el comportamiento de la entidad de dominio Cliente en el microservicio `clientes-service`.
Estas pruebas están escritas en JUnit 5 y se ejecutan como parte del ciclo estándar de testeo con Maven.

### 🔁 Prueba de integración
Se implementó una prueba de integración utilizando Karate para validar el comportamiento del endpoint de creación de clientes en el microservicio `clientes-service`.


---
<br> <!-- Salto de línea -->
## 🏛️ Arquitectura


### Arquitectura general

El sistema está compuesto por dos microservicios independientes: `clientes-service` y `cuentas-service`. Cada uno está diseñado bajo principios de arquitectura limpia, y expone sus funcionalidades a través de una API REST.

Ambos servicios están contenerizados con Docker y orquestados mediante Docker Compose, lo que permite levantar toda la solución de manera sencilla. Son microservicios independientes, comparten la misma base de datos PostgreSQL, cada uno accediendo a sus propias tablas.


<p align="center">
  <img src="diagrama-arquitectura-general.png" alt="Arquitectura general" width="600"/>
</p>


<br> <!-- Salto de línea -->
### 🧩 Arquitectura por microservicio

Los microservicios comparten el mismo diseño estructural, promoviendo reutilización de patrones y mantenibilidad del código.

```css
application/
  ├── usecase/               <- Casos de uso o servicios de aplicación

domain/
  └─ model/                  <- Entidades del dominio y gateways (interfaces)
  └─ useCase/                <- Interfaces de los casos de uso

infrastructure/
  └─ adapters/               <- Adaptadores de salida (Base de datos, clientes REST, colas, etc)
  └─ entry-points/           <- Adaptadores de entrada (REST controllers, GraphQL, solicitudes externas) 
```

Sigue una arquitectura limpia dividida en tres grandes capas:
- **Dominio**: Modelos del negocio, interfaces (use cases), y las interfaces (gateways) que definen los contratos con la infraestructura.
- **Aplicación**: implementa los casos de uso con la lógica central del servicio.
- **Infraestructura**:
  - Adaptadores implementan gateways, conexión o acceso tecnologías externas (Base de datos, REST, SQS, etc) 
  - Puntos de entrada (Controladores REST, GraphQL y manejo de solicitudes externas.)
  
Flujo general:
- Una petición llega al controlador (entry-point REST).
- El controlador transforma los datos con los DTOs y los pasa al caso de uso correspondiente.
- El caso de uso ejecuta la lógica y se comunica con los gateways definidos en el dominio.
- Los adaptadores de infraestructura implementan estos gateways y acceden a las tecnologías externas (por ejemplo, base de datos).




---

### **Gracias**














